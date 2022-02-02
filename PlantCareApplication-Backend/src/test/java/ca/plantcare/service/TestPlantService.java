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
import java.util.EnumSet;
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
import ca.plantcare.models.Plant.BloomTime;
import ca.plantcare.models.Plant.SoilType;
import ca.plantcare.models.Plant.SunExposure;
import ca.plantcare.models.Plant.Toxicity;

@ExtendWith(MockitoExtension.class)
public class TestPlantService {
	
	//Plant myPlant = new Plant();
	//Member myMember = new Member();
	Integer PLANTID = 1111;
	String BOTANICALNAME = "Botanical";
	String GIVENNAME = "MyPlant";
	String COMMONNAME = "Common";
	//List<BloomTime> Bloomtime =  EnumSet.of( Bloomtime.);
	BloomTime BLOOMTIME= BloomTime.Fall;
    List <BloomTime> bloomiTime = new ArrayList<>();
	SoilType SOILTYPE = SoilType.Chalky;
	SunExposure SUNEXPOSURE = SunExposure.FullSun;
	Toxicity TOXICITY = Toxicity.MajorToxicity;
	WateringSchedule wateringSchedule;
	
	
	@Mock
	private MemberRepository memberRepository;
	@Mock
	private PlantRepository plantRepository;
	@Mock
	private WateringScheduleRepository wateringSchedulRepository;

	@InjectMocks
	private PlantService plantService;
	
	@BeforeEach
	public void setMockOutput() { 
		
		 lenient().when(plantRepository.findPlantByPlantId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
	        	if (invocation.getArgument(0).equals(PLANTID)) {
		        	Plant plant = new Plant();
		        	Member member = new Member();
		        bloomiTime.add(BLOOMTIME);
		           plant.setBloomTime(bloomiTime);
		           plant.setBotanicalName(BOTANICALNAME);
		           plant.setCommonName(COMMONNAME);
		           plant.setIcon(PLANTID);
		           plant.setSoilType(SOILTYPE);
		           plant.setSunExposure(SUNEXPOSURE);
		           plant.setToxicity(TOXICITY);
		           plant.setWateringRecommendation(wateringSchedule);
		            return plant;
	        	}
	        	else {return null;
	        	}
	        	});
		 Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
				return invocation.getArgument(0);
			};
			lenient().when(memberRepository.save(any(Member.class))).thenAnswer(returnParameterAsAnswer);
			lenient().when(plantRepository.save(any(Plant.class))).thenAnswer(returnParameterAsAnswer);
			}
	
	@Test
	public void testCreatePlantPass() {
		Plant plant = null;
		try {
			Integer myPlantId = 1111;
			String myBotanicalName = "Botanical";
			String myGivenName = "MyPlant";
			String myCommonName = "Common";
			//List<BloomTime> Bloomtime =  EnumSet.of( Bloomtime.);
			BloomTime myBloomTime= BloomTime.Fall;
		    List <BloomTime> myBloomiTime = new ArrayList<>();
		    myBloomiTime.add(myBloomTime);
			SoilType mySoilType = SoilType.Chalky;
			SunExposure mySun = SunExposure.FullSun;
			Toxicity myToxic = Toxicity.MajorToxicity;
			WateringSchedule myWateringSchedule = new WateringSchedule();
			plant = plantService.createPlant(myPlantId, myGivenName, myBotanicalName, myCommonName, mySun, mySoilType, myToxic, myBloomiTime, myWateringSchedule);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(plant);
	}
	@Test
	public void testCreatePlantNoWateringSchedule() {
		Plant plant = null;
		String error = "";
		try {
			Integer myPlantId = 1111;
			String myBotanicalName = "Botanical";
			String myGivenName = "MyPlant";
			String myCommonName = "Common";
			//List<BloomTime> Bloomtime =  EnumSet.of( Bloomtime.);
			BloomTime myBloomTime= BloomTime.Fall;
		    List <BloomTime> myBloomiTime = new ArrayList<>();
		    myBloomiTime.add(myBloomTime);
			SoilType mySoilType = SoilType.Chalky;
			SunExposure mySun = SunExposure.FullSun;
			Toxicity myToxic = Toxicity.MajorToxicity;
			WateringSchedule myWateringSchedule = null;
			plant = plantService.createPlant(myPlantId, myGivenName, myBotanicalName, myCommonName, mySun, mySoilType, myToxic, myBloomiTime, myWateringSchedule);
		} catch (Exception e) {
			// Check that no error occurred
			error=e.getMessage();
		}
		assertNull(plant);
		assertNotNull(error);

	}
	
	@Test
	public void testCreatePlantNoBloomTime() {
		Plant plant = null;
		String error = "";
		try {
			Integer myPlantId = 1111;
			String myBotanicalName = "Botanical";
			String myGivenName = "MyPlant";
			String myCommonName = "Common";
			//List<BloomTime> Bloomtime =  EnumSet.of( Bloomtime.);
			BloomTime myBloomTime= BloomTime.Fall;
		    List <BloomTime> myBloomiTime = new ArrayList<>();
		    //myBloomiTime.add(myBloomTime);
			SoilType mySoilType = SoilType.Chalky;
			SunExposure mySun = SunExposure.FullSun;
			Toxicity myToxic = Toxicity.MajorToxicity;
			WateringSchedule myWateringSchedule = null;
			plant = plantService.createPlant(myPlantId, myGivenName, myBotanicalName, myCommonName, mySun, mySoilType, myToxic, myBloomiTime, myWateringSchedule);
		} catch (Exception e) {
			// Check that no error occurred
			error=e.getMessage();
		}
		assertNull(plant);
		assertNotNull(error);

	}
	

}
