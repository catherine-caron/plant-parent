package ca.plantcare.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.vehiclerepairshop.dao.MemberRepository;
import ca.mcgill.ecse321.vehiclerepairshop.model.Member;

@Service
public class MemberService {

    @Autowired
	private MemberRepository memberRepository;

    // methods to write:
    // createMember
    // getMemberByUsername
    // getMemberByEmail
    // updateMember
    // deleteMember
    // loginMember
    // logoutMember

	/**
	 * Create a Membert with given parameters
	 * @param username
	 * @param password
	 * @param email
	 * @param name
	 * @param phoneNumber can be empty if they don't want a phone number
	 * @param numberOfPlants
	 * @return the account created
	 */
	@Transactional
	public Member createMember(String username, String password, 
                                String email, String name, String phoneNumber, int numberOfPlants){

        if (username == null || username.replaceAll("\\s+", "").length() == 0 || username.equals("undefined")) {
			throw new InvalidInputException("Username cannot be empty.");
		}
		else if (username.contains(" ")) {
			throw new InvalidInputException("Username cannot contain spaces.");
		}
		else if (isUsernameAvailable(username) == false) {
			throw new InvalidInputException("This username is not available.");
		}
		else if (password == null || password.replaceAll("\\s+", "").length() == 0 || password.equals("undefined")) {
			throw new InvalidInputException("Password cannot be empty.");
		}
		else if (password.contains(" ")) {
			throw new InvalidInputException("Password cannot contain spaces.");
		}
		else if ((email == null || email.replaceAll("\\s+", "").length() == 0 || email.equals("undefined")) {
			throw new InvalidInputException("Email cannot be empty.");
		}
		else if (email.contains(" ")) {
			throw new InvalidInputException("Email cannot contain spaces.");
		}
		else if (isEmailAvailable(email) == false) {
			throw new InvalidInputException("This email is not available.");

		else if (name == null || name.replaceAll("\\s+", "").length() == 0 || name.equals("undefined")){ //name.trim().length() == 0
			throw new InvalidInputException("Name cannot be empty.");
		}
		else {
			Member member = new Member();
			member.setUsername(username);
			member.setPassword(password);
			member.setName(name);
			member.setEmail(email);
			member.setPhoneNumber(phoneNumeber);
			member.setNumberOfPlants(0); // initally no plants
			memberRepository.save(member);
			return member;
		}
    }


    // ---------- helper methods --------------

	/**
	 * Helper method to search through all members and see if the username is already in use
	 * @param username
	 * @return
	 */
	private boolean isUsernameAvailable(String username) {
		boolean available = false;
		if (memberRepository.findByUsername(username) != null) {
			return available;
		}
		else{ 
			available = true;
			return available;
		}
	}

    /**
	 * Helper method to search through all members and see if the email is already in use
	 * @param email
	 * @return
	 */
	private boolean isEmailAvailable(String email) {
		boolean available = false;
		if (memberRepository.findByEmail(email) != null) {
			return available;
		}
		else{ 
			available = true;
			return available;
		}
	}