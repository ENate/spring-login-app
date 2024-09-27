package com.minejava.portal.config;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.minejava.portal.service.CustomUserDetailsService;
import com.minejava.portal.service.util.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenFilter extends OncePerRequestFilter {
	
	private final JwtUtils jwtUtils;
	
	private final CustomUserDetailsService userDetailsService;
	
	public JwtTokenFilter(CustomUserDetailsService userDetails,
			JwtUtils jwtUtils) {
		this.userDetailsService = userDetails;
		this.jwtUtils = jwtUtils;
		
	}

	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request, 
			@NonNull HttpServletResponse response, 
			@NonNull FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
		      String jwt = parseJwt(request);
		      if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
		        String username = jwtUtils.getUserNameFromJwtToken(jwt);

		        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
		            userDetails.getAuthorities());
		        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		        SecurityContextHolder.getContext().setAuthentication(authentication);
		      }
		    } catch (Exception e) {
		      logger.error("Cannot set user authentication: {}", e);
		    }
		filterChain.doFilter(request, response);
	}
	private String parseJwt(HttpServletRequest request) {
	    String jwt = jwtUtils.getJwtFromCookies(request);
	    return jwt;
	  }

}
