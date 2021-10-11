package com.wpirog.accounts.service;

import com.wpirog.accounts.domain.Account;
import com.wpirog.commons.PaymentMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Slf4j
@Service
public class AccountListener {
    @Autowired
    private DbService dbService;

    @KafkaListener(topics = "accounts2")
    public void consume(@Payload PaymentMessage paymentMessage) throws IOException {
        log.info("Consumed paymentMessage: {}", paymentMessage);
        var senderAccount = dbService.getAccount(paymentMessage.getPaymentDto().getSenderAccount()).orElse(null);
        var recipientAccount = dbService.getAccount(paymentMessage.getPaymentDto().getRecipientAccount()).orElse(null);
        var amount = paymentMessage.getPaymentDto().getAmount();
        updateAmount(senderAccount, amount.negate());
        if (recipientAccount != null) {
            updateAmount(recipientAccount, amount);
        }
    }


    private void updateAmount(Account account, BigDecimal amount) {
        var beforeAmount = account.getAvailableFunds();
        var afterAmount = beforeAmount.add(amount);
        account.setAvailableFunds(afterAmount);
        dbService.saveAccount(account);
        log.info(String.format("Changed available funds for account %s from %s to %s", account.getNrb(), beforeAmount, afterAmount));
    }
}