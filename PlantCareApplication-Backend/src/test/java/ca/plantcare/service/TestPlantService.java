/*package ca.plantcare.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
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

	// Plant myPlant = new Plant();
	// Member myMember = new Member();
	Integer PLANTID = 7;
	Integer SCHEDULEID = 8;
	String BOTANICALNAME = "Botanical";
	String GIVENNAME = "MyPlant";
	String COMMONNAME = "Common";
	// List<BloomTime> Bloomtime = EnumSet.of( Bloomtime.);
	BloomTime BLOOMTIME = BloomTime.Fall;
	SoilType SOILTYPE = SoilType.Chalky;
	SunExposure SUNEXPOSURE = SunExposure.FullSun;
	Toxicity TOXICITY = Toxicity.MajorToxicity;
	WateringSchedule wateringSchedule;

	private static final String USERNAME1 = "Username1";
	private static final String NAME1 = "Rajaa Bk";

	private static final String GIVENNAME1 = "Elia";
	private static final String BOTANICALNAME1 = "Sunflower";
	private static final String COMMONNAME1 = "Sunflower";
	private static final Integer PLANTID1 = 123;

	private static final String USERNAME2 = "Username2";
	private static final String NAME2 = "Catherine Carbon";
	Member MEMBER;

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
			
				plant.setBloomtime(BLOOMTIME);
				plant.setBotanicalName(BOTANICALNAME);
				plant.setCommonName(COMMONNAME);
				plant.setIcon(PLANTID);
				plant.setSoilType(SOILTYPE);
				plant.setSunExposure(SUNEXPOSURE);
				plant.setToxicity(TOXICITY);
				plant.setWateringRecommendation(wateringSchedule);
				return plant;
			} else {
				return null;
			}
		});
		lenient().when(memberRepository.findMemberByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(USERNAME1)) {
				Member user = new Member();
				user.setUsername(USERNAME1);
				user.setName(NAME1);
				List<Plant> plants = new ArrayList<Plant>();
				Plant plant1 = new Plant();
				plant1.setGivenName(GIVENNAME1);
				plant1.setCommonName(COMMONNAME1);
				plant1.setBotanicalName(BOTANICALNAME1);
				plant1.setPlantId(PLANTID1);
				plants.add(plant1);
				user.setPlant(plants);
				user.setNumberOfPlants(plants.size());
				return user;
			} else if (invocation.getArgument(0).equals(USERNAME2)) {
				Member user = new Member();
				user.setUsername(USERNAME2);
				user.setName(NAME2);
				return user;
			} else {
				return null;
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
			Integer myPlantId = 5;
			Integer id =5;
			String myBotanicalName = "Botanical";
			String myGivenName = "MyPlant";
			String myCommonName = "Common";
			// List<BloomTime> Bloomtime = EnumSet.of( Bloomtime.);
			//BloomTime myBloomTime = BloomTime.Fall;
			//List<BloomTime> myBloomiTime = new ArrayList<>();
			//myBloomiTime.add(myBloomTime);
			BloomTime myBloomTime = BloomTime.Winter;
			SoilType mySoilType = SoilType.Chalky;
			SunExposure mySun = SunExposure.FullSun;
			Toxicity myToxic = Toxicity.MajorToxicity;
			WateringSchedule myWateringSchedule = new WateringSchedule();
			plant = plantService.createPlant(myPlantId, myGivenName, myBotanicalName, myCommonName, mySun, mySoilType,
					myToxic, myBloomTime, id,null);
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
			Integer myPlantId = 2;
			Integer id =5;
			String myBotanicalName = "Botanical";
			String myGivenName = "MyPlant";
			String myCommonName = "Common";
			// List<BloomTime> Bloomtime = EnumSet.of( Bloomtime.);
			BloomTime myBloomTime = BloomTime.Fall;
			List<BloomTime> myBloomiTime = new ArrayList<>();
			myBloomiTime.add(myBloomTime);
			SoilType mySoilType = SoilType.Chalky;
			SunExposure mySun = SunExposure.FullSun;
			Toxicity myToxic = Toxicity.MajorToxicity;
			WateringSchedule myWateringSchedule = null;
			plant = plantService.createPlant(myPlantId, myGivenName, myBotanicalName, myCommonName, mySun, mySoilType,
					myToxic, myBloomTime, null,null);
		} catch (Exception e) {
			// Check that no error occurred
			error = e.getMessage();
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
			// List<BloomTime> Bloomtime = EnumSet.of( Bloomtime.);
			BloomTime myBloomTime = BloomTime.Fall;
			List<BloomTime> myBloomiTime = new ArrayList<>();
			// myBloomiTime.add(myBloomTime);
			SoilType mySoilType = SoilType.Chalky;
			SunExposure mySun = SunExposure.FullSun;
			Toxicity myToxic = Toxicity.MajorToxicity;
			WateringSchedule myWateringSchedule = null;
			plant = plantService.createPlant(myPlantId, myGivenName, myBotanicalName, myCommonName, mySun, mySoilType,
					myToxic, myBloomTime, null,null);

		} catch (Exception e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		assertNull(plant);
		assertNotNull(error);

	}

	@Test
	public void testCreatePlantWrongId() {
		Plant plant = null;
		String error = "";
		try {
			Integer id = 1;
			Integer myPlantId = 1111;
			String myBotanicalName = "Botanical";
			String myGivenName = "MyPlant";
			String myCommonName = "Common";
			// List<BloomTime> Bloomtime = EnumSet.of( Bloomtime.);
			BloomTime myBloomTime = BloomTime.Fall;
			List<BloomTime> myBloomiTime = new ArrayList<>();
			// myBloomiTime.add(myBloomTime);
			SoilType mySoilType = SoilType.Chalky;
			SunExposure mySun = SunExposure.FullSun;
			Toxicity myToxic = Toxicity.MajorToxicity;
			WateringSchedule myWateringSchedule = null;
			plant = plantService.createPlant(myPlantId, myGivenName, myBotanicalName, myCommonName, mySun, mySoilType,
					myToxic, myBloomTime, 1,myPlantId);
		} catch (Exception e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		assertNull(plant);
		assertNotNull(error);

	}

	@Test
	public void updateCreatePlantPass() {
		Plant plant = null;
		try {

			String myBotanicalName = "Botanical";
			String myGivenName = "MyPlant";
			Integer icon = 3;
			String myCommonName = "Common";
			// List<BloomTime> Bloomtime = EnumSet.of( Bloomtime.);
			BloomTime myBloomTime = BloomTime.Fall;
			List<BloomTime> myBloomiTime = new ArrayList<>();
			myBloomiTime.add(myBloomTime);
			SoilType mySoilType = SoilType.Chalky;
			SunExposure mySun = SunExposure.FullSun;
			Toxicity myToxic = Toxicity.MajorToxicity;
			WateringSchedule myWateringSchedule = new WateringSchedule();
			plant = plantService.updatePlant(PLANTID, myGivenName, icon, myBotanicalName, myCommonName, mySun,
					mySoilType, myToxic, myBloomTime, myWateringSchedule);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(plant);
	}

	@Test
	public void addPlantSuccess() {
		Plant plant2 = null;

		String error = "";

		String memberId = "1";

		try {
			// plant = plantService.createPlant(myPlantId, myGivenName, myBotanicalName,
			// myCommonName, mySun, mySoilType, myToxic, myBloomiTime, myWateringSchedule);
			// plantRepository.save(plant);

			plant2 = plantService.addPlant(PLANTID, USERNAME1,GIVENNAME);
		} catch (Exception e) {

			error = e.getMessage();
		}
		// assertNotNull(plant);

		assertNotNull(plant2);

	}

	@Test
	public void addPlantWrongId() {

		Plant plant2 = null;
		String error = null;
		String memberId = "1";
		Integer myPlantId = 2;
		String given = "mine";
		
		try {

			plant2 = plantService.addPlant(myPlantId, memberId,given);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(plant2);
		assertNotNull(error);

	}

}*/
