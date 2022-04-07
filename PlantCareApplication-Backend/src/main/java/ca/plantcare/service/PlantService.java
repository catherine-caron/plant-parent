package ca.plantcare.service;


import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ca.plantcare.dao.MemberRepository;
import ca.plantcare.models.Member;
import ca.plantcare.dao.PlantRepository;
import ca.plantcare.dao.WateringScheduleRepository;
import ca.plantcare.dao.ReminderRepository;
import ca.plantcare.models.Reminder;
import ca.plantcare.models.Plant;
import ca.plantcare.models.WateringSchedule;
import ca.plantcare.models.Plant.BloomTime;
import ca.plantcare.models.Plant.SoilType;
import ca.plantcare.models.Plant.SunExposure;
import ca.plantcare.models.Plant.Toxicity;

import ca.plantcare.service.*; // automatically create watering schedule and reminder when creating a plant

import org.springframework.transaction.annotation.Transactional;

@Service
public class PlantService {

	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private PlantRepository plantRepository;
	@Autowired
	private ReminderRepository reminderRepository;
	@Autowired
	private WateringScheduleRepository wateringScheduleRepository;

	/**
	 * Find all plants owned by specific member
	 * 
	 * @param memberUsername
	 * @return
	 */
	@Transactional
	public List<Plant> getPlantsByMember(String memberUsername) {
		Member member = memberRepository.findMemberByUsername(memberUsername);
		if (memberUsername == null) {
			throw new IllegalArgumentException("Member not found.");
		} else {
			return member.getPlant();
		}
	}

	@Transactional
	public Plant getPlantByBotanicalName(String botanicalName) {
		if (botanicalName == null) {
			throw new IllegalArgumentException("Plant not found.");
		}
		Plant plant = plantRepository.findPlantByBotanicalName(botanicalName);
		return plant;

	}

	@Transactional
	public Plant getPlantByCommonName(String commonName) {
		if (commonName == null) {
			throw new IllegalArgumentException("Plant not found.");
		}
		Plant plant = plantRepository.findPlantByCommonName(commonName);
		return plant;

	}

	/*
	 * @Transactional public List<Plant> getPlantBySunExposure(String
	 * memberUsername){ return null; }
	 */

	/**
	 * Find specific plant by its id. Admin function needed to assign right id to
	 * plants
	 * 
	 * @param plantId
	 * @return plant
	 */
	@Transactional
	private Plant getPlantByPlantId(Integer plantId) {
		if (plantId == null || plantId < 0) {
			throw new IllegalArgumentException("Id must not be null.");
		}
		Plant plant = plantRepository.findPlantByPlantId(plantId);
		if (plant == null) {
			throw new IllegalArgumentException("Cannot find plant with id " + plantId + " in repository.");
		}
		return plant;

	}

	// create plant by admin needs an admin member id
	@Transactional
	public Plant createPlant(Integer icon, String botanicalName, String commonName,
			SunExposure sunExposure, SoilType soilType, Toxicity toxicity, BloomTime bloomTime,
			Integer hoursBetweenWatering) {

		if (icon == null || icon.equals("undefined")) {
			throw new IllegalArgumentException("Icon cannot be null or empty");
		}

	/*	if (memberRepository.findById(memberId) == null) {
			throw new IllegalArgumentException("Member not found");
		

		if (givenName == null || givenName == "") {
			throw new IllegalArgumentException("Given Name cannot be null or empty");
		}}*/

		if (botanicalName == null || botanicalName == "") {
			throw new IllegalArgumentException("Botanical Name cannot be null or empty");
		}

		if (commonName == null || commonName == "") {
			throw new IllegalArgumentException("Common Name cannot be null or empty");
		}
		if (sunExposure == null) {
			throw new IllegalArgumentException("Sun Exposure cannot be null or empty");
		}
		if (toxicity == null) {
			throw new IllegalArgumentException("Toxicity cannot be null or empty");
		}
		if (bloomTime == null) {
			throw new IllegalArgumentException("Bloom Time cannot be null or empty");
		}
		if (soilType == null) {
			throw new IllegalArgumentException("Soil Type cannot be null or empty");
		}
		if (hoursBetweenWatering == null) {
			throw new IllegalArgumentException("Watering Recommendation cannot be null or empty");
		} // need expection for wrong inputs

		int leftLimit = 000001;
		int rightLimit = 000100;
		int plantId = leftLimit + (int) (Math.random() * (rightLimit - leftLimit));
		if (plantId > 100) {
			throw new IllegalArgumentException("PlantId is too large. Contact Admin");
		} // need expection for wrong inputs

		Plant plant = new Plant();
		plant.setBloomtime(bloomTime);
		plant.getBotanicalName();
		plant.setCommonName(commonName);
		plant.setBotanicalName(botanicalName);
		//plant.setGivenName(givenName);
		plant.setIcon(icon);
		plant.setSunExposure(sunExposure);
		plant.setSoilType(soilType);
		plant.setToxicity(toxicity);

		// create a simple watering schedule
		WateringSchedule wateringSchedule = new WateringSchedule();
		int scheduleId = leftLimit + (int) (Math.random() * (rightLimit - leftLimit));
		wateringSchedule.setScheduleId(scheduleId);
		wateringSchedule.setHoursBetweenWatering(hoursBetweenWatering);
		wateringScheduleRepository.save(wateringSchedule);

		plant.setWateringRecommendation(wateringSchedule);
		plant.setPlantId(plantId);
		plantRepository.save(plant);

		// add to member's plants
		//Member member = memberRepository.findMemberByUsername(memberId);

		//member.setPlant(plants);
		//memberRepository.save(member);

		return plant;

	}

