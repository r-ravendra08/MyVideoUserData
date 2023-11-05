package com.userdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.userdata.entity.AuthRequest;
import com.userdata.entity.UserInfo;
import com.userdata.repository.UserInfoRepository;
import com.userdata.service.JwtService;
import com.userdata.service.UserInfoService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("http://localhost:5173")
public class UserController {

	@Autowired
	private UserInfoService service;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome this endpoint is not secure";
	}

	@PostMapping("/addNewUser")
	public ResponseEntity<?> registerUser(@RequestBody UserInfo userInfo) {

		// add check for mobile exists in a DB
		if (userInfoRepository.existsByMobile(userInfo.getMobile())) {
			return new ResponseEntity<>("Mobile is already taken!", HttpStatus.BAD_REQUEST);
		}

		// add check for email exists in DB
		if (userInfoRepository.existsByEmail(userInfo.getEmail())) {
			return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
		}
		
		service.addUser(userInfo);
		return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

	}

	@GetMapping("/user/userProfile")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String userProfile() {
		return "Welcome to User Profile";
	}

	@GetMapping("/admin/adminProfile")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String adminProfile() {
		return "Welcome to Admin Profile";
	}

	@PostMapping("/generateToken")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			return jwtService.generateToken(authRequest.getUsername());
		} else {
			throw new UsernameNotFoundException("invalid user request !");
		}
	}

}
