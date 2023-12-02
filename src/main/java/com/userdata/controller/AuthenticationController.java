package com.userdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userdata.dto.LoginResponse;
import com.userdata.dto.LoginUserDto;
import com.userdata.dto.RegisterUserDto;
import com.userdata.entity.Users;
import com.userdata.repository.UsersRepository;
import com.userdata.service.AuthenticationService;
import com.userdata.service.JwtService;

@RequestMapping("/auth")
@RestController
@CrossOrigin("http://localhost:5173")

public class AuthenticationController {
	private final JwtService jwtService;

	private final AuthenticationService authenticationService;
	
	@Autowired
	private UsersRepository usersRepository;

	public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
		this.jwtService = jwtService;
		this.authenticationService = authenticationService;
	}
	@PostMapping("/signup")
	public ResponseEntity<?> register(@RequestBody RegisterUserDto registerUserDto) {
	    // Check if mobile number already exists
	    if (usersRepository.existsByMobile(registerUserDto.getMobile())) {
	        return new ResponseEntity<>("Mobile is already taken!", HttpStatus.BAD_REQUEST);
	    }

	    // Check if email already exists
	    if (usersRepository.existsByEmail(registerUserDto.getEmail())) {
	        return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
	    }

	    // Proceed with user registration
	    Users registeredUser = authenticationService.signup(registerUserDto);
	    
	    // Check if registration was successful
	    if (registeredUser == null) {
	        return new ResponseEntity<>("Failed to register user. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    // Return the registered user
	    return ResponseEntity.ok(registeredUser);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
		Users authenticatedUser = authenticationService.authenticate(loginUserDto);
		String jwtToken = jwtService.generateToken(authenticatedUser);

		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setToken(jwtToken);
		loginResponse.setExpiresIn(jwtService.getExpirationTime());

		return ResponseEntity.ok(loginResponse);
	}
}