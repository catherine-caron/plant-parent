package ca.plantcare.models;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table (name = "plant")
@Entity
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
	//private List <BloomTime> bloomTime=new ArrayList<>();
	
	private Integer icon;
	private SunExposure sunExposure;
	private SoilType soilType;
	private Toxicity toxicity;
	private BloomTime bloomTime;
	private String givenName;
	private String botanicalName;
	private String commonName;
	private Integer plantId;
	private Integer id;
	private Integer addedPlantId;
	private Integer originalPlantId;
	private Date lastWateredDate;
	private Time lastWateredTime;

	
	// private Member member;
	private WateringSchedule wateringRecommendation;
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
	}
	

	
	public List<BloomTime> getBloomTime() {
		return this.bloomTime;
	}
	
	public void setBloomTime (List<BloomTime> aBloomTime) {
		this.bloomTime = aBloomTime;
	}*/

	@OneToOne(cascade = CascadeType.ALL)
	public WateringSchedule getWateringRecommendation() {
		return wateringRecommendation;
	}

	//@OneToOne(cascade = CascadeType.ALL)
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


	/**
	 * @return the bloomtime
	 */
	public BloomTime getBloomtime() {
		return bloomTime;
	}


	/**
	 * @param bloomtime the bloomtime to set
	 */
	public void setBloomtime(BloomTime bloomtime) {
		this.bloomTime = bloomtime;
	}


	// /**
	//  * @return the member
	//  */
	// @ManyToOne(cascade = CascadeType.ALL)
	// public Member getMember() {
	// 	return member;
	// }


	// /**
	//  * @param member the member to set
	//  */
	// public void setMember(Member member) {
	// 	this.member = member;
	// }


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


	/**
	 * @return the originalPlantId
	 */
	public Integer getOriginalPlantId() {
		return originalPlantId;
	}


	/**
	 * @param originalPlantId the originalPlantId to set
	 */
	public void setOriginalPlantId(Integer originalPlantId) {
		this.originalPlantId = originalPlantId;
	}


	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * 
	 * @return date of last water
	 */
	public Date getLastWateredDate() {
		return lastWateredDate;
	}

	/**
	 * 
	 * @param lastWatereDate 
	 */
	public void setLastWateredDate(Date lastWateredDate) {
		this.lastWateredDate = lastWateredDate;
	}

	/**
	 * 
	 * @return time of last water
	 */
	public Time getLastWateredTime() {
		return lastWateredTime;
	}

	/**
	 * 
	 * @param lastWateredTime
	 */
	public void setLastWateredTime(Time lastWateredTime) {
		this.lastWateredTime = lastWateredTime;
	}

}
