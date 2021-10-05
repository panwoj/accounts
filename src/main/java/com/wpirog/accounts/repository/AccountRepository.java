package com.wpirog.accounts.repository;

import com.wpirog.accounts.domain.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {
    @Override
    Optional<Account> findById(Long id);

    @Override
    Account save(Account account);
}
