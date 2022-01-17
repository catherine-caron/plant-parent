package ca.plantcare.models;

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
	private List <SunExposure> sunExposure;
	private List <SoilType> soilType;
	private List <Toxicity> toxicity;
	private List <BloomTime> bloomTime;
	/*TO ADD?*/ private List <BloomTime> wateringRecommendation;
	private Integer icon;
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
	
	public List<SunExposure> getSunExposure() {
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
	}
	
	public List<BloomTime> getBloomTime() {
		return this.bloomTime;
	}
	
	public void setBloomTime (List<BloomTime> aBloomTime) {
		this.bloomTime = aBloomTime;
	}


	public List <BloomTime> getWateringRecommendation() {
		return wateringRecommendation;
	}


	public void setWateringRecommendation(List <BloomTime> wateringRecommendation) {
		this.wateringRecommendation = wateringRecommendation;
	}
	

}
