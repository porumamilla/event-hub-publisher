package com.eventhub.publisher.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationProperties("eventhub.gcp")
public class ConfigProperties {

	private String project;
    private String topic;
    private String firestoreWorkspace;
	
	public String getProject() {
		return project;
    }
    
	public String getTopic() {
		return topic;
    }

    public String getFirestoreWorkspace() {
		return firestoreWorkspace;
	}
    
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
	public String localGcpProperties() {
		System.out.println("daoApiEndpoint == " + daoApiEndpoint);
		System.out.println("schemaApiEndpoint == " + schemaApiEndpoint);
		System.out.println("project == " + project);
        System.out.println("topic == " + topic);
        System.out.println("firestoreWorkspace == " + firestoreWorkspace);
		return "GCP properties - Local"; 
	}
	
	@Profile("gcp")
	@Bean
	public String gcpProperties() {
		System.out.println("daoApiEndpoint == " + daoApiEndpoint);
		System.out.println("schemaApiEndpoint == " + schemaApiEndpoint);
		System.out.println("project == " + project);
        System.out.println("topic == " + topic);
        System.out.println("firestoreWorkspace == " + firestoreWorkspace);
		return "GCP properties - GCP"; 
	}
}
