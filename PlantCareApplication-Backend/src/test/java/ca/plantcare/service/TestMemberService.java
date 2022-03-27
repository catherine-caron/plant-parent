package ca.plantcare.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.plantcare.dao.*;
import ca.plantcare.models.*;

@ExtendWith(MockitoExtension.class)
public class TestMemberService {

	private static final String USERNAME1 = "Username1";
	private static final String NAME1 = "Rajaa Bk";
	private static final String PASSWORD1 = "RajaaBk";
	private static final String EMAIL1 = "RajaaBK@icloud.com";


	private static final String GIVENNAME1 = "Elia";
	private static final String BOTANICALNAME1 = "Sunflower";
	private static final String COMMONNAME1 = "Sunflower";
	private static final Integer PLANTID1 = 123;

	private static final String USERNAME2 = "Username2";
	private static final String NAME2 = "Catherine Carbon";

	Plant addedPlant = new Plant();
	Member addedMember = new Member();

	@Mock
	private MemberRepository memberRepository;
	@Mock
	private PlantRepository plantRepository;

	@InjectMocks
	private MemberService memberService;

	@BeforeEach
	public void setMockOutput() {
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
			} 
			else if (invocation.getArgument(0).equals(USERNAME2)) {
				Member user = new Member();
				user.setUsername(USERNAME2);
				user.setName(NAME2);
				return user;
			}
			else {
				return null;
			}
		});
		lenient().when(memberRepository.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
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

			Member user2 = new Member();
			user2.setUsername(USERNAME2);
			user2.setName(NAME2);

			List<Member> members = new ArrayList<Member>();
			members.add(user);
			members.add(user2);
			return members;
		});
		lenient().when(plantRepository.findById(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(PLANTID1)) {
				Plant plant1 = new Plant();
				plant1.setGivenName(GIVENNAME1);
				plant1.setCommonName(COMMONNAME1);
				plant1.setBotanicalName(BOTANICALNAME1);
				plant1.setPlantId(PLANTID1);
				return plant1;
			} 
			else {
				return null;
			}
		});
		// Whenever anything is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(memberRepository.save(any(Member.class))).thenAnswer(returnParameterAsAnswer);
		// Used for Delete Tests
		lenient().when(plantRepository.save(any(Plant.class))).thenAnswer(returnParameterAsAnswer);
	}

	/**
	 * Create member successfully
	 */
	@Test
	public void testCreateMemberSuccessfully() {
		String username = "newUsername";
		Member user = null;
		try {
			user = memberService.createMember( PASSWORD1, username, NAME1, EMAIL1);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(user);
		assertEquals(username, user.getUsername());
		assertEquals(NAME1, user.getName());
	}

	/**
	 * Create member with empty username
	 */
	@Test
	public void testCreateMemberEmptyUsername(){
		String username = "";
		Member user = null;
		String error = null;
		try {
			user = memberService.createMember( PASSWORD1,username, NAME1, EMAIL1);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(user);
		// check error
		assertEquals("Username cannot be empty.", error);
	}

	/** 
	 * Create member with spaces in username
	 */
	@Test
	public void testCreateMemberSpacesInUsername(){
		String username = "this is a bad username";
		Member user = null;
		String error = null;
		try {
			user = memberService.createMember( PASSWORD1,username, NAME1, EMAIL1);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(user);
		// check error
		assertEquals("Username cannot contain spaces.", error);
	}

	/**
	 * Create member with a taken username
	 */
	@Test
	public void testCreateMemberTakenUsername(){
		Member user = null;
		String error = null;
		try {
			user = memberService.createMember( PASSWORD1,USERNAME1, NAME1, EMAIL1);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(user);
		// check error
		assertEquals("This username is not available.", error);
	}

	/**
	 * Create member with empty name
	 */
	@Test
	public void testCreateMemberEmptyName(){
		String username = "Joe";
		String email = "test@gmail.com";
		String password = "123";
		String name = "";
		Member user = null;
		String error = null;
		try {
			user = memberService.createMember(username, name, password, email);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(user);
		// check error
		assertEquals("Name cannot be empty.", error);
	}

	/**
	 * Update member successfully	
	 */
	@Test
	public void testUpdateMemberSuccessfully() {
		String newName = "New Name";
		Member user = null; 
		try {
			user = memberService.updateMember(USERNAME1, newName, EMAIL1, PASSWORD1);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(user);
		assertEquals(USERNAME1, user.getUsername());
		assertEquals(newName, user.getName());
	}

	/**isEmailAvailable
	 * Update member with empty username	
	 */
	@Test
	public void testUpdateMemberEmptyUsername() {
		String username = "undefined";
		String newName = "New Name";
		Member user = null; 
		String error = null;
		String email = "test@gmail.com";
		String password = "123";
		try {
			user = memberService.updateMember(username, newName, email, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(user);
		// check error
		assertEquals("One or more fields empty. Please try again.", error);
	}

	/**
	 * Update member with username not found	
	 */
	@Test
	public void testUpdateMemberUsernameNotFound() {
		String username = "Joe42";
		String newName = "New Name";
		String email = "test@gmail.com";
		String password = "123";
		Member user = null; 
		String error = null;
		try {
			user = memberService.updateMember(username, newName, email, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(user);
		// check error
		assertEquals("The member cannot be found.", error);
	}

	/**
	 * Update member with empty name	
	 */
	@Test
	public void testUpdateMemberEmptyName() {
		String newName = "      ";
		Member user = null; 
		String error = null;
		try {
			user = memberService.updateMember(USERNAME1, newName, EMAIL1, PASSWORD1);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(user);
		// check error
		assertEquals("Name cannot be empty.", error);
	}

	/**
	 * Update number of plants successfully
	 */
	@Test
	public void testUpdateNumberOfPlantsSuccessfully() {
		assertEquals(memberRepository.findMemberByUsername(USERNAME1).getNumberOfPlants(), 1);
		int newNumOfPlants = 3;
		Member user = null; 
		try {
			user = memberService.updateNumberOfPlants(USERNAME1, newNumOfPlants);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(user);
		assertEquals(USERNAME1, user.getUsername());
		assertEquals(newNumOfPlants, user.getNumberOfPlants());
	}

	/**
	 * Update number of plants username not found
	 */
	@Test
	public void testUpdateNumberOfPlantsUsernameNotFound() {
		String username = "Joe42";
		int newNumOfPlants = 3;
		Member user = null; 
		String error = null;
		try {
			user = memberService.updateNumberOfPlants(username, newNumOfPlants);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(user);
		// check error
		assertEquals("The member cannot be found.", error);
	}

	/**
	 * Update number of plants with negative number
	 */
	@Test
	public void testUpdateNumberOfPlantsNegative() {
		int newNumOfPlants = -3;
		Member user = null; 
		String error = null;
		try {
			user = memberService.updateNumberOfPlants(USERNAME1, newNumOfPlants);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(user);
		// check error
		assertEquals("Number of plants cannot be negative.", error);
	}

	/**
	 * Delete member successfully
	 */
	@Test
	public void testDeleteMemberSuccessfully() {
		Member user = null; 
		try {
			user = memberService.deleteMember(USERNAME1);
		} catch (IllegalArgumentException e) {
			fail();
		}
		Member savedUser = memberService.getMemberByUsername(USERNAME1);
		assertNotNull(user);
		assertEquals(savedUser.getUsername(), user.getUsername());
		assertEquals(savedUser.getName(), user.getName());
	}

	/**
	 * Delete member with empty username
	 */
	@Test
	public void testDeleteMemberEmptyUsername() {
		String username = "undefined";
		Member user = null; 
		String error = null;
		try {
			user = memberService.deleteMember(username);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(user);
		// check error
		assertEquals("Username empty. Please try again.", error);
	}

	/**
	 * Delete member with username not found
	 */
	@Test
	public void testDeleteMemberUsernameNotFound() {
		String username = "Joe42";
		Member user = null; 
		String error = null;
		try {
			user = memberService.deleteMember(username);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(user);
		// check error
		assertEquals("The member cannot be found.", error);
	}



}