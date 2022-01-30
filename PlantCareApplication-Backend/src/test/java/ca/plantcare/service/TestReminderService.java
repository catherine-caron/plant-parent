package ca.plantcare.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.plantcare.dao.*;
import ca.plantcare.models.*;

@ExtendWith(MockitoExtension.class)
public class TestReminderService {

	private static final String MESSAGE1 = "This is your reminder!";
    private static final Integer REMINDERID1 = 1;
    private static final Time TIME1 = Time.valueOf("10:00:00");
    private static final Date DATE1 = Date.valueOf("2022-03-01");

    private static final Integer SCHEDULEID1 = 1;
    private static final Integer HOURSBETWEENWATERING1 = 24;

	private static final String GIVENNAME1 = "Elia";
	private static final Integer PLANTID1 = 123;

	@Mock
	private ReminderRepository reminderRepository;
    @Mock
	private WateringScheduleRepository wateringScheduleRepository;
	@Mock
	private PlantRepository plantRepository;

	@InjectMocks
	private ReminderService reminderService;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setMockOutput() {
		MockitoAnnotations.initMocks(this);
		lenient().when(reminderRepository.findReminderByReminderId(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(REMINDERID1)) {
                // Reminder
                Reminder reminder = new Reminder();
                reminder.setReminderId(REMINDERID1); 
                reminder.setDate(DATE1);
                reminder.setTime(TIME1);
                reminder.setMessage(MESSAGE1);

                // Watering Schedule
                WateringSchedule wateringSchedule = new WateringSchedule();
                wateringSchedule.setScheduleId(SCHEDULEID1);
                wateringSchedule.setHoursBetweenWatering(HOURSBETWEENWATERING1);
                List<Reminder> reminders = new ArrayList<Reminder>();
                reminders.add(reminder);
                wateringSchedule.setReminder(reminders);

				return reminder;
			} else {
				return null;
			}
		});
        lenient().when(wateringScheduleRepository.findWateringScheduleByScheduleId(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(SCHEDULEID1)) {
                // Reminder
                Reminder reminder = new Reminder();
                reminder.setReminderId(REMINDERID1); 
                reminder.setDate(DATE1);
                reminder.setTime(TIME1);
                reminder.setMessage(MESSAGE1);

                // Watering Schedule
                WateringSchedule wateringSchedule = new WateringSchedule();
                wateringSchedule.setScheduleId(SCHEDULEID1);
                wateringSchedule.setHoursBetweenWatering(HOURSBETWEENWATERING1);
                List<Reminder> reminders = new ArrayList<Reminder>();
                reminders.add(reminder);
                wateringSchedule.setReminder(reminders);

				return wateringSchedule;
			} 
			else {
				return null;
			}
		});
		lenient().when(plantRepository.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
			// Reminder
			Reminder reminder = new Reminder();
			reminder.setReminderId(REMINDERID1); 
			reminder.setDate(DATE1);
			reminder.setTime(TIME1);
			reminder.setMessage(MESSAGE1);

			// Watering Schedule
			WateringSchedule wateringSchedule = new WateringSchedule();
			wateringSchedule.setScheduleId(SCHEDULEID1);
			wateringSchedule.setHoursBetweenWatering(HOURSBETWEENWATERING1);
			List<Reminder> reminders = new ArrayList<Reminder>();
			reminders.add(reminder);
			wateringSchedule.setReminder(reminders);

			// Plant
			Plant plant = new Plant();
			plant.setGivenName(GIVENNAME1);
			plant.setPlantId(PLANTID1);
			plant.setWateringRecommendation(wateringSchedule);

			List<Plant> plants = new ArrayList<Plant>();
			plants.add(plant);
		
			return plants;
		});
        // Whenever anything is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(reminderRepository.save(any(Reminder.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(wateringScheduleRepository.save(any(WateringSchedule.class))).thenAnswer(returnParameterAsAnswer);
    }

	/**
	 * Create reminder successfully
	 */
	@Test
	public void testCreateReminderSuccessfully() {
		Reminder reminder = null;
		try {
			reminder = reminderService.createReminder(SCHEDULEID1, MESSAGE1, TIME1, DATE1);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(reminder);
		assertNotNull(reminder.getReminderId());
		assertEquals(MESSAGE1, reminder.getMessage());
		assertEquals(TIME1, reminder.getTime());
		assertEquals(DATE1, reminder.getDate());
		// assertTrue(wateringScheduleRepository.findWateringScheduleByScheduleId(SCHEDULEID1).getReminder().contains(reminder));
	}

	/**
	 * Create member with non-existing watering schedule
	 */
	@Test
	public void testCreateReminderNullSchedule(){
		Integer scheduleId = 5;
		Reminder reminder = null;
		String error = null;
		try {
			reminder = reminderService.createReminder(scheduleId, MESSAGE1, TIME1, DATE1);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(reminder);
		assertEquals("Watering Schedule not found.", error);
	}

	/**
	 * Create member with null date
	 */
	@Test
	public void testCreateReminderNullDate(){
		Reminder reminder = null;
		String error = null;
		try {
			reminder = reminderService.createReminder(SCHEDULEID1, MESSAGE1, TIME1, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(reminder);
		assertEquals("Date cannot be empty.", error);
	}
    
	/**
	 * Create member with null time
	 */
	@Test
	public void testCreateReminderNullTime(){
		Reminder reminder = null;
		String error = null;
		try {
			reminder = reminderService.createReminder(SCHEDULEID1, MESSAGE1, null, DATE1);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(reminder);
		assertEquals("Time cannot be empty.", error);
	}

	/**
	 * Create reminder with empty message
	 */
	@Test
	public void testCreateReminderEmptyMessage() {
		String message = " ";
		Reminder reminder = null;
		try {
			reminder = reminderService.createReminder(SCHEDULEID1, message, TIME1, DATE1);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(reminder);
		assertNotNull(reminder.getReminderId());
		assertEquals("Time to water " + GIVENNAME1 + "!", reminder.getMessage());
		assertEquals(TIME1, reminder.getTime());
		assertEquals(DATE1, reminder.getDate());
	}

	/**
	 * Create reminder with secret easter egg message
	 */
	@Test
	public void testCreateReminderSecretMessage() {
		String message = "Catherine";
		Reminder reminder = null;
		try {
			reminder = reminderService.createReminder(SCHEDULEID1, message, TIME1, DATE1);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(reminder);
		assertNotNull(reminder.getReminderId());
		assertEquals("Water your plant, idiot!", reminder.getMessage());
		assertEquals(TIME1, reminder.getTime());
		assertEquals(DATE1, reminder.getDate());
	}

	/**
	 * Get reminder successfully
	 */
	@Test
	public void testGetReminderSuccessfully() {
		Reminder reminder = null;
		try {
			reminder = reminderService.getReminderById(REMINDERID1);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(reminder);
		assertEquals(REMINDERID1, reminder.getReminderId());
		assertEquals(MESSAGE1, reminder.getMessage());
		assertEquals(TIME1, reminder.getTime());
		assertEquals(DATE1, reminder.getDate());
	}

	/**
	 * Get reminder with reminder not found
	 */
	@Test
	public void testGetReminderNotFound(){
		Integer reminderId = 3;
		Reminder reminder = null;
		String error = null;
		try {
			reminder = reminderService.getReminderById(reminderId);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(reminder);
		assertEquals("The reminder cannot be found.", error);
	}

	/**
	 * Update reminder successfully
	 */
	@Test
	public void testUpdateReminderSuccessfully() {
		String newMessage = "This is the new message";
		Time newTime = Time.valueOf("11:00:00");
		Date newDate = Date.valueOf("2022-03-02");
		Reminder reminder = null;
		try {
			reminder = reminderService.updateReminder(REMINDERID1, newMessage, newTime, newDate);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(reminder);
		assertEquals(REMINDERID1, reminder.getReminderId());
		assertEquals(newMessage, reminder.getMessage());
		assertEquals(newTime, reminder.getTime());
		assertEquals(newDate, reminder.getDate());
	}

	/**
	 * Update reminder with reminder not found
	 */
	@Test
	public void testUpdateReminderNotFound(){
		Integer reminderId = 3;
		Reminder reminder = null;
		String error = null;
		try {
			reminder = reminderService.updateReminder(reminderId, MESSAGE1, TIME1, DATE1);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(reminder);
		assertEquals("The reminder cannot be found.", error);
	}

	/**
	 * Update reminder with new time null
	 */
	@Test
	public void testUpdateReminderNewTimeNull(){
		Time newTime = null;
		Reminder reminder = null;
		String error = null;
		try {
			reminder = reminderService.updateReminder(REMINDERID1, MESSAGE1, newTime, DATE1);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(reminder);
		assertEquals("Time cannot be empty.", error);
	}

	/**
	 * Update reminder with new date null
	 */
	@Test
	public void testUpdateReminderNewDateNull(){
		Date newDate = null;
		Reminder reminder = null;
		String error = null;
		try {
			reminder = reminderService.updateReminder(REMINDERID1, MESSAGE1, TIME1, newDate);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(reminder);
		assertEquals("Date cannot be empty.", error);
	}
	
	/**
	 * Update reminder with empty message
	 */
	@Test
	public void testUpdateReminderEmptyMessage() {
		String message = " ";
		Reminder reminder = null;
		try {
			reminder = reminderService.updateReminder(REMINDERID1, message, TIME1, DATE1);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(reminder);
		assertEquals(REMINDERID1, reminder.getReminderId());
		assertEquals("Time to water your plant!", reminder.getMessage());
		assertEquals(TIME1, reminder.getTime());
		assertEquals(DATE1, reminder.getDate());
	}

	/**
	 * Update reminder with secret easter egg message
	 */
	@Test
	public void testUpdateReminderSecretMessage() {
		String message = "Rajaa";
		Reminder reminder = null;
		try {
			reminder = reminderService.updateReminder(REMINDERID1, message, TIME1, DATE1);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(reminder);
		assertEquals(REMINDERID1, reminder.getReminderId());
		assertEquals("Water your plant, idiot!", reminder.getMessage());
		assertEquals(TIME1, reminder.getTime());
		assertEquals(DATE1, reminder.getDate());
	}

	/**
	 * Delete reminder successfully
	 */
	@Test
	public void testDeleteReminderSuccessfully() {
		Reminder reminder = null;
		try {
			reminder = reminderService.deleteReminder(REMINDERID1);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(reminder);
		assertEquals(REMINDERID1, reminder.getReminderId());
		assertEquals(MESSAGE1, reminder.getMessage());
		assertEquals(TIME1, reminder.getTime());
		assertEquals(DATE1, reminder.getDate());
	}
	
	/**
	 * Delete reminder with reminder not found
	 */
	@Test
	public void testDeleteeReminderNotFound(){
		Integer reminderId = 3;
		Reminder reminder = null;
		String error = null;
		try {
			reminder = reminderService.deleteReminder(reminderId);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(reminder);
		assertEquals("The reminder cannot be found.", error);
	}
	
	/**
	 * Get reminder by watering schedule successfully
	 */
	@Test
	public void testGetReminderByWateringScheduleSuccessfully() {
		List<Reminder> reminders = null;
		try {
			reminders = reminderService.getReminderByWateringSchedule(SCHEDULEID1);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(reminders);
		assertEquals(REMINDERID1, reminders.get(0).getReminderId());
		assertEquals(MESSAGE1, reminders.get(0).getMessage());
		assertEquals(TIME1, reminders.get(0).getTime());
		assertEquals(DATE1, reminders.get(0).getDate());
	}

	/**
	 * Get reminder by watering schedule with schedule not found
	 */
	@Test
	public void testGetReminderByWateringScheduleNotFound(){
		Integer scheduleId = 3;
		String error = null;
		List<Reminder> reminders = null;
		try {
			reminders = reminderService.getReminderByWateringSchedule(scheduleId);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(reminders);
		assertEquals("Schedule not found.", error);
	}


}	
