package ca.plantcare.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.plantcare.dao.MemberRepository;
import ca.plantcare.model.Member;
import ca.plantcare.dao.PlantRepository;
import ca.plantcare.model.Plant;

@Service
public class MemberService {

    @Autowired
	private MemberRepository memberRepository;
	@Autowired
	private PlantRepository plantRepository;

    // methods to write:

    // loginMember  ???
    // logoutMember ???

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
	public Member createMember(String username, String password, String email, String name, String phoneNumber){

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

	/**
	 * Find a member by username
	 * @param username
	 * @return the member
	 */
	@Transactional
	public Member getMemberByUsername(String username) {
		Member member = memberRepository.findByUsername(username);
		return member;
	}

	/**
	 * Find a member by email
	 * @param email
	 * @return the member
	 */
	@Transactional
	public Member getMemberByEmail(String email) {
		Member member = memberRepository.findByEmail(email);
		return member;
	}
	
	/**
	 * Update member password, name, email, and phoneNumber. 
	 * If one parameter shouldn't change, pass old value as new value. 
	 * @param username
	 * @param newPassword
	 * @param newName
	 * @param newEmail
	 * @param newPhoneNumber - can be empty/erased
	 * @return the member updated
	 */
	@Transactional
	public Member updateMember(String username, String newPassword, String newEmail, String newName, String newPhoneNumber) {
		if (username.equals("undefined") || newPassword.equals("undefined") || newName.equals("undefined") || newEmail.equals("undefined")) {
			throw new InvalidInputException("One or more fields empty. Please try again.");
			// note that phone number can be empty/undefined
		}
		else {
			Member member = memberRepository.findByUsername(username);
			if (member == null) {
				throw new InvalidInputException("The member cannot be found.");
			}
			else if ( !member.getEmail.equals(newEmail) && !isEmailAvailable(newEmail)){
				throw new InvalidInputException("This email is not available.");
			}
			else if (newEmail == null || newEmail.replaceAll("\\s+", "").length() == 0 || newEmail.equals("undefined")) {
				throw new InvalidInputException("Email cannot be empty.");
			}
			else if (newEmail.contains(" ")) {
				throw new InvalidInputException("Email cannot contain spaces.");
			}
			else if (newPassword == null || newPassword.replaceAll("\\s+", "").length() == 0 || newPassword.equals("undefined")) {
				throw new InvalidInputException("Password cannot be empty.");
			}
			else if (newPassword.contains(" ")) {
				throw new InvalidInputException("Password cannot contain spaces.");
			}
			else if (newName == null || newName.replaceAll("\\s+", "").length() == 0 || newName.equals("undefined")){
				throw new InvalidInputException("Name cannot be empty.");
			}
			else {
				member.setPassword(newPassword);
				member.setName(newName);
				member.setEmail(newEmail);
				member.setPhoneNumber(newPhoneNumber);
				memberRepository.save(member);
				return member;
			}
		}
	}

	/**
	 * Update numberOfPlants owned by a member
	 * @param username
	 * @param newNumberOfPlants
	 * @return the member updated
	 */
	@Transactional
	public Member updateNumberOfPlants(String username, Integer newNumberOfPlants) {
		Member member = memberRepository.findByUsername(username);
		if (member == null) {
			throw new InvalidInputException("The member cannot be found.");
		}
		else if (newNumberOfPlants < 0){
			throw new InvalidInputException("Number of plants cannot be negative.");
		}
		else {
			member.setNumberOfPlants(newNumberOfPlants);
			memberRepository.save(member);
			return member;
		}
	}

	/**
	 * Deletes the member
	 * @param username
	 * @return member deleted
	 */
	@Transactional
	public Member deleteMember(String username)  {
		if (username.equals("undefined")) {
			throw new InvalidInputException("Username empty. Please try again.");
		}
		else {
			Member member = memberRepository.findByUsername(username);
			if(member == null) {
				throw new InvalidInputException("The member cannot be found.");
			}
			else {
				if (member.getPlant() != null) {
					for (Plant plant : member.getPlant()) {
						plantRepository.delete(plant);
					}
				}
				memberRepository.delete(member);
				return member;
			}
		}
	}


    // ---------- Helper Methods --------------

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

	/**
	 *  helper method that converts iterable to list
	 * @param <T>
	 * @param iterable
	 * @return
	 */
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}

