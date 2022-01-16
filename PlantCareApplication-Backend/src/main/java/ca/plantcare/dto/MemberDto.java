package ca.plantcare.dto;
import java.util.Collections;
import java.util.List;

public class MemberDto {

	private String username;
	private String password;
	private String name;
    private String email;
    private String phoneNumber;
    private Integer numberOfPlants;
	private List<PlantDto> plantsDTO;
	

	public MemberDto() {
	}
	
    @SuppressWarnings("unchecked") // added
	public MemberDto(String username, String password, String name, String email, String phoneNumber,  Integer numberOfPlants) {
		this(username, password, name, email, phoneNumber, numberOfPlants, Collections.EMPTY_LIST);
	}
		
	public MemberDto(String username, String password, String name, String email, String phoneNumber, Integer numberOfPlants, List<PlantDto> arrayList) {
		this.username = username;
		this.password = password;
		this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber
		this.this.numberOfPlants = numberOfPlants;
        this.plantsDTO = arrayList;
	}
		
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getName() {
		return name;
	}

    public String getEmail() {
		return email;
	}

    public String getPhoneNumber() {
		return phoneNumber;
	}

    public Integer getNumberOfPlants() {
		return numberOfPlants;
	}

    public List<PlantDto> getPlants() {
		return plantsDTO;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setName(String name) {
		this.name = name;
	}

    public void setEmail(String email) {
		this.email = email;
	}

    public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public void setNumberOfPlants(Integer numberOfPlants) {
		this.numberOfPlants = numberOfPlants;
	}

    public void setPlants(List<PlantDto> arrayList) {
		this.plantsDTO = arrayList;
	}
	
}