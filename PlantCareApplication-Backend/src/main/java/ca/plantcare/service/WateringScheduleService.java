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

@Service
public class WateringScheduleService {

    @Autowired
	private WateringScheduleRepository wateringScheduleRepository;
	@Autowired
	private ReminderRepository reminderRepository;
    
    // create
    // get by schedule
    // update 
    // delete
    // find/get by plant

    /**
     * Create a Watering Schedule with given parameters
	 * @param scheduleId
	 * @param hoursBetweenWatering
	 * @return the schedule created
     */
	@Transactional
	public WateringSchedule createMeWateringSchedule(Integer scheduleId, Integer hoursBetweenWatering){

        if (scheduleId == null){
            throw new IllegalArgumentException("Schedule ID cannot be empty.");
        }
        else if (hoursBetweenWatering == null){
            throw new IllegalArgumentException("Hours Between Watering cannot be empty.");
        }
		else {
            WateringSchedule wateringSchedule = new WateringSchedule();
            wateringSchedule.setScheduleId(scheduleId);
            wateringSchedule.setHoursBetweenWatering(hoursBetweenWatering);
            wateringScheduleRepository.save(wateringSchedule);
            return wateringSchedule;
		}
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
