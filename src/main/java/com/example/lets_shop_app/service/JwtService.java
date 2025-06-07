package com.example.lets_shop_app.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.lets_shop_app.dao.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private final static String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
	
	private Key getSignInKey() {
		byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyByte);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
					.setSigningKey(getSignInKey())
					.build()
					.parseClaimsJws(token)
					.getBody();
	}
	
	public <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public String extractUsername(String token) {
		return extractClaims(token, Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
		return extractClaims(token, Claims::getExpiration);
	}
	
	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
	}
	
	public String genrateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts.builder()
					.addClaims(extraClaims)
					.setSubject(userDetails.getUsername())
//					.claim("roles", userDetails.getAuthorities().toString())
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis()+ 1000*60*60*24))
					.signWith(getSignInKey(), SignatureAlgorithm.HS256)
					.compact();
	}
	
	public String genrateToken(UserDetails userDetails) {
		return genrateToken(new HashMap<>(), userDetails);
	}
}
