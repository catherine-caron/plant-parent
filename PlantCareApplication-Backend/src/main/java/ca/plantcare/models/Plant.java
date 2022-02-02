package ca.plantcare.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class Plant {

	public enum SunExposure {
		FullSun, PartSun, PartShade, Shade
	}
	public enum SoilType {
		Clay, Sandy, Silty, Peaty, Chalky, Loamy
	}
	public enum Toxicity {
		NonToxic, MinorToxicity, MajorToxicity, Oxalates, Dermatitis
	}
	public enum BloomTime {
		None, Winter, Spring, Summer, Fall 
	}
	//private List <SunExposure> sunExposure= new ArrayList<>();
	//private List <SoilType> soilType=new ArrayList<>();
	//private List <Toxicity> toxicity=new ArrayList<>();
	private List <BloomTime> bloomTime=new ArrayList<>();
	private WateringSchedule wateringRecommendation;
	private Integer icon;
	private SunExposure sunExposure;
	private SoilType soilType;
	private Toxicity toxicity;
	private String givenName;
	private String botanicalName;
	private String commonName;
	private Integer plantId;
	
	/**
	 * Default constructor.
	 */
	public Plant() {}
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getPlantId() {
		return plantId;
	}

	public void setPlantId(Integer plantId) {
		this.plantId = plantId;
	}
	
	public Integer getIcon() {
		return icon;
	}

	public void setIcon(Integer icon) {
		this.icon = icon;
	}
	

	public void setGivenName(String aGivenName) {
		this.givenName = aGivenName; 
	}
	
	public String getGivenName() {
		return givenName;
	}
	
	public void setBotanicalName(String abotanicalName) {
		this.botanicalName = abotanicalName; 
	}
	
	public String getBotanicalName() {
		return botanicalName;
	}
	
	public void setCommonName(String aCommonName) {
		this.commonName = aCommonName; 
	}
	
	public String getCommonName() {
		return commonName;
	}
	
	/*public List<SunExposure> getSunExposure() {
		return this.sunExposure;
	}
	
	public void setSunExposure (List<SunExposure> aSunExposure) {
		this.sunExposure = aSunExposure;
	}
	
	public List<SoilType> getSoilType() {
		return this.soilType;
	}
	
	public void setSoilType (List<SoilType> aSoilType) {
		this.soilType = aSoilType;
	}
	
	public List<Toxicity> getToxicity() {
		return this.toxicity;
	}
	
	public void setToxicity (List<Toxicity> aToxicity) {
		this.toxicity = aToxicity;
	}*/
	

	
	public List<BloomTime> getBloomTime() {
		return this.bloomTime;
	}
	
	public void setBloomTime (List<BloomTime> aBloomTime) {
		this.bloomTime = aBloomTime;
	}


	public WateringSchedule getWateringRecommendation() {
		return wateringRecommendation;
	}


	public void setWateringRecommendation(WateringSchedule wateringRecommendation) {
		this.wateringRecommendation = wateringRecommendation;
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
	

}
