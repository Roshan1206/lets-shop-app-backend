package com.example.lets_shop_app.service.impl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.example.lets_shop_app.service.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


/**
 * Service class to handle functionalities related to JWT Tokens
 */
@Service
public class JwtServiceImpl implements JwtService {

	/**
	 * Secret key for JWT token
	 */
	@Value("${secret.key}")
	private String SECRET_KEY;


	/**
	 * Converting the Secret key from String to Key
	 *
	 * @return Key
	 */
	private Key getSignInKey() {
		byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyByte);
	}


	/**
	 * Extracting all claims from JWT token.
	 *
	 * @param token User JWT token
	 * @return Claims
	 */
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
					.setSigningKey(getSignInKey())
					.build()
					.parseClaimsJws(token)
					.getBody();
	}


	/**
	 * Calls {@code extractAllClaims} function.
	 *
	 * @param token User JWT token
	 * @param claimsResolver Function to extract a particular claim
	 * @return claim
	 */
	public <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}


	/**
	 * Extract username from JWT token
	 *
	 * @param token User JWT token
	 * @return username
	 */
	@Override
	public String extractUsername(String token) {
		return extractClaims(token, Claims::getSubject);
	}


	/**
	 * Extract expiration time from JWT token
	 *
	 * @param token User JWT token
	 * @return Expiration time
	 */
	public Date extractExpiration(String token) {
		return extractClaims(token, Claims::getExpiration);
	}


	/**
	 * Validate JWT token expiration time
	 *
	 * @param token User JWT token
	 * @return true if token expired else false
	 */
	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}


	/**
	 * Validate JWT token.
	 *
	 * @param token User JWT token
	 * @param userDetails UserDetails
	 * @return true if token expired else false
	 */
	@Override
	public boolean isTokenValid(String token, UserDetails userDetails) {
		return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
	}


	/**
	 * Generate new JWT token for user with the required details/claims
	 *
	 * @param extraClaims any extra claims to be added
	 * @param userDetails UserDetails
	 * @return token
	 */
	@Override
	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts.builder()
					.addClaims(extraClaims)
					.setSubject(userDetails.getUsername())
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + 1000*60*60))
					.signWith(getSignInKey(), SignatureAlgorithm.HS256)
					.compact();
	}


	/**
	 * Generate new JWT token for user
	 *
	 * @param userDetails UserDetails
	 * @return token
	 */
	@Override
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}
}
