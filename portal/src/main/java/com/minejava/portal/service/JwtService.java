package com.minejava.portal.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
	
	// Some methods to implement
	String extractUserName(String token);
	String generateToken(UserDetails userDetails);
	boolean isTokenValid(String token, UserDetails userDetails);

}
