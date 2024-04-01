package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.UserService;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.UserRequest;
import com.example.demo.exception.RaiseException;
import com.example.demo.model.User;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000") 
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;
	
	// Login
	@PostMapping("/login")
	public ResponseEntity<User> loginUser(@RequestBody LoginRequest loginRequest) throws RaiseException{
		if(loginRequest.getUsername() == null || loginRequest.getPassword() == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword()),HttpStatus.ACCEPTED);
	}
	
	//Add new User
	@PostMapping("/adduser")
	public ResponseEntity<User> addUser(@Valid @RequestBody UserRequest userRequest) throws RaiseException {
		return new ResponseEntity<>(userService.addUser(userRequest), HttpStatus.CREATED);
	}
	
	@GetMapping("/getallusers")
	public ResponseEntity<List<UserRequest>> getAllUsers() throws RaiseException {
		return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.CREATED);
	}
	
	@PutMapping("/updateuser")
	public ResponseEntity<User> updateUser(@RequestParam String userId, @RequestParam String name, @RequestParam String contact) throws RaiseException {
		if(userId == null || name == null || contact == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(userService.updateUser(userId, name, contact), HttpStatus.CREATED);
	}
	
	// Admin granting access to users
	@PutMapping("/grantaccess")
	public ResponseEntity<User> grantAccess(@RequestParam String requestId) throws RaiseException{
		if(requestId == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(userService.grantAccess(requestId),HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/deleteuser")
	public ResponseEntity<String> deleteUser(@RequestParam String requestId) throws RaiseException{
		if(requestId == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		userService.deleteUser(requestId);
		return new ResponseEntity<>( "Successful" ,HttpStatus.ACCEPTED);
	}
}
