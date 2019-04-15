package com.mediastore.audio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.mediastore.audio.exception.RestTemplateResponseErrorHandler;

/**
 * @author mbgangurde
 *
 */

@SpringBootApplication
public class AudioApplication {

	public static void main(String[] args) {
		SpringApplication.run(AudioApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
		return restTemplate;
	}
}
