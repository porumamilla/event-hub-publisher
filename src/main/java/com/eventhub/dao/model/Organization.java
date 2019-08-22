package com.eventhub.dao.model;

import java.util.List;
import java.util.Map;

public class Organization {
    private String id;
	private String name;
	private String address;
	private String address2;
	private String city;
	private String country;
	private String state;
	private String postalCode;
	private List<Map<String, String>> sourceTypes;
	
	public List<Map<String, String>> getSourceTypes() {
		return sourceTypes;
	}

	public void setSourceTypes(List<Map<String, String>> sourceTypes) {
		this.sourceTypes = sourceTypes;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
