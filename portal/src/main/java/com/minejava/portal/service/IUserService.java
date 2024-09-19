package com.minejava.portal.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.minejava.portal.persistence.entity.UserEntity;

public interface IUserService {

    UserEntity findByUsername(String username);
    UserDetailsService userDetailsService();
}
