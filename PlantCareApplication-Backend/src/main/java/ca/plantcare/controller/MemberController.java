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

import java.util.List;
import java.util.stream.Collectors;

import static ca.plantcare.extra.HttpUtil.httpFailureMessage;
import static ca.plantcare.extra.HttpUtil.httpSuccess;


import static ca.plantcare.extra.HttpUtil.httpFailure;

import ca.plantcare.dto.MemberDto;
import ca.plantcare.dto.PlantDto;
import ca.plantcare.models.*;
import ca.plantcare.service.*;

@CrossOrigin(origins = "*")
@RestController
public class MemberController {

    @Autowired
	private MemberService memberService;
    
	private static final String BASE_URL = "/member";
	/**
	 * Return the member with specified username
	 * 
	 * @param username
	 * @return Member Dto
	 */
	@GetMapping(value = { BASE_URL+ "/getMemberByUsername/{username}", BASE_URL+ "/getMemberByUsername/{username}/" })
	public MemberDto getMemberByUsername(@PathVariable("username") String username) {
		Member member = memberService.getMemberByUsername(username);
        return MemberDto.convertToDto(member);
	}
	

	// /**
	//  * Return the member with specified email
	//  * 
	//  * @param email
	//  * @return Member Dto
	//  */
	// @GetMapping(value = { "/getMemberByEmail/{email}", "/getMemberByEmail/{email}/" })
	// public MemberDto getMemberByEmail(@PathVariable("email") String email) {
	// 	Member member = memberService.getMemberByEmail(email);
    //     return MemberDto.converToDto(member);
	// }


	// /**
	//  * Update the number of plants 
	//  * 
	//  * @param username
	//  * @param numPlants
	//  * @return Member  Dto
	//  */
	// // @PutMapping(value = {BASE_URL+ "/updateNumberOfPlants/{username}/{numPlants}", BASE_URL+ "/updateNumberOfPlants/{username}/{numPlants}/" })
	// // public MemberDto updateNumberOfPlants(@PathVariable("username") String username, @PathVariable("numPlants") int numPlants)   {
	// // 	Member user = memberService.updateNumberOfPlants(username, numPlants);
	// // 	return MemberDto.converToDto(user);
	// // }
	// @PutMapping(value = { BASE_URL + "/updateNumberOfPlants", BASE_URL+"/updateNumberOfPlants/" })
	// public ResponseEntity<?>updateNumberOfPlants(
	// 		@RequestParam("username") String username,
	// 		@RequestParam("numPlants") int numPlants)
	// 		{
		
	// 	try {
	// 		Member member = memberService.updateNumberOfPlants(username, numPlants);
	// 		return httpSuccess(MemberDto.converToDto(member));
	// 	}
	// 	catch(Exception e){
	// 		return httpFailure("Error: " + e.getMessage());
	// 	}
	// }


