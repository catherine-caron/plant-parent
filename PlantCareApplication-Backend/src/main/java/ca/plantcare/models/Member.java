package ca.plantcare.models;

public class Member {

	private Integer memberId; //not sure if we're keeping it in the databse
	private Integer numberOfPlants;
	private String email;
	private String password;
	private String fullName;
	private String phoneNumber;
	private String userName;
	
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
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	//@Id
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	
}
