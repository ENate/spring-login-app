package com.minejava.portal.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.minejava.portal.persistence.entity.UserEntity;

@Service
public class UserServiceImpl implements IUserService {

    @Override
    public UserEntity findByUsername(String username) {
        // TO DO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUsername'");
    }

	@Override
	public UserDetailsService userDetailsService() {
		// TO BE DONE Auto-generated method stub
		return null;
	}
}
