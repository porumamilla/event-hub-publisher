package com.eventhub.publisher.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationProperties("evenhub.rest.client")
public class ApiEndPointUri {

	private String daoApiEndpoint;
	private String schemaApiEndpoint;
	
	public String getDaoApiEndpoint() {
		return daoApiEndpoint;
	}
	public void setDaoApiEndpoint(String daoApiEndpoint) {
		this.daoApiEndpoint = daoApiEndpoint;
	}

	public String getSchemaApiEndpoint() {
		return schemaApiEndpoint;
	}
	public void setSchemaApiEndpoint(String schemaApiEndpoint) {
		this.schemaApiEndpoint = schemaApiEndpoint;
	}
	
	@Profile("local")
	@Bean
	public String localRestClientProperties() {
		
		System.out.println("daoApiEndpoint == " + daoApiEndpoint);
		System.out.println("schemaApiEndpoint == " + schemaApiEndpoint);
		return "Rest Client properties - Local"; 
	}
	
	@Profile("gcp")
	@Bean
	public String gcpRestClientProperties() {
		
		System.out.println("daoApiEndpoint == " + daoApiEndpoint);
		System.out.println("schemaApiEndpoint == " + schemaApiEndpoint);
		return "Rest Client properties - GCP"; 
	}
}
