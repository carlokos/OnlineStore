package com.javaschool.OnlineStore.controllers;

import com.javaschool.OnlineStore.dtos.CreateNewUserDto;
import com.javaschool.OnlineStore.dtos.UserDto;
import com.javaschool.OnlineStore.dtos.passwordDto;
import com.javaschool.OnlineStore.services.UserService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	

	@GetMapping
	public ResponseEntity<List<UserDto>> getAllUsers(){
		List<UserDto> result = userService.getAllUsers();
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
		UserDto result = userService.getUserById(id);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/current")
	public ResponseEntity<UserDto> getCurrentUser(){
		UserDto result = userService.getCurrentUser();
		return ResponseEntity.ok(result);
	}

	@PostMapping()
	public ResponseEntity<String> createNewUser(@RequestBody CreateNewUserDto dto){
		userService.createNewUser(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body("User created succesfully");
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody CreateNewUserDto dto){
		userService.updateUser(id, dto);
		return ResponseEntity.ok("User changed succesfully");
	}

	@PutMapping("/password/{id}")
	public ResponseEntity<String> updatePassword(@PathVariable Long id, @RequestBody passwordDto dto){
		userService.updatePassword(id, dto);
		return ResponseEntity.ok("Password changed succesfully");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id){
		userService.deleteUser(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User deleted succesfully");
	}
}
