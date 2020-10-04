package com.demo.accountservice.repository;

import java.util.Optional;

import com.demo.accountservice.model.Account;

public interface AccountRepository {
	Optional<Account> findById(long id);
}
