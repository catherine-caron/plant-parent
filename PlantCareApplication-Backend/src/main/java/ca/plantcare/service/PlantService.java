package ca.plantcare.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.plantcare.dao.MemberRepository;
import ca.plantcare.models.Member;
import ca.plantcare.dao.PlantRepository;
import ca.plantcare.models.Plant;
import ca.plantcare.models.Plant.BloomTime;
import ca.plantcare.models.Plant.SoilType;
import ca.plantcare.models.Plant.SunExposure;
import ca.plantcare.models.Plant.Toxicity;

import org.springframework.transaction.annotation.Transactional;

@Service
public class PlantService {

    @Autowired
	private MemberRepository memberRepository;
	@Autowired
	private PlantRepository plantRepository;


	/**
     * Find all plants owned by specific member
	 * @param memberUsername
	 * @return
	 */
	@Transactional
	public List<Plant> getPlantsByMember(String memberUsername){
		List<Member> members = toList(memberRepository.findAll());
		for (Member member:members) {
			if (member.getUsername().equals(memberUsername)) {
				return member.getPlant();
			}
		}
		throw new IllegalArgumentException("Username not found.");
	}
	
	@Transactional
	public Plant getPlantByBotanicalName(String botanicalName) {
		return null;
		
	}
	
	@Transactional
	public Plant getPlantByGivenName(String botanicalName) {
		return null;
		
	}
	
	@Transactional
	public List<Plant> getPlantBySunExposure(String memberUsername){
		return null;
	}
	
	/**
     * Find specific plant by its id. Admin function needed to assign right id to plants
	 * @param plantId
	 * @return plant
	 */
	@Transactional
	private Plant getPlantByPlantId(Integer plantId){
		if (plantId == null || plantId < 0) {
			throw new IllegalArgumentException("Id must not be null.");
		}
		Plant plant = plantRepository.findPlantByPlantId(plantId);
		if (plant == null) {
			throw new IllegalArgumentException("Cannot find plant with id " + plantId + " in repository.");
		}
		return plant;
		
	}
	
	//create plant by admin needs an admin member id
	public Plant createPlant(Integer icon, String givenName, String botanicalName,
			String commonName,List <SunExposure> sunExposure,List <SoilType> soilType,
			List <Toxicity> toxicity, List <BloomTime> bloomTime, List <BloomTime> wateringRecommendation){
		
		if (icon == null ||  icon.equals("undefined")) {
            throw new IllegalArgumentException("Icon cannot be null or empty");
        }
		
		if (givenName == null || givenName == "" ) {
            throw new IllegalArgumentException("Given Name cannot be null or empty");
		}
		
		if (botanicalName == null || botanicalName == "") {
	            throw new IllegalArgumentException("Botanical Name cannot be null or empty");
	    }
		
		if (commonName == null || commonName == "" ) {
            throw new IllegalArgumentException("Common Name cannot be null or empty");
		}
		if (sunExposure == null ) {
            throw new IllegalArgumentException("Sun Exposure cannot be null or empty");
		}
		if (toxicity == null ) {
            throw new IllegalArgumentException("Toxicity cannot be null or empty");
		}
		if (bloomTime == null ) {
            throw new IllegalArgumentException("Bloom Time cannot be null or empty");
		}
		if (soilType == null ) {
            throw new IllegalArgumentException("Soil Type cannot be null or empty");
		}
		if (wateringRecommendation == null ) {
            throw new IllegalArgumentException("Watering Recommendation cannot be null or empty");
		} //need expection for wrong inputs
		
		int leftLimit = 000001;
		int rightLimit = 000100;
	    int plantId = leftLimit + (int) (Math.random() * (rightLimit - leftLimit));
		Plant plant = new Plant();
		plant.setBloomTime(bloomTime);
		plant.getBotanicalName();
		plant.setCommonName(commonName);
		plant.setGivenName(givenName);
		plant.setIcon(icon);
		plant.setSunExposure(sunExposure);
		plant.setSoilType(soilType);
		plant.setToxicity(toxicity);
		plant.setWateringRecommendation(wateringRecommendation);
		plant.setPlantId(plantId);
		plantRepository.save(plant);
		return plant;
		
	}
	
	/**
     * Add plant from database with its information onto the member's profile. 
     * No changes done from the database. Will be assigned a new plantid
	 * @param 
	 * @return plant
	 */
	public Plant addPlant(Integer plantId, Integer memberId){
		Plant plant = plantRepository.findPlantByPlantId(plantId);
		int leftLimit = 000101; //starting after the original plantid
		int rightLimit = 999999;
	    int plantIdNew = leftLimit + (int) (Math.random() * (rightLimit - leftLimit));
		Plant newPlant = plant;
		newPlant.setPlantId(plantIdNew); //idk if this works
		plantRepository.save(newPlant);
	    Member member = memberRepository.findMemberbyMemberId(memberId);
	    member.getPlant().add(newPlant);
	    memberRepository.save(member);
	    return newPlant;
		
	}
	
	/**
     * Change plant that was added  from database.
     * Might be split into different Methods such as updateplanttoxicity since there
     * are many exceptions for each.
	 * @param plantId
	 * @return plant
	 */
	public Plant updatePlant(Integer plantId ){
		Plant plant = plantRepository.findPlantByPlantId(plantId);
		updatePlantToxicity(plantId);
		updatePlantSun( plantId );
		updatePlantSoil( plantId );
		return plant;
		
	}
	
	/**
     * Delete Plant from Database and MemberProfile
	 * @param plantId
	 * @return plant
	 */
	public Plant deletePlant(Integer plantId ){
		Plant plant = plantRepository.findPlantByPlantId(plantId);
		if (plant == null) {
			throw new IllegalArgumentException("Loan does not exist");
		}
		deletePlantForMember( plantId );
		plantRepository.delete(plant);
		return plant;
		
	}
	
	
								/* HELPER METHODS*/
			
	
	public Plant updatePlantToxicity(Integer plantId ){
		Plant plant = plantRepository.findPlantByPlantId(plantId);
		return plant;
		
	}
	
	public Plant updatePlantSoil(Integer plantId ){
		Plant plant = plantRepository.findPlantByPlantId(plantId);
		return plant;
		
	}
	
	public Plant updatePlantSun(Integer plantId ){
		Plant plant = plantRepository.findPlantByPlantId(plantId);
		return plant;
		
	}
	
	public Member getMemberbyPlantId(Integer plantId ){
		
		Plant plant = plantRepository.findPlantByPlantId(plantId);
		List<Member> members = toList(memberRepository.findAll());
		for (Member member:members) {
			if (member.getPlant() ==plant ) {
				return member;
			}
		}
		throw new IllegalArgumentException("Member not found.");
	}
	
	public Plant deletePlantForMember(Integer plantId ){
		
		Plant plant = plantRepository.findPlantByPlantId(plantId);
		List<Member> members = toList(memberRepository.findAll());
		int counter =0;
		for (Member member:members) {
			counter ++;
			if (member.getPlant() ==plant ) {
				member.getPlant().remove(counter);
			}
		}
		throw new IllegalArgumentException("Member not found.");
	}
	
	
	
	/**
     * Helper method that converts iterable to list
	 * @param <T>
	 * @param iterable
	 * @return
	 */
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
