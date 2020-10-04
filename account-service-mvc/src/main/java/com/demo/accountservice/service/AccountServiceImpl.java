package com.demo.accountservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.demo.accountservice.model.Account;
import com.demo.accountservice.repository.AccountRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Optional<Account> getAccount(long id) {
		log.info("Executing some business logic");
		return accountRepository.findById(id);
	}

	@Override
	@Async
	public void asyncTask() {
		log.info("Performing an async task");
	}

}
