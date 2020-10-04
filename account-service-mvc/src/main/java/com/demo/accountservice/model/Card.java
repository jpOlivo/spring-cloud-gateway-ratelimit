package com.demo.accountservice.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Card {
	private String number;
	private String cvv;

	@JsonProperty("expiration_date")
	private LocalDate expirationDate;
}
