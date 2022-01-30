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
public class TestWateringScheduleService {

    private static final Integer SCHEDULEID1 = 1;
    private static final Integer HOURSBETWEENWATERING1 = 24;

    private static final Integer REMINDERID1 = 1;

	private static final Integer PLANTID1 = 123;


    @Mock
	private WateringScheduleRepository wateringScheduleRepository;
	@Mock
	private ReminderRepository reminderRepository;
	@Mock
	private PlantRepository plantRepository;

	@InjectMocks
	private WateringScheduleService wateringScheduleService;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setMockOutput() {
		MockitoAnnotations.initMocks(this);
		lenient().when(wateringScheduleRepository.findWateringScheduleByScheduleId(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(SCHEDULEID1)) {
                // Reminder
                Reminder reminder = new Reminder();
                reminder.setReminderId(REMINDERID1); 

                // Watering Schedule
                WateringSchedule wateringSchedule = new WateringSchedule();
                wateringSchedule.setScheduleId(SCHEDULEID1);
                wateringSchedule.setHoursBetweenWatering(HOURSBETWEENWATERING1);
                List<Reminder> reminders = new ArrayList<Reminder>();
                reminders.add(reminder);
                wateringSchedule.setReminder(reminders);

                // Plant
                Plant plant = new Plant();
                plant.setPlantId(PLANTID1);
                plant.setWateringRecommendation(wateringSchedule);

				return wateringSchedule;
			} else {
				return null;
			}
		});
        lenient().when(plantRepository.findPlantByPlantId(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(PLANTID1)) {
                // Reminder
                Reminder reminder = new Reminder();
                reminder.setReminderId(REMINDERID1); 

                // Watering Schedule
                WateringSchedule wateringSchedule = new WateringSchedule();
                wateringSchedule.setScheduleId(SCHEDULEID1);
                wateringSchedule.setHoursBetweenWatering(HOURSBETWEENWATERING1);
                List<Reminder> reminders = new ArrayList<Reminder>();
                reminders.add(reminder);
                wateringSchedule.setReminder(reminders);

                // Plant
                Plant plant = new Plant();
                plant.setPlantId(PLANTID1);
                plant.setWateringRecommendation(wateringSchedule);

				return plant;
			} 
			else {
				return null;
			}
		});
        // Whenever anything is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(wateringScheduleRepository.save(any(WateringSchedule.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(reminderRepository.save(any(Reminder.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(plantRepository.save(any(Plant.class))).thenAnswer(returnParameterAsAnswer);
    }
    
    /**
	 * Create watering schedule successfully
	 */
	@Test
	public void testCreateWateringScheduleSuccessfully() {
		WateringSchedule wateringSchedule = null;
		try {
			wateringSchedule = wateringScheduleService.createWateringSchedule(PLANTID1, HOURSBETWEENWATERING1);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(wateringSchedule);
		assertNotNull(wateringSchedule.getScheduleId());
		assertEquals(HOURSBETWEENWATERING1, wateringSchedule.getHoursBetweenWatering());
	}

    /**
	 * Create watering schedule with non-existing plant
	 */
	@Test
	public void testCreateWateringscheduleNullPlant(){
		Integer plantId = 5;
		WateringSchedule wateringSchedule = null;
		String error = null;
		try {
			wateringSchedule = wateringScheduleService.createWateringSchedule(plantId, HOURSBETWEENWATERING1);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(wateringSchedule);
		assertEquals("Plant was not found.", error);
	}
    
    /**
	 * Create watering schedule with empty hours 
	 */
	@Test
	public void testCreateWateringscheduleEmptyHours(){
        Integer hours = null;
		WateringSchedule wateringSchedule = null;
		String error = null;
		try {
			wateringSchedule = wateringScheduleService.createWateringSchedule(PLANTID1, hours);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(wateringSchedule);
		assertEquals("Hours Between Watering cannot be empty.", error);
	}
    
    /**
	 * Get watering schedule successfully
	 */
	@Test
	public void testGetWateringScheduleSuccessfully() {
		WateringSchedule wateringSchedule = null;
		try {
			wateringSchedule = wateringScheduleService.getWateringScheduleById(SCHEDULEID1);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(wateringSchedule);
		assertEquals(SCHEDULEID1, wateringSchedule.getScheduleId());
		assertEquals(HOURSBETWEENWATERING1, wateringSchedule.getHoursBetweenWatering());
	}

    /**
	 * Get watering schedule with schedule not found
	 */
	@Test
	public void testCreateWateringscheduleNotFound(){
        Integer scheduleId = 5;
		WateringSchedule wateringSchedule = null;
		String error = null;
		try {
			wateringSchedule = wateringScheduleService.getWateringScheduleById(scheduleId);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(wateringSchedule);
		assertEquals("The schedule cannot be found.", error);
	}

    /**
	 * Update watering schedule successfully
	 */
	@Test
	public void testUpdateWateringScheduleSuccessfully() {
        Integer newHours = 5;
		WateringSchedule wateringSchedule = null;
		try {
			wateringSchedule = wateringScheduleService.updateWateringSchedule(SCHEDULEID1, newHours);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(wateringSchedule);
		assertEquals(SCHEDULEID1, wateringSchedule.getScheduleId());
		assertEquals(newHours, wateringSchedule.getHoursBetweenWatering());
	}

    /**
	 * Update watering schedule with schedule not found
	 */
	@Test
	public void testUpdateWateringscheduleNotFound(){
        Integer scheduleId = 5;
		WateringSchedule wateringSchedule = null;
		String error = null;
		try {
			wateringSchedule = wateringScheduleService.updateWateringSchedule(scheduleId, HOURSBETWEENWATERING1);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(wateringSchedule);
		assertEquals("The watering schedule cannot be found.", error);
	}

    /**
	 * Update watering schedule with empty hours 
	 */
	@Test
	public void testUpdateWateringscheduleEmptyHours(){
        Integer newHours = null;
		WateringSchedule wateringSchedule = null;
		String error = null;
		try {
			wateringSchedule = wateringScheduleService.updateWateringSchedule(SCHEDULEID1, newHours);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(wateringSchedule);
		assertEquals("Hours Between Watering cannot be empty.", error);
	}

    /**
	 * Delete watering schedule successfully
	 */
	@Test
	public void testDeleteWateringScheduleSuccessfully() {
		WateringSchedule wateringSchedule = null;
		try {
			wateringSchedule = wateringScheduleService.deleteWateringSchedule(SCHEDULEID1);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(wateringSchedule);
		assertEquals(SCHEDULEID1, wateringSchedule.getScheduleId());
		assertEquals(HOURSBETWEENWATERING1, wateringSchedule.getHoursBetweenWatering());
	}

    /**
	 * Delete watering schedule with schedule not found
	 */
	@Test
	public void testDeleteWateringscheduleNotFound(){
        Integer scheduleId = 5;
		WateringSchedule wateringSchedule = null;
		String error = null;
		try {
			wateringSchedule = wateringScheduleService.deleteWateringSchedule(scheduleId);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(wateringSchedule);
		assertEquals("The watering schedule cannot be found.", error);
	}

    /**
	 * Get watering schedule by plant id successfully
	 */
	@Test
	public void testGetWateringScheduleByPlantSuccessfully() {
		WateringSchedule wateringSchedule = null;
		try {
			wateringSchedule = wateringScheduleService.getWateringScheduleByPlant(PLANTID1);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(wateringSchedule);
		assertEquals(SCHEDULEID1, wateringSchedule.getScheduleId());
		assertEquals(HOURSBETWEENWATERING1, wateringSchedule.getHoursBetweenWatering());
	}
    
    /**
	 * Get watering schedule by plant with plant not found
	 */
	@Test
	public void testGetWateringscheduleByPlantNotFound(){
        Integer plantId = 5;
		WateringSchedule wateringSchedule = null;
		String error = null;
		try {
			wateringSchedule = wateringScheduleService.getWateringScheduleByPlant(plantId);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(wateringSchedule);
		assertEquals("Plant not found.", error);
	}


}
