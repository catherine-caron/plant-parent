package ca.plantcare.controller;

import static ca.plantcare.extra.HttpUtil.httpFailure;
import static ca.plantcare.extra.HttpUtil.httpSuccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@PostMapping(value = { BASE_URL + "/create", BASE_URL + "/create/" })
	public ResponseEntity<?> createWateringSchedule(@RequestParam("plantId") Integer plantId, 
			@RequestParam("hoursBetweenWatering") Integer hoursBetweenWatering)  {
		try {
			WateringSchedule wateringSchedule = wateringScheduleService.createWateringSchedule(plantId, hoursBetweenWatering);
					return httpSuccess(WateringScheduleDto.converToDto(wateringSchedule));

		}
		catch(Exception e){
			return httpFailure("Error: " + e.getMessage());
		}
	
	
}}
