package ca.plantcare.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.*;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@Entity
public class Member {

	private Integer numberOfPlants;
	 private String email;
	 private String password;
	private String name;
	// private String phoneNumber;
	private String username;
	private List<Plant> plant; 
	private int token; // for login
	
	public Integer getNumberOfPlants() {
		return numberOfPlants;
	}
	public void setNumberOfPlants(Integer numberOfPlants) {
		this.numberOfPlants = numberOfPlants;
	}
	 public String getEmail() {
	 	return email;
	 }
	 public void setEmail(String email) {
	 	this.email = email;
	 }
	 public String getPassword() {
	 	return password;
	 }
	 public void setPassword(String password) {
	 	this.password = password;
	 }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	// public String getPhoneNumber() {
	// 	return phoneNumber;
	// }
	// public void setPhoneNumber(String phoneNumber) {
	// 	this.phoneNumber = phoneNumber;
	// }

	@Id
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	@OneToMany(cascade = {CascadeType.ALL})
	public List<Plant> getPlant()
	{
	  return this.plant;
	}
	
	public void setPlant(List<Plant> aPlant) {
		this.plant = aPlant;
	}

	public void setToken(int token) {
		this.token = token;
	}
  
	public int getToken() {
		return this.token;
	}

	
}
