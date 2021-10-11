package com.wpirog.accounts.service;

import java.util.List;
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

    public Optional<Account> getAccount(final String nrb) {
        return repository.findByNrb(nrb);
    }

    public List<Account> getAccounts(final String nrb) {
        return repository.findAllByNrb(nrb);
    }

    public Account saveAccount(Account account) {
        return repository.save(account);
    }
}
