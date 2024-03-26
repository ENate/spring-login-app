package com.minejava.portal.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity(name = "user_profile")
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
	private Integer id;

	private String username;
	private String password;
}