	/**
	 * Create a Member Dto with provided parameters
	 * 
	 * @param username
	 * @param password
	 * @param name
	 * @param email
	 * @return Member  Dto
	 */
	@PostMapping(value = { BASE_URL+"/create", BASE_URL+"/create/" })
	public ResponseEntity<?>createMember(
			@RequestParam("username") String username,
			@RequestParam("name") String name,
			@RequestParam("password") String password,
			@RequestParam("email") String email)
			{
		
		try {
			Member member = memberService.createMember(username, name, password, email);
			return httpSuccess(MemberDto.convertToDto(member));

		}
		catch(Exception e){
			return httpFailure("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Get all members
	 * @return
	 */
	@GetMapping(value = { BASE_URL, BASE_URL + "/", BASE_URL + "/get-all", BASE_URL + "/get-all/" })
	public ResponseEntity<?> getAllMembers() {
		List<MemberDto> members = null;
		try {
		//	members = memberService.getAllMembers().stream().map(member -> MemberDto.convertToDto(member)).collect(Collectors.toList());
			members = memberService.getAllMembers().stream().map(member -> MemberDto.convertToDto(member)).collect(Collectors.toList());
		} catch (Exception e) {
			return httpFailureMessage(e.getMessage());
		}
		return httpSuccess(members);
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
	// @PutMapping(value = {"/updateMember/{username}/{newName}/{newEmail}/{newPassword}", "/updateMember/{username}/{newName}/{newEmail}/{newPassword}/" })
	// public MemberDto updateMember(@PathVariable("username") String username, @PathVariable("newName") String newName, @PathVariable("newEmail") String newEmail, @PathVariable("newPassword") String newPassword)   {
	// 	Member user = memberService.updateMember(username, newName, newEmail, newPassword);
	// 	return MemberDto.converToDto(user);
	// }
	@PutMapping(value = { BASE_URL + "/updateMember", BASE_URL+"/updateMember/" })
	public ResponseEntity<?>updateMember(
			@RequestParam("username") String username,
			@RequestParam("newName") String newName, 
			@RequestParam("newEmail") String newEmail, 
			@RequestParam("newPassword") String newPassword)
			{
		
		try {
			Member member = memberService.updateMember(username, newName, newEmail, newPassword);
			return httpSuccess(MemberDto.convertToDto(member));
		}
		catch(Exception e){
			return httpFailure("Error: " + e.getMessage());
		}
	}

	/**
	 * Delete member
	 * 
	 * @param username
	 */
	// @PutMapping(value = { "/deleteMember/{username}", "/deleteMember/{username}/" })
	// public MemberDto deleteMember(@PathVariable("username") String username)   {
	// 	Member user = memberService.deleteMember(username);
	// 	return MemberDto.converToDto(user);
	// }
	@PutMapping(value = { BASE_URL + "/deleteMember", BASE_URL+"/deleteMember/" })
	public ResponseEntity<?>deleteMember(
			@RequestParam("username") String username)
			{
		
		try {
			Member member = memberService.deleteMember(username);
			return httpSuccess(MemberDto.convertToDto(member));
		}
		catch(Exception e){
			return httpFailure("Error: " + e.getMessage());
		}
	}

	/**
	 * Login and generate token
	 * 
	 * @param username
	 * @param password
	 * @return boolean if successful
	 */
	// @PutMapping(value = {"/loginMember/{username}/{password}", "/loginMember/{username}/{password}/" })
	// public MemberDto loginMember(@PathVariable("username") String username, @PathVariable("password") String password)   {
	// 	Member user = memberService.loginMember(username, password);
	// 	return MemberDto.converToDto(user);
	// }
	@PutMapping(value = { BASE_URL + "/loginMember", BASE_URL+"/loginMember/" })
	public ResponseEntity<?>loginMember(
			@RequestParam("username") String username,
			@RequestParam("password") String password)
			{
		
		try {
			Member member = memberService.loginMember(username, password);
			return httpSuccess(MemberDto.convertToDto(member));
		}
		catch(Exception e){
			return httpFailure("Error: " + e.getMessage());
		}
	}
	

	/**
	 * Logout and delete token
	 * 
	 * @param username
	 */
	// @PutMapping(value = {"/logoutMember/{username}", "/logoutMember/{username}/" })
	// public MemberDto logoutMember(@PathVariable("username") String username)   {
	// 	Member user = memberService.logoutMember(username);
	// 	return MemberDto.converToDto(user);
	// }
	@PutMapping(value = { BASE_URL + "/logoutMember", BASE_URL+"/logoutMember/" })
	public ResponseEntity<?>logoutMember(
			@RequestParam("username") String username)
			{
		
		try {
			Member member = memberService.logoutMember(username);
			return httpSuccess(MemberDto.convertToDto(member));
		}
		catch(Exception e){
			return httpFailure("Error: " + e.getMessage());
		}
	}

	/**
	 * Authenticate token
	 * 
	 * @param username
	 */
	// @PostMapping(value = {"/authenticateMember/{username}", "/authenticateMember/{username}/" })
	// public MemberDto authenticateMember(@PathVariable("username") String username)  {
	// 	Member user = memberService.authenticateMember(username);
	// 	return MemberDto.converToDto(user);
	// }
	@PutMapping(value = { BASE_URL + "/authenticateMember", BASE_URL+"/authenticateMember/" })
	public ResponseEntity<?>authenticateMember(
			@RequestParam("username") String username)
			{
		
		try {
			Member member = memberService.authenticateMember(username);
			return httpSuccess(MemberDto.convertToDto(member));
		}
		catch(Exception e){
			return httpFailure("Error: " + e.getMessage());
		}
	}


}
