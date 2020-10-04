package com.demo.accountservice.service;

import java.util.Optional;

import com.demo.accountservice.model.Account;

public interface AccountService {

	Optional<Account> getAccount(long id);

	void asyncTask();

}
