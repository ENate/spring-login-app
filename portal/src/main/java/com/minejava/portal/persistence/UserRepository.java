package com.minejava.portal.persistence;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<UserEntity, String> {

    Optional<UserEntity> findByEmail(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
