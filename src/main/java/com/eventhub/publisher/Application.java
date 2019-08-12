package com.eventhub.publisher;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
			SpringApplication.run(Application.class, args);
				/*SpringApplication app = new SpringApplication(Application.class);

        app.setDefaultProperties(Collections
                .singletonMap("server.port", "8084"));
              app.run(args);*/
    }

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
