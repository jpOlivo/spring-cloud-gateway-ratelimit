package com.demo.ratelimit.gateway.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.reactive.function.client.WebClient;

import com.demo.ratelimit.gateway.client.model.Account;

@SpringBootApplication
@EnableScheduling
public class ClientApplication {

	@Autowired
	private WebClient webClient;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ClientApplication.class, args);
	}

	@Scheduled(fixedRate = 100)
	public void run() {
		webClient.get().uri("/mvc/account/{id}", 1).retrieve().bodyToMono(Account.class).subscribe(System.out::println);
	}

}