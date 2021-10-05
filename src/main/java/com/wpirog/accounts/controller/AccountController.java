package com.wpirog.accounts.controller;

import com.wpirog.accounts.domain.AccountDto;
import com.wpirog.accounts.exception.AccountNotFoundException;
import com.wpirog.accounts.mapper.AccountMapper;
import com.wpirog.accounts.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1")
public class AccountController {
    @Autowired
    private AccountMapper mapper;

    @Autowired
    private DbService dbService;

    @RequestMapping(method = RequestMethod.GET, value = "/accounts")
    public AccountDto getAccounts(@RequestParam Long customerId) throws AccountNotFoundException {
        return mapper.mapToAccountDto(dbService.getAccount(customerId).orElseThrow(() -> new AccountNotFoundException("Please provide correct taskId value")));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/accounts", consumes = APPLICATION_JSON_VALUE)
    public void getAccounts(@RequestBody AccountDto accountDto) {
        dbService.saveAccount(mapper.mapToAccount(accountDto));
    }
}
