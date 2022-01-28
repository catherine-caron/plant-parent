package ca.plantcare.dto;
import java.util.Collections;
import java.util.List;

public class MemberDto {

	private String username;
	private String name;
    private Integer numberOfPlants;
	private List<PlantDto> plantsDTO;
	

	public MemberDto() {
	}
	
    @SuppressWarnings("unchecked") // added
	public MemberDto(String username,  String name, Integer numberOfPlants) {
		this(username, name, numberOfPlants, Collections.EMPTY_LIST);
	}
		
	public MemberDto(String username, String name,  Integer numberOfPlants, List<PlantDto> arrayList) {
		this.username = username;
		this.name = name;
        // this.phoneNumber = phoneNumber;
		this.numberOfPlants = numberOfPlants;
      //  this.plantsDTO = arrayList; error on this line
	}
		
	public String getUsername() {
		return username;
	}
	
	public String getName() {
		return name;
	}

    // public String getEmail() {
	// 	return email;
	// }

    // public String getPhoneNumber() {
	// 	return phoneNumber;
	// }

    public Integer getNumberOfPlants() {
		return numberOfPlants;
	}

    public List<PlantDto> getPlants() {
		return plantsDTO;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}

    // public void setEmail(String email) {
	// 	this.email = email;
	// }

    // public void setPhoneNumber(String phoneNumber) {
	// 	this.phoneNumber = phoneNumber;
	// }
	
	public void setNumberOfPlants(Integer numberOfPlants) {
		this.numberOfPlants = numberOfPlants;
	}

    public void setPlants(List<PlantDto> arrayList) {
		this.plantsDTO = arrayList;
	}
	
}