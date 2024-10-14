package com.payswiff.mfmsproject.security;

import java.security.Key;
import java.sql.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	@Value("${app.jwt-secret}")
	private String jwtSecretKey;
	@Value("${app-jwt-expiration-milliseconds}")
	private Long jwtExpiration;

	// generate jwt token
	public String generateToken(Authentication authentication) {

		String username = authentication.getName();
		java.util.Date currentDate =  new java.util.Date();

		java.util.Date expireDate = new java.util.Date(currentDate.getTime() + jwtExpiration);

		String token = Jwts.builder().subject(username).issuedAt(currentDate).expiration(expireDate).signWith(key())
				.compact();
		return token;

	}

	// generate key
	public Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
	}

	// get username from jwt token
	public String getUsername(String token) {

		return Jwts.parser()
				.verifyWith((SecretKey) key())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}
	
	//validate jwt token
	public boolean validate(String token) throws Exception
	{
		try {
			Jwts.parser()
			.verifyWith((SecretKey) key())
			.build()
			.parse(token);
			
		} catch (MalformedJwtException e) {
			// TODO: handle exception
			throw new Exception("Invalid jwt token");
		}
		catch (ExpiredJwtException e) {
			// TODO: handle exception
			throw new Exception("jwt token expired");
		}
		catch (UnsupportedJwtException e) {
			// TODO: handle exception
			throw new Exception("unsupported jwt token");
		}
		catch (IllegalArgumentException e) {
			// TODO: handle exception
			throw new Exception("String is empty");
		}
		return true;
	}
}
