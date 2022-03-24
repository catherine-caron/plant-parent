package ca.plantcare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static ca.plantcare.extra.HttpUtil.httpFailureMessage;
import static ca.plantcare.extra.HttpUtil.httpSuccess;
import static ca.plantcare.extra.HttpUtil.httpFailure;


import ca.plantcare.models.WateringSchedule;
import ca.plantcare.dto.PlantDto;
import ca.plantcare.models.Plant;
import ca.plantcare.models.Plant.BloomTime;
import ca.plantcare.models.Plant.SoilType;
import ca.plantcare.models.Plant.SunExposure;
import ca.plantcare.models.Plant.Toxicity;
import ca.plantcare.service.*;

@CrossOrigin(origins = "*")
@RestController
public class PlantController {

	private static final String BASE_URL = "/plant";
	
	@Autowired
	private PlantService plantService;
	
	@PostMapping(value = { BASE_URL + "/create", BASE_URL + "/create/" })
	public ResponseEntity<?> createPlant(@RequestParam("icon") Integer icon, 
			@RequestParam("givenName") String givenName, 
			@RequestParam("botanicalName") String botanicalName, 
			@RequestParam("commonName") String commonName, 
			@RequestParam("sunExposure") SunExposure sunExposure,
			@RequestParam("soilType") SoilType soilType, 
			@RequestParam("toxicity") Toxicity toxicity,
			@RequestParam("bloomTime") BloomTime bloomTime,
			@RequestParam("wateringRecommendation") Integer wateringRecommendation)  {
		try {
			Plant plant = plantService.createPlant(icon, givenName, botanicalName, commonName, sunExposure, soilType, toxicity, bloomTime, wateringRecommendation);
					return httpSuccess(PlantDto.convertToDto(plant));

		}
		catch(Exception e){
			return httpFailure("Error: " + e.getMessage());
		}
	}
}
