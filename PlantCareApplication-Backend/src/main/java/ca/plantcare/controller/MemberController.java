package ca.plantcare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import antlr.collections.List;

import static ca.plantcare.extra.HttpUtil.httpFailureMessage;
import static ca.plantcare.extra.HttpUtil.httpSuccess;
import static ca.plantcare.extra.HttpUtil.httpFailure;

import ca.plantcare.dto.*;
import ca.plantcare.models.*;
import ca.plantcare.service.*;

@CrossOrigin(origins = "*")
@RestController
public class MemberController {

    @Autowired
	private MemberService memberService;

	// /**
	//  * Return a list of all Member Dtos 
	//  * 
	//  * @return list of Member Dtos
	//  */
	// @GetMapping(value = { "/getAllMembers", "/getAllMembers/" })
	// public List<MemberDto> getAllMembers() {
	// 	return memberService.getAllMembers().stream().map(u -> convertToDto(u)).collect(Collectors.toList());
	// }

	/**
	 * Return the member with specified username
	 * 
	 * @param username
	 * @return Member Dto
	 */
	@GetMapping(value = { "/getMemberByUsername/{username}", "/getMemberByUsername/{username}/" })
	public MemberDto getMemberByUsername(@PathVariable("username") String username) {
		return convertToDto(memberService.getMemberByUsername(username));
	}

	/**
	 * Create a Member Dto with provided parameters
	 * 
	 * @param username
	 * @param password
	 * @param name
	 * @return Member  Dto
	 */
	@PostMapping(value = { "/createMember/{username}/{email}/{password}/{name}", "/createMember/{username}/{email}/{password}/{name}/" })
	public MemberDto createMember(@PathVariable("username") String username, @PathVariable("email") String email, @PathVariable("password") String password, @PathVariable("name") String name)  {
		Member user = memberService.createMember(username, email, password, name);
		return convertToDto(user);
	}


	/**
	 * Update a Member Dto 
	 * If not changing something, pass old value
	 * 
	 * @param username
	 * @param newPassword
	 * @param newName
	 * @return Member  Dto
	 */
	@PutMapping(value = {"/updateMember/{username}/{newName}/{newEmail}/{newPassword}", "/updateMember/{username}/{newName}/{newEmail}/{newPassword}/" })
	public MemberDto updateMember(@PathVariable("username") String username, @PathVariable("newName") String newName, @PathVariable("newEmail") String newEmail, @PathVariable("newPassword") String newPassword)   {
		Member user = memberService.updateMember(username, newName, newEmail, newPassword);
		return convertToDto(user);
	}

	/**
	 * Delete member
	 * 
	 * @param username
	 * @return boolean if successful
	 */
	@PutMapping(value = { "/deleteMember/{username}", "/deleteMember/{username}/" })
	public MemberDto deleteMember(@PathVariable("username") String username)   {
		Member user = memberService.deleteMember(username);
		return convertToDto(user);
	}

	/**
	 * Login and generate token
	 * 
	 * @param username
	 * @param password
	 * @return boolean if successful
	 */
	@PutMapping(value = {"/loginMember/{username}/{password}", "/loginMember/{username}/{password}/" })
	public MemberDto loginMember(@PathVariable("username") String username, @PathVariable("password") String password)   {
		Member user = memberService.loginMember(username, password);
		return convertToDto(user);
	}

	/**
	 * Logout and delete token
	 * 
	 * @param username
	 * @return boolean if successful
	 */
	@PutMapping(value = {"/logoutMember/{username}", "/logoutMember/{username}/" })
	public MemberDto logoutMember(@PathVariable("username") String username)   {
		Member user = memberService.logoutMember(username);
		return convertToDto(user);
	}

	/**
	 * Authenticate token
	 * 
	 * @param username
	 * @return boolean authenticity
	 */
	@PostMapping(value = {"/authenticateMember/{username}", "/authenticateMember/{username}/" })
	public MemberDto authenticateMember(@PathVariable("username") String username)  {
		Member user = memberService.authenticateMember(username);
		return convertToDto(user);
	}

	/**
	 * Get the member associated to specified car
	 * 
	 * @param license plate for car
	 * @return
	 */
	@GetMapping(value = { "/getMemberByCar/{licensePlate}", "/getMemberByCar/{licensePlate}/" })
	public MemberDto getMemberByCar(@PathVariable("licensePlate") String licensePlate) {
		return convertToDto(memberService.getMemberWithCar(licensePlate));
	}



	//-------------------------- Helper Methods -----------------------------

	/**
	 * Helper Method to convert an member to a Dto
	 * 
	 * @param user
	 * @return MemberDto
	 */
	private MemberDto convertToDto(Member user){
		if (user == null) {
			throw new InvalidInputException("This user does not exist");
		}
		MemberDto memberDto = new MemberDto(user.getUsername(), user.getPassword(), user.getName());

		if (user.getCar() != null) {
			memberDto.setCars(user.getCar().stream().map(c -> convertToDto(c)).collect(Collectors.toList()));
		}
		memberDto.setToken(user.getToken());

		return memberDto;
	}

	/**
	 * Helper Method to convert a car to a Dto
	 * Will return null if you pass null
	 * 
	 * @param car
	 * @return CarDto
	 */
	private CarDto convertToDto(Car car)  {
		if (car == null) {
			return null;
		}
		else {
			CarDto carDto = new CarDto(car.getLicensePlate(), car.getModel(), car.getYear(), car.getMotorType());
			return carDto;
		}
	}	


}
