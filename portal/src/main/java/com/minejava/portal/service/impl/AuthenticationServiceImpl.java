package com.minejava.portal.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.minejava.portal.dao.request.SignUpRequest;
import com.minejava.portal.dao.request.SigninRequest;
import com.minejava.portal.dao.response.JwtAuthenticationResponse;
import com.minejava.portal.persistence.UserEntity;
import com.minejava.portal.persistence.UserRepository;
import com.minejava.portal.service.AuthenticationService;
import com.minejava.portal.service.JwtService;
import com.minejava.portal.service.util.JwtUtils;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final JwtUtils jwtUtils;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationServiceImpl(
		UserRepository userRepository,
			JwtService jwtService, PasswordEncoder encoder, 
			AuthenticationManager authManager,
			JwtUtils jwtUtils) {
		this.authenticationManager = authManager;
		this.userRepository = userRepository;
		this.jwtService = jwtService;
		this.passwordEncoder = encoder;
		this.jwtUtils = jwtUtils;
	}
	/**
	 * Creates user by collecting information provided in user form fields
	 * Generates token and builds Jwt token using the provided services.
	 * @param request represents a sign up request object.
	 */
	@Override
	public JwtAuthenticationResponse signup(SignUpRequest request) {
		// Implement user sign up
		UserEntity customUser = new UserEntity(
			request.getUsername(),
			request.getEmail(),
			request.getPassword()
		);
		// Save into user repository
		userRepository.save(customUser);
		// Generate token
		var jwt = jwtUtils.generateTokenFromUsername(request.getUsername());
		// var jwt = jwtService.generateToken(customUser);
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
		//var jwt = jwtService.generateToken(user);
		var jwtToken = jwtUtils.generateTokenFromUsername(request.getEmail());

		return JwtAuthenticationResponse.builder().token(jwtToken).build();
	}

}
