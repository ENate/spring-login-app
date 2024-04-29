package com.minejava.portal.persistence;

import java.util.Optional;

import com.minejava.portal.persistence.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUsername(String username);
}
