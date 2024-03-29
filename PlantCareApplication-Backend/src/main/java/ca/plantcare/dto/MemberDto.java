package ca.plantcare.dto;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import ca.plantcare.models.Member;
import ca.plantcare.models.Plant;

public class MemberDto {

	private String username;
	private String name;
	private String email;
	private String password;
    private Integer numberOfPlants;
	private List<Plant> plants;
	private int token;
	

	public MemberDto() {
	}
	
   @SuppressWarnings("unchecked") // added
	public MemberDto( String password,String username,  String name, String email) {
		this( password,username, name, email, Collections.EMPTY_LIST);
	} 
		
	public MemberDto(String password, String username, String name, String email, List<Plant> plants) {
		this.username = username;
		this.name = name;
		this.email= email;
		this.password = password;
		this.plants = plants;
		if (plants != null) {
		//List<Integer> aPlantsDto = plants.stream().map(plant -> plant.getPlantId()).collect(Collectors.toList());
		this.setPlants(plants);
		}
		
        // this.phoneNumber = phoneNumber;
		// this.numberOfPlants = numberOfPlants;
       // this.plantsDTO = arrayList; //error on this line
	}
	
	public static MemberDto convertToDto(Member member) {
		MemberDto memberDto = new MemberDto(
				member.getPassword(),
				member.getUsername(),
				member.getName(),
				member.getEmail(),
				// member.getNumberOfPlants(),
				member.getPlant());
		return memberDto;
		
	}
		
	public String getUsername() {
		return username;
	}
	
	public String getName() {
		return name;
	}

    // public String getPhoneNumber() {
	// 	return phoneNumber;
	// }

    public Integer getNumberOfPlants() {
		return numberOfPlants;
	}

	
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}

    // public void setPhoneNumber(String phoneNumber) {
	// 	this.phoneNumber = phoneNumber;
	// }
	
	public void setNumberOfPlants(Integer numberOfPlants) {
		this.numberOfPlants = numberOfPlants;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the plants
	 */
	public List<Plant> getPlants() {
		return plants;
	}

	/**
	 * @param plants the plants to set
	 */
	public void setPlants(List<Plant> plants) {
		this.plants = plants;
	}

	public int getToken() {
		return token;
	}

	public void setToken(int token) {
		this.token = token;
	}
	
}