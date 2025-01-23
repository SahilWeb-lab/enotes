package com.test.service.impl;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.test.model.User;
import com.test.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {
	
	private String secretKey = "";

	public JwtServiceImpl() {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk = keyGenerator.generateKey();
		 	secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String generateToken(User user) {
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", user.getId());
		claims.put("role", user.getRole());
		claims.put("status", user.getStatus());
		
		String token = Jwts.builder().claims()
				.add(claims)
				.subject(user.getEmail())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 60 * 60 * 60 *10))
				.and()
				.signWith(getKey())
				.compact();
		
		return token;
	}

	private Key getKey() {
		byte[] decode = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(decode);
	}

	@Override
	public String extractUsername(String token) {
		Claims claims = extractAllClaims(token);
		return claims.getSubject();
	}
	
//	If we want to get role:
	public String extractRole(String token) {
		Claims claims = extractAllClaims(token);
		return (String) claims.get("role");
	}

	private Claims extractAllClaims(String token) {
		Claims claims = Jwts.parser().verifyWith(decryptKey(secretKey)).build().parseSignedClaims(token).getPayload();
		return claims;
	}

	private SecretKey decryptKey(String secretKey2) {
		byte[] decode = Decoders.BASE64.decode(secretKey2);
		return Keys.hmacShaKeyFor(decode);
	}

	@Override
	public Boolean validateToken(String token, UserDetails userDetails) {
		String username = extractUsername(token);
		Boolean isExpired = isTokenExpired(token);
		
		if(username.equalsIgnoreCase(userDetails.getUsername()) && !isExpired) {
			return true;
		}
		
		return false;
	}

	private Boolean isTokenExpired(String token) {
		
		Claims claims = extractAllClaims(token);
		Date expirationDate = claims.getExpiration();
		
		return expirationDate.before(new Date());
	}
	
	

}
