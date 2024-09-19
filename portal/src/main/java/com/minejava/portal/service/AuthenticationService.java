package com.minejava.portal.service;

import com.minejava.portal.dao.request.SignUpRequest;
import com.minejava.portal.dao.request.SigninRequest;
import com.minejava.portal.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
	JwtAuthenticationResponse signup(SignUpRequest request);
	JwtAuthenticationResponse signin(SigninRequest request);

}
