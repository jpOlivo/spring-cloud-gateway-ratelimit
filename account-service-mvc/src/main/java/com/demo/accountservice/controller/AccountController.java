package com.demo.accountservice.controller;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.demo.accountservice.error.DemoNotWorkingException;
import com.demo.accountservice.model.Account;
import com.demo.accountservice.service.AccountService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AccountController {

	@Autowired
	private AccountService accountService;

	@GetMapping("/account/{id}")
	public ResponseEntity<Account> getAccount(@PathVariable("id") long id) {


		// Log and add context to event (see
		// https://github.com/logstash/logstash-logback-encoder/tree/logstash-logback-encoder-5.2#event-specific-custom-fields)
		log.info("Getting account resource for {}", keyValue("accountId", id));
		Optional<Account> body = accountService.getAccount(1);
		log.info("Retrieving account resource", keyValue("account", body));
		
		// performing some async task
		accountService.asyncTask();

		return new ResponseEntity<>(body.get(), HttpStatus.OK);

	}

	/*
	 * private Map<String, String> getHeadersInfo(HttpServletRequest request) {
	 * 
	 * Map<String, String> map = new HashMap<String, String>();
	 * 
	 * Enumeration headerNames = request.getHeaderNames(); while
	 * (headerNames.hasMoreElements()) { String key = (String)
	 * headerNames.nextElement(); String value = request.getHeader(key);
	 * map.put(key, value); }
	 * 
	 * return map; }
	 */

	@GetMapping(path = "/account/error")
	public String demo() {
		throw new DemoNotWorkingException();
	}
}