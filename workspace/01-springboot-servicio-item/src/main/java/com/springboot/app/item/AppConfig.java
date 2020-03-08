package com.springboot.app.item;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {
	
	@Bean(name = "clientRest")
	public WebClient registrarRestTemplate() {
		return WebClient.create("http://localhost:8001");
	}

}
