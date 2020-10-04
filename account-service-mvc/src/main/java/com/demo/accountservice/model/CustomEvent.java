package com.demo.accountservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomEvent {
	private String globalTransactionId;
	private String userMail;
	private String data;

}
