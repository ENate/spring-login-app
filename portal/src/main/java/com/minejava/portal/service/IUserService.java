package com.minejava.portal.service;

import com.minejava.portal.persistence.entity.UserEntity;

public interface IUserService {

    UserEntity findByUsername(String username);
}
