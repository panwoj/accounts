package com.wpirog.accounts.repository;

import com.wpirog.accounts.domain.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {
    @Override
    Optional<Account> findById(Long id);

    Optional<Account> findByNrb(String nrb);

    List<Account> findAllByNrb(String nrb);

    @Override
    Account save(Account account);
}
