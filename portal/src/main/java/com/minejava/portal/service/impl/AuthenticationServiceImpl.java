package com.minejava.portal.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.minejava.portal.dao.request.SignUpRequest;
import com.minejava.portal.dao.request.SigninRequest;
import com.minejava.portal.dao.response.JwtAuthenticationResponse;
import com.minejava.portal.persistence.CustomUserDetails;
import com.minejava.portal.persistence.Role;
import com.minejava.portal.persistence.UserRepository;
import com.minejava.portal.service.AuthenticationService;
import com.minejava.portal.service.JwtService;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationServiceImpl(UserRepository userRepository,
			JwtService jwtService, PasswordEncoder encoder, 
			AuthenticationManager authManager) {
		this.authenticationManager = authManager;
		this.userRepository = userRepository;
		this.jwtService = jwtService;
		this.passwordEncoder = encoder;
		
	}
	/**
	 * Creates user by collecting information provided in user form fields
	 * Generates token and builds Jwt token using the provided services.
	 * @param request represents a sign up request object.
	 */
	@Override
	public JwtAuthenticationResponse signup(SignUpRequest request) {
		// Implement user sign up
		var user = CustomUserDetails.builder().firstName(request.getFirstName()).lastName(request.getLastName())
				.email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
				.role(Role.USER).build();
		// Save into user repository
		userRepository.save(user);
		// Generate token
		var jwt = jwtService.generateToken(user);
		// Build and return token
		return JwtAuthenticationResponse.builder().token(jwt).build();
	}

	@Override
	public JwtAuthenticationResponse signin(SigninRequest request) {
		// Implemented Auto-generated method stub
		// Login via authentication manager and provide login credentials: which return tokens
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		var user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("Invalid Email or Password"));
		// Generate token
		var jwt = jwtService.generateToken(user);
		
		return JwtAuthenticationResponse.builder().token(jwt).build();
	}

}
