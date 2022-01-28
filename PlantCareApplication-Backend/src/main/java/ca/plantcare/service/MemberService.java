package ca.plantcare.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.plantcare.dao.MemberRepository;
import ca.plantcare.models.Member;
import ca.plantcare.dao.PlantRepository;
import ca.plantcare.models.Plant;

@Service
public class MemberService {

    @Autowired
	private MemberRepository memberRepository;
	@Autowired
	private PlantRepository plantRepository;

	/**
	 * Create a Member with given parameters
	 * @param username
	 * @param name
	 * @param numberOfPlants
	 * @return the account created
	 */
	@Transactional
	public Member createMember(String username, String name){

        if (username == null || username.replaceAll("\\s+", "").length() == 0 || username.equals("undefined")) {
			throw new IllegalArgumentException("Username cannot be empty.");
		}
		else if (username.contains(" ")) {
			throw new IllegalArgumentException("Username cannot contain spaces.");
		}
		else if (isUsernameAvailable(username) == false) {
			throw new IllegalArgumentException("This username is not available.");
		}
		// else if (password == null || password.replaceAll("\\s+", "").length() == 0 || password.equals("undefined")) {
		// 	throw new IllegalArgumentException("Password cannot be empty.");
		// }
		// else if (password.contains(" ")) {
		// 	throw new IllegalArgumentException("Password cannot contain spaces.");
		// }
		// else if ((email == null || email.replaceAll("\\s+", "").length() == 0 || email.equals("undefined"))) {
		// 	throw new IllegalArgumentException("Email cannot be empty.");
		// }
		// else if (email.contains(" ")) {
		// 	throw new IllegalArgumentException("Email cannot contain spaces.");
		// }
		// else if (isEmailAvailable(email) == false) {
		// 	throw new IllegalArgumentException("This email is not available.");
		// }

		else if (name == null || name.replaceAll("\\s+", "").length() == 0 || name.equals("undefined")){ //name.trim().length() == 0
			throw new IllegalArgumentException("Name cannot be empty.");
		}
		else {
			Member member = new Member();
			member.setUsername(username);
			member.setName(name);
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
		Member member = memberRepository.findMemberByUsername(username);
		return member;
	}

	// /**
	//  * Find a member by email
	//  * @param email
	//  * @return the member
	//  */
	// @Transactional
	// public Member getMemberByEmail(String email) {
	// 	Member member = memberRepository.findMemberByEmail(email);
	// 	return member;
	// }
	
	/**
	 * Update member name. 
	 * Allowed to pass same old name.
	 * @param username
	 * @param newName
	 * @return the member updated
	 */
	@Transactional
	public Member updateMember(String username, String newName, String newPhoneNumber) {
		// public Member updateMember(String username, String newPassword, String newEmail, String newName, String newPhoneNumber)
		if (username.equals("undefined") || newName.equals("undefined")) {
			throw new IllegalArgumentException("One or more fields empty. Please try again.");
		}
		else {
			Member member = memberRepository.findMemberByUsername(username);
			if (member == null) {
				throw new IllegalArgumentException("The member cannot be found.");
			}
			// else if ( !member.getEmail().equals(newEmail) && !isEmailAvailable(newEmail)){
			// 	throw new IllegalArgumentException("This email is not available.");
			// }
			// else if (newEmail == null || newEmail.replaceAll("\\s+", "").length() == 0 || newEmail.equals("undefined")) {
			// 	throw new IllegalArgumentException("Email cannot be empty.");
			// }
			// else if (newEmail.contains(" ")) {
			// 	throw new IllegalArgumentException("Email cannot contain spaces.");
			// }
			// else if (newPassword == null || newPassword.replaceAll("\\s+", "").length() == 0 || newPassword.equals("undefined")) {
			// 	throw new IllegalArgumentException("Password cannot be empty.");
			// }
			// else if (newPassword.contains(" ")) {
			// 	throw new IllegalArgumentException("Password cannot contain spaces.");
			// }
			else if (newName == null || newName.replaceAll("\\s+", "").length() == 0 || newName.equals("undefined")){
				throw new IllegalArgumentException("Name cannot be empty.");
			}
			else {
				member.setName(newName);
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
		Member member = memberRepository.findMemberByUsername(username);
		if (member == null) {
			throw new IllegalArgumentException("The member cannot be found.");
		}
		else if (newNumberOfPlants < 0){
			throw new IllegalArgumentException("Number of plants cannot be negative.");
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
			throw new IllegalArgumentException("Username empty. Please try again.");
		}
		else {
			Member member = memberRepository.findMemberByUsername(username);
			if(member == null) {
				throw new IllegalArgumentException("The member cannot be found.");
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
		if (memberRepository.findMemberByUsername(username) != null) {
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
		if (memberRepository.findMemberByEmail(email) != null) {
			return available;
		}
		else{ 
			available = true;
			return available;
		}
	}

	/**
	 * Helper method that converts iterable to list
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

