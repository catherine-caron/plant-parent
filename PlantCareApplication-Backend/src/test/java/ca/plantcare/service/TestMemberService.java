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

	// member has username, numberOfPlants, name, List<Plant> plant

	private static final String USERNAME1 = "Username1";
	private static final String NAME1 = "Rajaa Bk";

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

	// todo:

	// find by username

	// update name successfully
	// update name empty username
	// update name empty name
	// update name username not found
	// update name empty name/only spaces

	// update numplant successfully
	// update numplant user not found
	// update numplant negative number

	// delete member successfully
	// delete username empty
	// delete username not found

	/**
	 * Create member successfully
	 */
	@Test
	public void testCreateMemberSuccessfully() {
		String username = "newUsername";
		Member user = null;
		try {
			user = memberService.createMember(username, NAME1);
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
			user = memberService.createMember(username, NAME1);
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
			user = memberService.createMember(username, NAME1);
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
			user = memberService.createMember(USERNAME1, NAME1);
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
		String name = "";
		Member user = null;
		String error = null;
		try {
			user = memberService.createMember(username, name);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(user);
		// check error
		assertEquals("Name cannot be empty.", error);
	}

}