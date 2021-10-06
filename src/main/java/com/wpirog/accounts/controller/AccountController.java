package com.wpirog.accounts.controller;

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
@RequestMapping("/v1")
@RefreshScope
public class AccountController {
    @Autowired
    private AccountMapper mapper;

    @Autowired
    private DbService dbService;

    @Value("${application.allow-get-accounts}")
    private boolean allowGetAccounts;

    @RequestMapping(method = RequestMethod.GET, value = "/accounts")
    public AccountDto getAccounts(@RequestParam Long customerId) throws AccountNotFoundException {
        if(allowGetAccounts) {
            return mapper.mapToAccountDto(dbService.getAccount(customerId).orElseThrow(() -> new AccountNotFoundException("Please provide correct taskId value")));
        } else {
            log.info("Getting accounts is disabled");
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Getting accounts is disabled");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/accounts", consumes = APPLICATION_JSON_VALUE)
    public void getAccounts(@RequestBody AccountDto accountDto) {
        dbService.saveAccount(mapper.mapToAccount(accountDto));
    }
}
