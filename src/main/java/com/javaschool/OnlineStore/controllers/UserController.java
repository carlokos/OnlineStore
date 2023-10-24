package com.javaschool.OnlineStore.controllers;

import com.javaschool.OnlineStore.dtos.CreateNewUserDto;
import com.javaschool.OnlineStore.dtos.LogInDto;
import com.javaschool.OnlineStore.dtos.UserDto;
import com.javaschool.OnlineStore.services.UserService;

import lombok.RequiredArgsConstructor;

import java.util.List;

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

	@GetMapping("/login")
	public ResponseEntity<UserDto> logInUser(@RequestBody LogInDto dto){
		UserDto result = userService.getUserByLogIn(dto);
		return ResponseEntity.ok(result);
	}

	@PostMapping
	public ResponseEntity<String> createNewUser(@RequestBody CreateNewUserDto dto){
		userService.createNewUser(dto);
		return ResponseEntity.status(201).body("User created succesfully");
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody CreateNewUserDto dto){
		userService.updateUser(id, dto);
		return ResponseEntity.ok("User changed succesfully");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id){
		userService.deleteUser(id);
		return ResponseEntity.status(204).body("User deleted succesfully");
	}
}
