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
    
	private static final String BASE_URL = "/reminder";
	/**
	 * Return the reminder with specified id
	 * 
	 * @param reminderId
	 * @return Member Dto
	 */
	@GetMapping(value = { BASE_URL+ "/getReminderById/{reminderId}", BASE_URL+ "/getReminderById/{reminderId}/" })
	public ReminderDto getReminderById(@PathVariable("reminderId") Integer reminderId) {
		Reminder reminder = reminderService.getReminderById(reminderId);
        return ReminderDto.convertToDto(reminder);
	}

    // createReminder(Integer scheduleId, String message, Time time, Date date)
    // getReminderById(Integer reminderId)
    // updateReminder(Integer reminderId, String newMessage, Time newTime, Date newDate)
    // deleteReminder(Integer reminderId)
    // getReminderByWateringSchedule(Integer scheduleId)



}
