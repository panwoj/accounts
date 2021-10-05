package com.wpirog.accounts.service;

import com.wpirog.accounts.domain.Account;
import com.wpirog.accounts.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DbService {
    @Autowired
    private AccountRepository repository;

    public Optional<Account> getAccount(final Long id) {
        return repository.findById(id);
    }

    public Account saveAccount(Account account) {
        return repository.save(account);
    }
}
