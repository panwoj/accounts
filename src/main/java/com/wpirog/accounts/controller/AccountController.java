package com.wpirog.accounts.controller;

import com.wpirog.accounts.domain.Account;
import com.wpirog.accounts.domain.AccountDto;
import com.wpirog.accounts.exception.AccountNotFoundException;
import com.wpirog.accounts.mapper.AccountMapper;
import com.wpirog.accounts.service.DbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/v1/accounts")
@RefreshScope
public class AccountController {
    @Autowired
    private AccountMapper mapper;

    @Autowired
    private DbService dbService;

    @Value("${application.allow-get-accounts}")
    private boolean allowGetAccounts;

    @GetMapping
    public AccountDto getAccount(@RequestParam(required = false) Long customerId, @RequestParam(required = false) String nrb) throws AccountNotFoundException {
        if (!allowGetAccounts) {
            log.info("Getting accounts is disabled");
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Getting accounts is disabled");
        }

        Account account = null;
        if (customerId != null) {
            account = dbService.getAccount(customerId).orElseThrow(() -> new AccountNotFoundException("Please provide correct taskId value"));
        } else if (nrb != null) {
            account = dbService.getAccount(nrb).orElseThrow(() -> new AccountNotFoundException("Please provide correct nrb value"));
        }
        return mapper.mapToAccountDto(account);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void addAccount(@RequestBody AccountDto accountDto) {
        if (dbService.getAccounts(accountDto.getNrb()).size() > 0) {
            throw new IllegalArgumentException(String.format("Account %s already exists.", accountDto.getNrb()));
        }
        dbService.saveAccount(mapper.mapToAccount(accountDto));
    }
}
