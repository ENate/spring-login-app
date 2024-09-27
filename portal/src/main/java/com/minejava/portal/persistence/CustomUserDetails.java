
package com.minejava.portal.persistence;


import lombok.AllArgsConstructor;

import org.springframework.data.annotation.Id;
// import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@AllArgsConstructor
//@Document(collection = "_user")
public class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String username;
    //@Column(unique = true)
    private String email;
    @JsonIgnore
    private String password;
    // @Enumerated(EnumType.STRING)
    private Collection<? extends GrantedAuthority> authorities;
    
    public static CustomUserDetails build(UserEntity user) {
    	// Get User roles and convert List<Role> -> List<Granted>
    	// So as to effectively work with Spring Security
    	// and Authentication.
    	List<GrantedAuthority> authorities =  user
    			.getRoles()
    			.stream()
    			.map(role -> new SimpleGrantedAuthority(role.getName().name()))
    			.collect(Collectors.toList());
    	// Return UserEntity collected from the User details service
    	// and stored in the UserDetails interface.
    	return new CustomUserDetails(
    			user.getId(),
    			user.getUsername(),
    			user.getEmail(),
    			user.getPassword(),
    			authorities);
    }

    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    public String getId() {
    	return id;
    }
    
    public String getEmail() {
    	return email;
    }

    @Override
    public String getUsername() {
        // email in our case
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    @Override
    public String getPassword() {
    	return password;
    }
    
    @Override
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (o == null || getClass() != o.getClass())
        return false;
      CustomUserDetails user = (CustomUserDetails) o;
      return Objects.equals(id, user.id);
    }
    
    
}
