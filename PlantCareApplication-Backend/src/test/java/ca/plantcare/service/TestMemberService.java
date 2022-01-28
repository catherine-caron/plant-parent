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

	// member has username, 

	private static final String USERNAME1 = "Username1";
	private static final String NAME1 = "Alicia";
	private static final String PASSWORD1 = "PassWord123!";
	private static final int TOKEN1 = 1000000;

	private static final String USERNAME2 = "Username2";
	private static final String NAME2 = "Catherine The 1st";
	private static final String PASSWORD2 = "GhostPassword101?!";
	private static final int TOKEN2 = 0; //invalid

	private static final String LICENSE = "123ABC";
	private static final String LICENSE2 = "123ABCD";
	Car addCar = new Car();
	CustomerAccount ownerWithCar = new CustomerAccount();



	@Mock
	private CustomerAccountRepository customerAccountRepository;
	@Mock
	private CarRepository carRepository;
	@Mock
	private AdminAccountRepository adminAccountRepository;
	@Mock
	private TechnicianAccountRepository technicianAccountRepository;


	@InjectMocks
	private CustomerAccountService customerAccountService;


	@BeforeEach
	public void setMockOutput() {
		lenient().when(customerAccountRepository.findByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(USERNAME1)) {
				CustomerAccount user = new CustomerAccount();
				user.setUsername(USERNAME1);
				user.setPassword(PASSWORD1);
				user.setName(NAME1);
				user.setToken(TOKEN1);
				List<Car> cars = new ArrayList<Car>();
				Car car = new Car();
				car.setLicensePlate(LICENSE2);
				cars.add(car);
				user.setCar(cars);
				return user;
			} 
			else if (invocation.getArgument(0).equals(USERNAME2)) {
				CustomerAccount user = new CustomerAccount();
				user.setUsername(USERNAME2);
				user.setPassword(PASSWORD2);
				user.setName(NAME2);
				user.setToken(TOKEN2);
				return user;
			}
			else {
				return null;
			}
		});
		lenient().when(customerAccountRepository.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
			CustomerAccount user = new CustomerAccount();
			user.setUsername(USERNAME1);
			user.setPassword(PASSWORD1);
			user.setName(NAME1);
			user.setToken(TOKEN1);
			List<Car> cars = new ArrayList<Car>();
			Car car = new Car();
			car.setLicensePlate(LICENSE2);
			cars.add(car);
			user.setCar(cars);
			CustomerAccount user2 = new CustomerAccount();
			user2.setUsername(USERNAME2);
			user2.setPassword(PASSWORD2);
			user2.setName(NAME2);
			user2.setToken(TOKEN2);
			List<CustomerAccount> customerAccounts = new ArrayList<CustomerAccount>();
			customerAccounts.add(user);
			customerAccounts.add(user2);
			return customerAccounts;
		});
		lenient().when(customerAccountRepository.findCustomerAccountByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(NAME1)) {
				CustomerAccount user = new CustomerAccount();
				user.setUsername(USERNAME1);
				user.setPassword(PASSWORD1);
				user.setName(NAME1);
				user.setToken(TOKEN1);
				List<CustomerAccount> customerAccounts = new ArrayList<CustomerAccount>();
				customerAccounts.add(user);
				return customerAccounts;
			} 
			else if (invocation.getArgument(0).equals(NAME2)) {
				CustomerAccount user = new CustomerAccount();
				user.setUsername(USERNAME2);
				user.setPassword(PASSWORD2);
				user.setName(NAME2);
				user.setToken(TOKEN2);
				List<CustomerAccount> customerAccounts = new ArrayList<CustomerAccount>();
				customerAccounts.add(user);
				return customerAccounts;
			}
			else {
				return null;
			}
		});
		lenient().when(carRepository.findByLicensePlate(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(LICENSE)) {
				Car car = new Car();
				car.setLicensePlate(LICENSE);
				return car;
			} 
			else if(invocation.getArgument(0).equals(LICENSE2)) {
				addCar.setLicensePlate(LICENSE2);
				return addCar;
			} 
			else {
				return null;
			}
		});
		// Whenever anything is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(customerAccountRepository.save(any(CustomerAccount.class))).thenAnswer(returnParameterAsAnswer);
		// Used for Delete, Authenticate, and Login/out Tests
		lenient().when(carRepository.save(any(Car.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(adminAccountRepository.save(any(AdminAccount.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(technicianAccountRepository.save(any(TechnicianAccount.class))).thenAnswer(returnParameterAsAnswer);

	}

	/**
	 * Create Customer Account successfully
	 */
	@Test
	public void testCreateCustomerAccountSuccessfully() {
		assertEquals(2, customerAccountService.getAllCustomerAccounts().size());

		String username = "newUsername";
		CustomerAccount user = null;
		try {
			user = customerAccountService.createCustomerAccount(username, PASSWORD1, NAME1);
		} catch (InvalidInputException e) {
			// Check that no error occurred
			fail();
		}
		//CustomerAccount savedUser = customerAccountService.getCustomerAccountByUsername(USERNAME1);
		assertNotNull(user);
		assertEquals(username, user.getUsername());
		assertEquals(PASSWORD1, user.getPassword());
		assertEquals(NAME1, user.getName());
	}
}