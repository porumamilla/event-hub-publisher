package com.eventhub.publisher.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.eventhub.dao.model.EventDefinition;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;

@RestController
public class PublisherController {

	@Autowired
	RestTemplate restTemplate;
	private String daoApiEndpoint = "http://localhost:8081";
	private static final String PROJECT_ID = "event-hub-249001";
	private static final String TOPIC_ID = "consumer-events";
	private static final String WORKSPACE = "DEV";
	private String schemaApiEndpoint = "http://localhost:8083";
	private Publisher publisher = null;
	
	@PostConstruct
	public void init() throws Exception {
		ProjectTopicName topicName = ProjectTopicName.of(PROJECT_ID, TOPIC_ID);
		publisher = Publisher.newBuilder(topicName).build();
	}
	
	@PreDestroy
	public void cleanup() {
		if (publisher != null) {
			publisher.shutdown();
		}
	}
	
	@RequestMapping(value = "/publish", method = RequestMethod.POST)
	public void publish(@RequestParam(name="jsonData") String body) throws Exception {

		List<ApiFuture<String>> futures = new ArrayList<>();
		try {
			System.out.println("body == " + body);
			JsonElement jsonData = new JsonParser().parse(body);
			JsonObject jsonObject = jsonData.getAsJsonObject();
			String eventName = jsonObject.get("name").getAsString();
			String orgId = jsonObject.get("orgId").getAsString();
			EventDefinition definition = restTemplate.exchange(daoApiEndpoint + "/organization/eventDefinition?eventName=" + 
					eventName + "&orgId=" + orgId + "&workspace=" + WORKSPACE, HttpMethod.GET, null,
					new ParameterizedTypeReference<EventDefinition>() {
					}).getBody();
			
			HttpHeaders headers1 = new HttpHeaders();
			headers1.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			
			MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
			map.add("jsonSchema",  definition.getSchema());
			map.add("jsonData",  body);
			
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers1);

			ResponseEntity<String> response = restTemplate.postForEntity( schemaApiEndpoint + "/validate", request , String.class );
			
			if (!response.getStatusCode().equals(HttpStatus.OK)) {
				throw new RuntimeException(response.getBody());
			}
			
			// convert message to bytes
			ByteString data = ByteString.copyFromUtf8(body);
			PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();

			// Schedule a message to be published. Messages are automatically batched.
			ApiFuture<String> future = publisher.publish(pubsubMessage);
			futures.add(future);

		} finally {
			// Wait on any pending requests
			List<String> messageIds = ApiFutures.allAsList(futures).get();

			for (String messageId : messageIds) {
				System.out.println(messageId);
			}
		}
	}
}
