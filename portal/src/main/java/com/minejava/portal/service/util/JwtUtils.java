package com.minejava.portal.service.util;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.minejava.portal.persistence.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtils {
	private static Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${login.app.jwtSecret}")
	private String jwtSecret;

	@Value("${token.signing.key}")
	private String jwtSigningKey;

	@Value("${login.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	@Value("${login.app.jwtCookieName}")
	private String jwtCookie;

	public String getJwtFromCookies(HttpServletRequest request) {
		Cookie cookie = WebUtils.getCookie(request, jwtCookie);
		if (cookie != null) {
			return cookie.getValue();
		} else {
			return null;
		}
	}

	public ResponseCookie generateJwtCookie(CustomUserDetails userPrincipal) {
		String jwt = generateTokenFromUsername(userPrincipal.getUsername());
		ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/api").maxAge(24 * 60 * 60).httpOnly(true)
				.build();
		return cookie;
	}

	public ResponseCookie getCleanJwtCookie() {
		ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
		return cookie;
	}

	private Claims extractAllClaims(String token) {
		MacAlgorithm alg = Jwts.SIG.HS512; // or HS384 or HS256
		SecretKey key = alg.key().build();

		return Jwts
				.parser()
				.verifyWith(key)
				// .setSigningKey(getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	/**
	 * public String getUserNameFromJwtToken(String token) {
	 * MacAlgorithm alg = Jwts.SIG.HS512; //or HS384 or HS256
	 * SecretKey key = alg.key().build();
	 * return Jwts
	 * .parser()
	 * //.parserBuilder()
	 * .verifyWith(key)
	 * .build()
	 * //.setSigningKey(key()).build()
	 * .parseSignedContent(token)
	 * .getPayload();
	 * // .parseClaimsJws(token).getBody().getSubject();
	 * }
	 */
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
		final Claims claims = extractAllClaims(token);
		return claimsResolvers.apply(claims);
	}

	public String getUserNameFromJwtToken(String token) {
		Claims claims = extractClaim(token, null);
		String username = claims.getSubject();
		return username;
	}

	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
		// byte[] keyBytes = jwtSigningKey.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public boolean validateJwtToken(String authToken) {
		MacAlgorithm alg = Jwts.SIG.HS512; // or HS384 or HS256
		SecretKey key = alg.key().build();

		try {
			Jwts
					.parser()
					// .setSigningKey(key())
					.verifyWith(key)
					.build()
					.parse(authToken);
			return true;
		} catch (MalformedJwtException e) {
			LOGGER.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			LOGGER.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			LOGGER.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			LOGGER.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}

	public String generateTokenFromUsername(String username) {
		MacAlgorithm alg = Jwts.SIG.HS256;
		SecretKey key = alg.key().build();
		return Jwts.builder()
				.subject(username)
				.issuedAt(new Date())
				.expiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(getSigningKey())
				.signWith(key, alg)
				.compact();
	}

}
