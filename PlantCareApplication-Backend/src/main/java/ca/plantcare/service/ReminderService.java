package ca.plantcare.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.plantcare.dao.WateringScheduleRepository;
import ca.plantcare.models.WateringSchedule;
import ca.plantcare.dao.PlantRepository;
import ca.plantcare.dao.ReminderRepository;
import ca.plantcare.models.Plant;
import ca.plantcare.models.Reminder;

public class ReminderService {

    @Autowired
	private WateringScheduleRepository wateringScheduleRepository;
    @Autowired
	private PlantRepository plantRepository;
	@Autowired
	private ReminderRepository reminderRepository;

    // get
    // update
    // delete
    // get by watering

    /**
     * Create a Reminder with given parameters
     * @param scheduleId
	 * @param message
     * @param time
     * @param date
	 * @return the reminder created
     */
	@Transactional
	public Reminder createReminder(Integer scheduleId, String message, Time time, Date date){
        WateringSchedule wateringSchedule = wateringScheduleRepository.findWateringScheduleByScheduleId(scheduleId);

        if (wateringSchedule == null){
            throw new IllegalArgumentException("Watering Schedule not found.");
        }
        else if (time == null){
            throw new IllegalArgumentException("Time cannot be empty.");
        }
        else if (date == null){
            throw new IllegalArgumentException("Date cannot be empty.");
        }
		else {

            int leftLimit = 000001;
		    int rightLimit = 000100;
	        int reminderId = leftLimit + (int) (Math.random() * (rightLimit - leftLimit));

            
            if (message == null || message.replaceAll("\\s+", "").length() == 0 || message.equals("undefined")) {
                // get plant for default message if message is empty
                Plant foundPlant = null; 
                List<Plant> plants = toList(plantRepository.findAll());
                for (Plant plant:plants) {
                    if (plant.getWateringRecommendation() == wateringSchedule) {
                        foundPlant = plant;
                        break;
                    }
                } 
                message = "Time to water " + foundPlant.getGivenName() + "!";
            }
            else if ( message.equals("Rajaa") || message.equals("Catherine")){
                // easter egg
                message = "Water your plant, idiot!";
            }

            Reminder reminder = new Reminder();
            reminder.setReminderId(reminderId);
            reminder.setMessage(message);
            reminder.setTime(time);
            reminder.setDate(date);
            reminderRepository.save(reminder);

            List <Reminder> reminders = wateringSchedule.getReminder();
            reminders.add(reminder);
            wateringSchedule.setReminder(reminders);
            wateringScheduleRepository.save(wateringSchedule);

            return reminder;
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