	/**
	 * Add plant from database with its information onto the member's profile. No
	 * changes done from the database. Will be assigned a new plantid
	 * 
	 * @param
	 * @return plant
	 */
	public Plant addPlant(Integer plantId, String memberId, String givenName) {
		/*
		 * if (plantId == null){ throw new IllegalArgumentException("Plant not found.");
		 * } if (memberId == null){ throw new
		 * IllegalArgumentException("Member not found."); }
		 */
		Plant plant = plantRepository.findPlantByPlantId(plantId);
		/*
		 * if (plant == null){ throw new IllegalArgumentException("Plant not found."); }
		 */
		int leftLimit = 000101; // starting after the original plantid
		int rightLimit = 999999;
		int plantIdNew = leftLimit + (int) (Math.random() * (rightLimit - leftLimit));
		/*
		 * if (!isIdAvailable(plantIdNew)) { throw new
		 * IllegalArgumentException("PlantId is Taken. Contact Admin"); }
		 */
		// Plant newPlant = plant;
		Plant newPlant = new Plant();
		newPlant.setAddedPlantId(plantIdNew);
		newPlant.setPlantId(plantIdNew);
		newPlant.setBloomtime(plant.getBloomtime());
		newPlant.setBotanicalName(plant.getBotanicalName());
		newPlant.setCommonName(plant.getCommonName());
		newPlant.setGivenName(givenName);
		newPlant.setIcon(plant.getIcon());
		// newPlant.setMember(memberRepository.findMemberByUsername(memberId));
		newPlant.setSoilType(plant.getSoilType());
		newPlant.setSunExposure(plant.getSunExposure());
		newPlant.setToxicity(plant.getToxicity());
		newPlant.setWateringRecommendation(plant.getWateringRecommendation());
		//newPlant.setPlantId(null); // idk if this works
		//newPlant.setAddedPlantId(plantIdNew);
		//newPlant.setMember(memberRepository.findMemberByUsername(memberId));
		plantRepository.save(newPlant);
		Member member = memberRepository.findMemberByUsername(memberId);
		member.getPlant().add(newPlant);
		memberRepository.save(member);
		/*memberUpdated.getPlant().add(newPlant);
		memberRepository.save(memberUpdated);*/
		List <Plant> plants = member.getPlant();
		plants.add(newPlant);
		
		return newPlant;

	}

	/**
	 * Change plant that was added from database. Might be split into different
	 * Methods such as updateplanttoxicity since there are many exceptions for each.
	 * 
	 * @param plantId
	 * @return plant
	 */
	@Transactional
	public Plant updatePlant(Integer plantId, String givenName, Integer icon, String botanicalName, String commonName,
			SunExposure sunExposure, SoilType soilType, Toxicity toxicity, BloomTime bloomTime,
			WateringSchedule wateringRecommendation) {
		if (plantId == null) {
			throw new IllegalArgumentException("Plant not found.");
		}
		if (givenName == null) {
			throw new IllegalArgumentException("Plant not found.");
		}
		Plant plant = plantRepository.findPlantByPlantId(plantId);
		// updatePlantToxicity(plantId);
		// updatePlantSun( plantId );
		// updatePlantSoil( plantId );
		plant.setBloomtime(bloomTime);
		plant.getBotanicalName();
		plant.setCommonName(commonName);
		plant.setGivenName(givenName);
		plant.setIcon(icon);
		plant.setSunExposure(sunExposure);
		plant.setSoilType(soilType);
		plant.setToxicity(toxicity);
		plant.setWateringRecommendation(wateringRecommendation);
		updateBloom(plantId, null);
		plant.setGivenName(givenName);
		plantRepository.save(plant);

		return plant;

	}

	/**
	 * Delete Plant from Database and MemberProfile
	 * 
	 * @param plantId
	 * @return plant
	 */
	@Transactional
	public Plant deletePlant(Integer plantId) {
		if (plantId == null) {
			throw new IllegalArgumentException("Plant not found.");
		}
		Plant plant = plantRepository.findPlantByPlantId(plantId);
		if (plant == null) {
			throw new IllegalArgumentException("Plant does not exist"); // @TODO loooooool
		}
		//plant.setMember(null);
		plant.setWateringRecommendation(null);
		
		//deletePlantForMember(plantId);
		
		plantRepository.delete(plant);
		return plant;

	}
	
