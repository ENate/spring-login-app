package com.minejava.portal.config;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.minejava.portal.service.IUserService;
import com.minejava.portal.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final IUserService userService;

	public JwtAuthenticationFilter(JwtService jwtService, IUserService userService) {
		this.jwtService = jwtService;
		this.userService = userService;
	}

	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request,
			@NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain)
			throws ServletException, IOException {
		// Get Authorization header
		String header = request.getHeader("Authorization");
		final String jwt;
		final String userEmail;
		// Checks whether use is authenticated
		if (StringUtils.isEmpty(header) || !StringUtils.startsWith(header, "Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		jwt = header.substring(7);
		userEmail = jwtService.extractUserName(jwt);
		if (StringUtils.isNotEmpty(userEmail) &&
				SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);
			if (jwtService.isTokenValid(jwt, userDetails)) {
				SecurityContext context = SecurityContextHolder.createEmptyContext();
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null,
						userDetails.getAuthorities());
				context.setAuthentication(authToken);

				SecurityContextHolder.setContext(context);
			}
		}
		filterChain.doFilter(request, response);

	}

}
