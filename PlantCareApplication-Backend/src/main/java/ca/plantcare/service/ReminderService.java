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

@Service
public class ReminderService {

    @Autowired
	private WateringScheduleRepository wateringScheduleRepository;
    @Autowired
	private PlantRepository plantRepository;
	@Autowired
	private ReminderRepository reminderRepository;

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
                    if (plant.getWateringRecommendation().getScheduleId() == scheduleId) {
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

     /**
	 * Get the Reminder by its ID
	 * @param reminderId
	 * @return the reminder
	 */
	@Transactional
	public Reminder getReminderById(Integer reminderId) {
        Reminder reminder = reminderRepository.findReminderByReminderId(reminderId);
        if (reminder == null){
            throw new IllegalArgumentException("The reminder cannot be found.");
        }
        else {
            return reminder;
        }
	}

    /**
     * Update Reminder
     * @param reminderId
     * @param newMessage
     * @param newTime
     * @param newDate
     * @return the updated reminder
     */
    @Transactional
    public Reminder updateReminder(Integer reminderId, String newMessage, Time newTime, Date newDate){
        Reminder reminder = reminderRepository.findReminderByReminderId(reminderId);

        if (reminder == null){
            throw new IllegalArgumentException("The reminder cannot be found.");
        }
        else if (newTime == null){
            throw new IllegalArgumentException("Time cannot be empty.");
        }
        else if (newDate == null){
            throw new IllegalArgumentException("Date cannot be empty.");
        }
        else {
            if (newMessage == null || newMessage.replaceAll("\\s+", "").length() == 0 || newMessage.equals("undefined")) {
                // default message
                newMessage = "Time to water your plant!";
            }
            else if ( newMessage.equals("Rajaa") || newMessage.equals("Catherine")){
                // easter egg
                newMessage = "Water your plant, idiot!";
            }
            reminder.setMessage(newMessage);
            reminder.setDate(newDate);
            reminder.setTime(newTime);
            reminderRepository.save(reminder);
            return reminder;
        }
    }

    /**
     * Delete Reminder 
     * @param reminderId
     * @return the deleted reminder
     */
    @Transactional
    public Reminder deleteReminder(Integer reminderId) {
        Reminder reminder = reminderRepository.findReminderByReminderId(reminderId);
        if (reminder == null){
            throw new IllegalArgumentException("The reminder cannot be found.");
        }
        else {
            reminderRepository.delete(reminder);
            return reminder;
        }
    }


    /**
     * Gets all reminders for a watering schedule
     * @param scheduleId
     * @return list of reminders
     */
    @Transactional 
    public List<Reminder> getReminderByWateringSchedule(Integer scheduleId){
		WateringSchedule wateringSchedule = wateringScheduleRepository.findWateringScheduleByScheduleId(scheduleId);
		if (wateringSchedule == null){
			throw new IllegalArgumentException("Schedule not found.");
		}
		else{
			return wateringSchedule.getReminder();
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
