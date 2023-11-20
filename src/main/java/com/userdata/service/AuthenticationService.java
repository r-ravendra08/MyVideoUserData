package com.userdata.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.userdata.dto.LoginUserDto;
import com.userdata.dto.RegisterUserDto;
import com.userdata.entity.Users;
import com.userdata.repository.UsersRepository;

@Service
public class AuthenticationService {
	private final UsersRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManager authenticationManager;

	public AuthenticationService(UsersRepository userRepository, AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public Users signup(RegisterUserDto input) {
		Users user = new Users();
		user.setName(input.getName());
		user.setMobile(input.getMobile());
		user.setEmail(input.getEmail());
		user.setPassword(passwordEncoder.encode(input.getPassword()));

		return userRepository.save(user);
	}

	public Users authenticate(LoginUserDto input) {
	 authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(input.getMobileOrEmail(), input.getPassword()));

		return userRepository.findByMobileOrEmail(input.getMobileOrEmail(),input.getMobileOrEmail()).orElseThrow();
	}
}
