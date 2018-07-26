package com.tcdevelop.jwtlogin.config;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticationService {

	// EXPIRATION_TIME = 10 dias
	static final long EXPIRATION_TIME = 860_000_000;
	static final String SECRET = "MySecret";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";
	
	static void addAuthentication(HttpServletResponse response, String username, Collection<? extends GrantedAuthority> authorities) {
		String JWT = Jwts.builder()
				.claim("ROLE", authorities.stream().map(s -> s.toString()).collect(Collectors.toList()))
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
		
		response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
	}
	
	static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		
		if (token != null) {
			String user = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
					.getBody()
					.getSubject();
			
			Jws<Claims> claims = Jwts.parser()
					  .setSigningKey(SECRET)
					  .parseClaimsJws(token.replace(TOKEN_PREFIX, ""));
			
			List<String> roles = claims.getBody().get("ROLE", List.class);
			
			List<GrantedAuthority> permissoes = roles.stream()
	                .map(authority -> new SimpleGrantedAuthority(authority))
	                .collect(Collectors.toList());				
			
			if (user != null) {
				
				return new UsernamePasswordAuthenticationToken(user, null, permissoes);
			}
		}
		return null;
	}
	
}