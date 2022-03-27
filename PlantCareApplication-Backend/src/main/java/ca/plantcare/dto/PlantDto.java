package ca.plantcare.dto;

import ca.plantcare.models.WateringSchedule;

import java.util.List;
import java.util.stream.Collectors;

import ca.plantcare.models.Member;
import ca.plantcare.models.Plant;
import ca.plantcare.models.Plant.BloomTime;
import ca.plantcare.models.Plant.SoilType;
import ca.plantcare.models.Plant.SunExposure;
import ca.plantcare.models.Plant.Toxicity;

public class PlantDto {

	private Integer icon;
	private SunExposure sunExposure;
	private SoilType soilType;
	private Toxicity toxicity;
	private BloomTime bloomTime;
	private String givenName;
	private String botanicalName;
	private String commonName;
	private Integer plantId;
	private WateringSchedule wateringSchedule;
	// private Member member;
	private String memberId;
	private Integer addedPlantId;
	//default constructor
	public PlantDto(){
		
	}
	
	public PlantDto (Integer icon, String givenName, String botanicalName, String commonName,
			SunExposure sunExposure, SoilType soilType, Toxicity toxicity, BloomTime bloomTime,
			WateringSchedule wateringRecommendation,Integer plantId, Integer addedPlantId) {
		this.bloomTime = bloomTime;
		this.botanicalName = botanicalName;
		this.commonName = commonName;
		this.icon = icon;
		this.soilType = soilType;
		this.sunExposure = sunExposure;
		this.toxicity = toxicity;
		this.wateringSchedule = wateringRecommendation;
		this.setAddedPlantId(addedPlantId);
		// this.member = member;
		// if (member!= null) {
		// this.memberId = member.getUsername();}
		
		if(plantId ==null) { //admin plant used to also have: ( memberId == null || )
			this.plantId = plantId;
			//this.member = memberId;
		}
		else {
			this.givenName = givenName;
		}
	}
	public static PlantDto convertToDto(Plant plant) {
		PlantDto plantDto = new PlantDto(plant.getIcon(),
				plant.getGivenName(),
				plant.getBotanicalName(),
				plant.getCommonName(),
				plant.getSunExposure(),
				plant.getSoilType(),
				plant.getToxicity(),
				plant.getBloomtime(),
				plant.getWateringRecommendation(),
				plant.getPlantId(),
				// plant.getMember(),
				plant.getAddedPlantId())
				;
		
		return plantDto;
		
	}
	
	public static List<PlantDto> convertToDtos(List<Plant> plants) {
		List<PlantDto> plantDtos = plants.stream().map(l -> PlantDto.convertToDto(l)).collect(Collectors.toList());
		return plantDtos;
	}


	/**
	 * @return the icon
	 */
	public Integer getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(Integer icon) {
		this.icon = icon;
	}

	/**
	 * @return the sunExposure
	 */
	public SunExposure getSunExposure() {
		return sunExposure;
	}

	/**
	 * @param sunExposure the sunExposure to set
	 */
	public void setSunExposure(SunExposure sunExposure) {
		this.sunExposure = sunExposure;
	}

	/**
	 * @return the soilType
	 */
	public SoilType getSoilType() {
		return soilType;
	}

	/**
	 * @param soilType the soilType to set
	 */
	public void setSoilType(SoilType soilType) {
		this.soilType = soilType;
	}

	/**
	 * @return the toxicity
	 */
	public Toxicity getToxicity() {
		return toxicity;
	}

	/**
	 * @param toxicity the toxicity to set
	 */
	public void setToxicity(Toxicity toxicity) {
		this.toxicity = toxicity;
	}

	/**
	 * @return the bloomTime
	 */
	public BloomTime getBloomTime() {
		return bloomTime;
	}

	/**
	 * @param bloomTime the bloomTime to set
	 */
	public void setBloomTime(BloomTime bloomTime) {
		this.bloomTime = bloomTime;
	}

	/**
	 * @return the givenName
	 */
	public String getGivenName() {
		return givenName;
	}

	/**
	 * @param givenName the givenName to set
	 */
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	/**
	 * @return the botanicalName
	 */
	public String getBotanicalName() {
		return botanicalName;
	}

	/**
	 * @param botanicalName the botanicalName to set
	 */
	public void setBotanicalName(String botanicalName) {
		this.botanicalName = botanicalName;
	}

	/**
	 * @return the commonName
	 */
	public String getCommonName() {
		return commonName;
	}

	/**
	 * @param commonName the commonName to set
	 */
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	/**
	 * @return the plantId
	 */
	public Integer getPlantId() {
		return plantId;
	}

	/**
	 * @param plantId the plantId to set
	 */
	public void setPlantId(Integer plantId) {
		this.plantId = plantId;
	}

	/**
	 * @return the wateringRecommendation
	 */
	public WateringSchedule getWateringRecommendation() {
		return wateringSchedule;
	}

	/**
	 * @param wateringRecommendation the wateringRecommendation to set
	 */
	public void setWateringRecommendation(WateringSchedule wateringRecommendation) {
		this.wateringSchedule = wateringRecommendation;
	}

	/**
	 * @return the addedPlantId
	 */
	public Integer getAddedPlantId() {
		return addedPlantId;
	}

	/**
	 * @param addedPlantId the addedPlantId to set
	 */
	public void setAddedPlantId(Integer addedPlantId) {
		this.addedPlantId = addedPlantId;
	}
	
}
