package com.demo.accountservice.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.demo.accountservice.model.Account;
import com.demo.accountservice.model.Card;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class AccountRepositoryImpl implements AccountRepository {

	@Override
	public Optional<Account> findById(long id) {
		log.info("SQL Statment -> select * from account where id={}", id);
		return Optional.of(new Account(id, "1234567890", new Card("4752677496988301", "123", LocalDate.of(2023, 11, 11))));
		
		//throw new RuntimeException("Repository Error");
	}

}
