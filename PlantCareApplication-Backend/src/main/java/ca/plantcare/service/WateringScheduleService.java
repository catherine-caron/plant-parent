package ca.plantcare.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.plantcare.dao.WateringScheduleRepository;
import ca.plantcare.models.WateringSchedule;
import ca.plantcare.dao.ReminderRepository;
import ca.plantcare.models.Reminder;
import ca.plantcare.dao.PlantRepository;
import ca.plantcare.models.Plant;

@Service
public class WateringScheduleService {

    @Autowired
	private WateringScheduleRepository wateringScheduleRepository;
	@Autowired
	private ReminderRepository reminderRepository;
    @Autowired
	private PlantRepository plantRepository;

    /**
     * Create a Watering Schedule with given parameters for a plant
     * @param plantId
	 * @param hoursBetweenWatering
	 * @return the schedule created
     */
	@Transactional
	public WateringSchedule createWateringSchedule(Integer plantId, Integer hoursBetweenWatering){
        Plant plant = plantRepository.findPlantByPlantId(plantId);

        if (plant == null){
            throw new IllegalArgumentException("Plant was not found.");
        }
        else if (hoursBetweenWatering == null){
            throw new IllegalArgumentException("Hours Between Watering cannot be empty.");
        }
		else {
            int leftLimit = 000001;
		    int rightLimit = 000100;
	        int scheduleId = leftLimit + (int) (Math.random() * (rightLimit - leftLimit));

            WateringSchedule wateringSchedule = new WateringSchedule();
            wateringSchedule.setScheduleId(scheduleId);
            wateringSchedule.setHoursBetweenWatering(hoursBetweenWatering);
            wateringScheduleRepository.save(wateringSchedule);
            
            plant.setWateringRecommendation(wateringSchedule);
            plantRepository.save(plant);

            return wateringSchedule;
		}
    }

    /**
	 * Get the Watering Schedule by its ID
	 * @param scheduleId
	 * @return the schedule
	 */
	@Transactional
	public WateringSchedule getWateringScheduleById(Integer scheduleId) {
		WateringSchedule wateringSchedule = wateringScheduleRepository.findWateringScheduleByScheduleId(scheduleId);
        if (wateringSchedule == null){
            throw new IllegalArgumentException("The schedule cannot be found.");
        }
        else {
            return wateringSchedule;
        }
    }

    /**
     * Update the hours between watering
     * @param scheduleId
     * @param newHoursBetweenWatering
     * @return the updated schedule
     */
    @Transactional
    public WateringSchedule updateWateringSchedule(Integer scheduleId, Integer newHoursBetweenWatering){
        WateringSchedule wateringSchedule = wateringScheduleRepository.findWateringScheduleByScheduleId(scheduleId);

        if (wateringSchedule == null){
            throw new IllegalArgumentException("The watering schedule cannot be found.");
        }
        else if (newHoursBetweenWatering == null){
            throw new IllegalArgumentException("Hours Between Watering cannot be empty.");
        }
        else {
            wateringSchedule.setHoursBetweenWatering(newHoursBetweenWatering);
            wateringScheduleRepository.save(wateringSchedule);
            return wateringSchedule;
        }
    }

    /**
     * Delete Watering Schedule
     * @param scheduleId
     * @return the deleted schedule
     */
    @Transactional
    public WateringSchedule deleteWateringSchedule(Integer scheduleId) {
        WateringSchedule wateringSchedule = wateringScheduleRepository.findWateringScheduleByScheduleId(scheduleId);

        if (wateringSchedule == null){
            throw new IllegalArgumentException("The watering schedule cannot be found.");
        }
        else {
            if (wateringSchedule.getReminder() == null) {
                for (Reminder reminder : wateringSchedule.getReminder()) {
                    reminderRepository.delete(reminder);
                }
            }
            wateringScheduleRepository.delete(wateringSchedule);
            return wateringSchedule;
        }
    }

    /**
     * Gets the watering schedule for a plant
     * @param plantId
     * @return schedule
     */
    @Transactional 
    public WateringSchedule getWateringScheduleByPlant(Integer plantId){
		Plant plant = plantRepository.findPlantByPlantId(plantId);
		if (plant == null){
			throw new IllegalArgumentException("Plant not found.");
		}
		else{
			return plant.getWateringRecommendation();
		}
	}


    // --------- Helper Methods ------------

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
