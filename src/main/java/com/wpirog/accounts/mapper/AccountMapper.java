package com.wpirog.accounts.mapper;

import com.wpirog.accounts.domain.Account;
import com.wpirog.accounts.domain.AccountDto;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public AccountDto mapToAccountDto(Account account) {
        if (account == null) {
            return null;
        }
        return new AccountDto(account.getId(), account.getNrb(),
                account.getCurrency(), account.getAvailableFunds());
    }

    public Account mapToAccount(AccountDto accountDto) {
        if (accountDto == null) {
            return null;
        }
        return new Account(accountDto.getId(), accountDto.getNrb(),
                accountDto.getCurrency(), accountDto.getAvailableFunds());
    }
}
