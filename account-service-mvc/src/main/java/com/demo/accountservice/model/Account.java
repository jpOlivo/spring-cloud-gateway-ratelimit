package com.demo.accountservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Account {
	private long id;
	private String number;
	private Card card;
}
