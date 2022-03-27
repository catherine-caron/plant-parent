package ca.plantcare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

import static ca.plantcare.extra.HttpUtil.httpFailureMessage;
import static ca.plantcare.extra.HttpUtil.httpSuccess;


import static ca.plantcare.extra.HttpUtil.httpFailure;

import ca.plantcare.dto.*;
import ca.plantcare.models.*;
import ca.plantcare.service.*;

@CrossOrigin(origins = "*")
@RestController
public class ReminderController {

    @Autowired
	private ReminderService reminderService;

    @Autowired
	private WateringScheduleService wateringScheduleService;
    
	private static final String BASE_URL = "/reminder";


	/**
	 * Return the reminder with specified id
	 * 
	 * @param reminderId
	 * @return Reminder Dto
	 */
	@GetMapping(value = { BASE_URL+ "/getReminderById/{reminderId}", BASE_URL+ "/getReminderById/{reminderId}/" })
	public ReminderDto getReminderById(@PathVariable("reminderId") Integer reminderId) {
		Reminder reminder = reminderService.getReminderById(reminderId);
        return ReminderDto.convertToDto(reminder);
	}


    /**
	 * Create a Reminder Dto with provided parameters
	 * 
	 * @return Reminder  Dto
	 */
	@PostMapping(value = { BASE_URL+"/createReminder", BASE_URL+"/createReminder/" })
	public ResponseEntity<?>createReminder(
			@RequestParam("scheduleId") Integer scheduleId,
			@RequestParam("message") String message,
			@RequestParam("time") Time time,
			@RequestParam("date") Date date)
			{
		
		try {
			Reminder reminder = reminderService.createReminder(scheduleId, message, time, date);
			return httpSuccess(ReminderDto.convertToDto(reminder));

		}
		catch(Exception e){
			return httpFailure("Error: " + e.getMessage());
		}
	}
	
    /**
	 * Update a Reminder 
     * Pass old values if not changing
	 * 
	 * @return Reminder Dto updated
	 */
	@PutMapping(value = { BASE_URL+"/updateReminder", BASE_URL+"/updateReminder/" })
	public ResponseEntity<?>updateReminder(
			@RequestParam("reminderId") Integer reminderId,
			@RequestParam("newMessage") String newMessage,
			@RequestParam("newTime") Time newTime,
			@RequestParam("newDate") Date newDate)
			{
		
		try {
			Reminder reminder = reminderService.updateReminder(reminderId, newMessage, newTime, newDate);
			return httpSuccess(ReminderDto.convertToDto(reminder));

		}
		catch(Exception e){
			return httpFailure("Error: " + e.getMessage());
		}
	}

   
    /**
	 * Delete a Reminder 
	 * 
	 * @return Reminder Dto deleted
	 */
	@PutMapping(value = { BASE_URL+"/deleteReminder", BASE_URL+"/deleteReminder/" })
	public ResponseEntity<?>deleteReminder(
			@RequestParam("reminderId") Integer reminderId)
			{
		
		try {
			Reminder reminder = reminderService.deleteReminder(reminderId);
			return httpSuccess(ReminderDto.convertToDto(reminder));

		}
		catch(Exception e){
			return httpFailure("Error: " + e.getMessage());
		}
	}


    /**
	 * Return the reminder associated to the watering schedule
	 * 
	 * @param reminderId
	 * @return Reminder Dto
	 */
	@GetMapping(value = { BASE_URL+ "/getReminderByWateringSchedule/{scheduleId}", BASE_URL+ "/getReminderByWateringSchedule/{scheduleId}/" })
	public  ResponseEntity<?> getReminderByWateringSchedule(@PathVariable("scheduleId") Integer scheduleId) {
        List<ReminderDto> reminders = null;
		try {
		    reminders = reminderService.getReminderByWateringSchedule(scheduleId).stream().map(reminder -> ReminderDto.convertToDto(reminder)).collect(Collectors.toList());
        } catch (Exception e) {
			return httpFailureMessage(e.getMessage());
		}
		return httpSuccess(reminders);
	}



}
