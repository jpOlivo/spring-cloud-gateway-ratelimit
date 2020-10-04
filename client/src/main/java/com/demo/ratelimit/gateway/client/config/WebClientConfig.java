package com.demo.ratelimit.gateway.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
	
	@Value("${gateway.baseurl:http://localhost:8080}")
	private String gatewayBaseUrl;
	
	@Bean
	WebClient webClient() {
		return WebClient.builder().baseUrl(gatewayBaseUrl).build();
	}
}
