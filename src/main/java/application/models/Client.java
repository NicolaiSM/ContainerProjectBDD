package application.models;

import java.util.*;

import javax.persistence.*;

import application.data.Element;
import application.data.QueryHashSet;
import application.data.QueryLinkedList;

public class Client extends User {
	private Collection<Container> clientContainers = new QueryLinkedList<Container>();
	private Collection<Journey> clientJourneys = new QueryHashSet<Journey>();
	
	public Client() {
		
	}
	
	public Client(String clientName) {
		userInfo.put("clientName",clientName);

	}
	
	public Client(String clientName, String address, String contactPerson, String email, String password) {
		super();
		userInfo.put("clientName",clientName);
		userInfo.put("address", address);
		userInfo.put("contactPerson", contactPerson);
		userInfo.put("email", email);
		userInfo.put("password",password);
	}

	
	public Collection<Container> getClientContainers() {
		return clientContainers;
	}

	public void setClientInfo(String key, String value) {
		userInfo.put(key, value);		
	}
	@Override
	public int hashCode() {
		return get("clientName").hashCode();
	}

	@Override
	public boolean equals(Object user) {
		return get("clientName").equals(((User) user).get("clientName"));	
	}
	
	public void addJourney(Journey journey) {
		clientJourneys.add(journey);
	}

	public Collection<Journey> getClientJourneys() {
		return clientJourneys;
	}
	
	
	
}
