package com.minejava.portal.persistence;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Document(collection = "tbl_users")
public class UserEntity {
	
	// Define fields
	@Id
	private String id;
	@NotBlank
	@Max(value = 20)
	private String username;
	
	@NotBlank(message = "Email field cannot be empty")
	@Size(max = 30)
	private String email;
	
	@Size(max = 120)
	@NotBlank(message = "Password filed cannot be empty")
	private String password;
	
	Set<Role> roles = new HashSet<>();
	
	public UserEntity() {
	}
	
	public UserEntity(String username, String email, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
	}
	
	public String getId() {
	    return id;
	  }

	  public void setId(String id) {
	    this.id = id;
	  }

	  public String getUsername() {
	    return username;
	  }

	  public void setUsername(String username) {
	    this.username = username;
	  }

	  public String getEmail() {
	    return email;
	  }

	  public void setEmail(String email) {
	    this.email = email;
	  }

	  public String getPassword() {
	    return password;
	  }

	  public void setPassword(String password) {
	    this.password = password;
	  }

	  public Set<Role> getRoles() {
	    return roles;
	  }

	  public void setRoles(Set<Role> roles) {
	    this.roles = roles;
	  }
	
}
