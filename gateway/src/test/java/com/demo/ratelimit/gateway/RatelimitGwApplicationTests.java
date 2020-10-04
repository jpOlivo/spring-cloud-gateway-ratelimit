package com.demo.ratelimit.gateway;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.reactive.server.WebTestClient;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 8091)
@Slf4j
public class RatelimitGwApplicationTests {

	@Autowired
	private WebTestClient webClient;

	@BeforeAll
	public static void init() {

		// Stubbing WireMock
		stubFor(get(urlEqualTo("/account")).willReturn(aResponse().withHeader("Content-Type", "application/json")
				.withBody("{\"id\":1,\"number\":\"1234567890\"}")));

	}

	@RepeatedTest(600)
	public void testAccountServiceMvc() {
		webClient.get().uri("/mvc/account").exchange()
				// .expectStatus().isOk()
				.expectBody()
				// .jsonPath("$.number").isEqualTo("1234567890")
				.consumeWith(response -> log.info("Received: status->{}, remaining->{}", response.getStatus(),
						response.getResponseHeaders().get("X-RateLimit-Remaining")));

	}

}