	@Transactional
	public List<Plant>  getAllOriginalPlants() {
		List<Plant> allPlants = toList(plantRepository.findAll());
		List<Plant> goodPlants = new ArrayList<Plant>();
		for (int i=0; i<allPlants.size(); i++) {
			if (allPlants.get(i).getAddedPlantId() == null) {
				goodPlants.add(allPlants.get(i));
			}
		}
		return goodPlants;

	}
	
	@Transactional
	public Plant  renamePlant(Integer plantId, String commonName, String botanicalName) {
		Plant plant = plantRepository.findPlantByPlantId(plantId);
		plant.setCommonName(commonName);
		plant.setBotanicalName(botanicalName);
		plantRepository.save(plant);
		return plant;

	}

	/* HELPER METHODS */

	/*
	 * public Plant updatePlantToxicity(Integer plantId,List <Toxicity> toxicity ){
	 * Plant plant = plantRepository.findPlantByPlantId(plantId);
	 * plant.setToxicity(toxicity); plantRepository.save(plant);
	 * 
	 * return plant;
	 * 
	 * }
	 * 
	 * public Plant updatePlantSoil(Integer plantId,List <SoilType> soilType){ Plant
	 * plant = plantRepository.findPlantByPlantId(plantId);
	 * plant.setSoilType(soilType); plantRepository.save(plant);
	 * 
	 * return plant;
	 * 
	 * }
	 * 
	 * public Plant updatePlantSun(Integer plantId,List <SunExposure> sunExposure){
	 * Plant plant = plantRepository.findPlantByPlantId(plantId);
	 * plant.setSunExposure(sunExposure); plantRepository.save(plant);
	 * 
	 * return plant;
	 * 
	 * }
	 */

	public Plant updateBloom(Integer plantId, BloomTime bloomTime) {
		Plant plant = plantRepository.findPlantByPlantId(plantId);
		plant.setBloomtime(bloomTime);
		plantRepository.save(plant);

		return plant;

	}

	public Member getMemberbyPlantId(Integer plantId) {

		Plant plant = plantRepository.findPlantByPlantId(plantId);
		List<Member> members = toList(memberRepository.findAll());
		for (Member member : members) {
			if (member.getPlant() == plant) {
				return member;
			}
		}
		throw new IllegalArgumentException("Member not found.");
	}
	
	public Plant getPlantbyId(Integer plantId) {

		Plant plant = plantRepository.findPlantByPlantId(plantId);
		return plant;
	}

	@Transactional
	public List<Plant> getAllPlants() {
		return toList(plantRepository.findAll());
	}

	
	public boolean isIdAvailable(Integer plantId) {

		Plant plant = plantRepository.findPlantByPlantId(plantId);
		if (plant != null) {
			return false;
		} else
			return true;
	}

	/*public Plant deletePlantForMember(Integer plantId) {

		Plant plant = plantRepository.findPlantByPlantId(plantId);
		Member member = plant.getMember();
		
		for (int i=0; i< member.getPlant().size(); i++) {
		
			if (member.getPlant().get(i) == plant) {
				member.getPlant().get(i).setMember(null);;
			}
		}
		return plant;
	}*/

	/**
	 * Set the last watered date to the current date
	 * Update any reminders
	 */
	@Transactional
	public boolean waterPlant(Integer plantId){
		boolean isWatered = false;
		Plant plant = plantRepository.findPlantByPlantId(plantId);
		if (plant != null){
			WateringSchedule schedule = plant.getWateringRecommendation();
			int hoursBetweenWatering = schedule.getHoursBetweenWatering();

			Date lastDate = plant.getLastWateredDate();
			Time lastTime = plant.getLastWateredTime();
			if (lastDate == null){ 
				// no date or time exists, so set it to now
				java.util.Date date = new java.util.Date();
				java.sql.Date sqlDate = new Date(date.getTime());
				lastDate = sqlDate;; // should return current date
				lastTime = Time.valueOf("10:00:00"); // set a random morning time
			}
			// calculate next watering date and time
			long daysUntilNextWatering = hoursBetweenWatering / 24;

			LocalDateTime nextDateTime = lastDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			nextDateTime = nextDateTime.plusDays(daysUntilNextWatering);
			java.util.Date next = Date.from(nextDateTime.atZone(ZoneId.systemDefault()).toInstant());
			java.sql.Date nextDate = new Date(next.getTime());
			Time nextTime = lastTime;

			// update plant
			plant.setLastWateredTime(nextTime);
			plant.setLastWateredDate(nextDate);
			plantRepository.save(plant);

			// update reminders
			List<Reminder> reminders = schedule.getReminder();
			if (!reminders.isEmpty()){
				// reminders exist
				// should only be one, but just in case update all
				for(int i = 0; i < reminders.size(); i++){
					int id = reminders.get(i).getReminderId();
					// update reminder
					reminders.get(i).setDate(nextDate);
					reminders.get(i).setTime(nextTime);
					// save reminder
					reminderRepository.save(reminders.get(i));
				}
			}
			// all done without error
			isWatered = true;
		}
		return isWatered;
	}

	/**
	 * Helper method that converts iterable to list
	 * 
	 * @param <T>
	 * @param iterable
	 * @return
	 */
	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}


}
