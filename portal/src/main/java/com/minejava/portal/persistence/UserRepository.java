package com.minejava.portal.persistence;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<CustomUserDetails, Integer> {

    Optional<CustomUserDetails> findByEmail(String username);
}
