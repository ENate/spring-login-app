package com.minejava.portal.service;

import com.minejava.portal.persistence.User;

public interface IUserService {

    User findByUsername(String username);
}
