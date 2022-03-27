package ca.plantcare.controller;

import static ca.plantcare.extra.HttpUtil.httpFailure;
import static ca.plantcare.extra.HttpUtil.httpSuccess;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.plantcare.dto.PlantDto;
import ca.plantcare.dto.WateringScheduleDto;
import ca.plantcare.models.Plant;
import ca.plantcare.models.WateringSchedule;
import ca.plantcare.models.Plant.BloomTime;
import ca.plantcare.models.Plant.SoilType;
import ca.plantcare.models.Plant.SunExposure;
import ca.plantcare.models.Plant.Toxicity;
import ca.plantcare.service.PlantService;
import ca.plantcare.service.WateringScheduleService;

@CrossOrigin(origins = "*")
@RestController
public class WateringScheduleController {

	private static final String BASE_URL = "/schedule";
	
	@Autowired
	private PlantService plantService;
	@Autowired
	private WateringScheduleService wateringScheduleService;
	
	/**
	 * Create watering schedule for a plant
	 * @param plantId
	 * @param hoursBetweenWatering
	 * @return
	 */
	@PostMapping(value = { BASE_URL + "/createWateringSchedule", BASE_URL + "/createWateringSchedule/" })
	public ResponseEntity<?> createWateringSchedule(@RequestParam("plantId") Integer plantId, 
			@RequestParam("hoursBetweenWatering") Integer hoursBetweenWatering)  {
		try {
			WateringSchedule wateringSchedule = wateringScheduleService.createWateringSchedule(plantId, hoursBetweenWatering);
					return httpSuccess(WateringScheduleDto.converToDto(wateringSchedule));

		}
		catch(Exception e){
			return httpFailure("Error: " + e.getMessage());
		}
	}

	/**
	 * Return the schedule with specified id
	 * 
	 * @param scheduleId
	 * @return Reminder Dto
	 */
	@GetMapping(value = { BASE_URL+ "/getWateringScheduleById/{scheduleId}", BASE_URL+ "/getWateringScheduleById/{scheduleId}/" })
	public WateringScheduleDto getWateringScheduleById(@PathVariable("scheduleId") Integer scheduleId) {
		WateringSchedule schedule = wateringScheduleService.getWateringScheduleById(scheduleId);
        return WateringScheduleDto.converToDto(schedule);
	}

	/**
	 * Update hours between watering
	 * @param scheduleId
	 * @param newHoursBetweenWatering
	 * @return
	 */
	@PutMapping(value = { BASE_URL + "/updateWateringSchedule", BASE_URL + "/updateWateringSchedule/" })
	public ResponseEntity<?> updateWateringSchedule(@RequestParam("scheduleId") Integer scheduleId, 
			@RequestParam("newHoursBetweenWatering") Integer newHoursBetweenWatering)  {
		try {
			WateringSchedule wateringSchedule = wateringScheduleService.updateWateringSchedule(scheduleId, newHoursBetweenWatering);
					return httpSuccess(WateringScheduleDto.converToDto(wateringSchedule));
		}
		catch(Exception e){
			return httpFailure("Error: " + e.getMessage());
		}
	}

	/**
	 * Delete schedule with Id
	 * @param scheduleId
	 * @return
	 */
	@PutMapping(value = { BASE_URL + "/deleteWateringSchedule", BASE_URL + "/deleteWateringSchedule/" })
	public ResponseEntity<?> deleteWateringSchedule(@RequestParam("scheduleId") Integer scheduleId)  {
		try {
			WateringSchedule wateringSchedule = wateringScheduleService.deleteWateringSchedule(scheduleId);
					return httpSuccess(WateringScheduleDto.converToDto(wateringSchedule));
		}
		catch(Exception e){
			return httpFailure("Error: " + e.getMessage());
		}
	}

	/**
	 * Find the watering schedule for a plant with Id
	 * @param plantId
	 * @return
	 */
	@GetMapping(value = { BASE_URL+ "/getWateringScheduleByPlant/{plantId}", BASE_URL+ "/getWateringScheduleByPlant/{plantId}/" })
	public WateringScheduleDto getWateringScheduleByPlant(@PathVariable("plantId") Integer plantId) {
		WateringSchedule schedule = wateringScheduleService.getWateringScheduleByPlant(plantId);
        return WateringScheduleDto.converToDto(schedule);
	}


}
