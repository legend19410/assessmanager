package com.specops.assetmanager.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.specops.assetmanager.exceptions.InvalidJwtException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

@Service
public class JwtUtil {
	
	private String SECRET_KEY = "secret";
	private Algorithm signingAlgorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());;
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
		
	}
	
	public String extractJwt(String bearerToken) {
		return bearerToken.substring(7);
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		String secretKey = TextCodec.BASE64URL.encode(SECRET_KEY);
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}
	
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
//	public String generateToken(UserDetails userDetails) {
//		Map<String, Object> claims = new HashMap<>();
//		return createToken(claims, userDetails.getUsername());
//	}
	
	public String createAccessToken(UserDetails user, String issuer) {
		
		System.out.println(user.getUsername());
		System.out.println(issuer);
		
		System.out.println(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
		return 	JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 1 * 60 * 1000))
				.withIssuer(issuer)
				.withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(signingAlgorithm);
	}
	
	public String createRefreshToken(UserDetails user, String issuer) {
		return JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 30000 * 60 * 1000))
				.withIssuer(issuer)
				.sign(signingAlgorithm);
	}
	
	public DecodedJWT verifyToken(String token) throws InvalidJwtException {
		if(token != null && token.startsWith("Bearer ")) {
			try {
				String jwt = token.substring("Bearer ".length());
				System.out.println("22222222222");
				System.out.println(jwt);
				JWTVerifier verifier = JWT.require(signingAlgorithm).build();
				DecodedJWT decodedJWT = verifier.verify(jwt);
				return decodedJWT;
				
			}catch(Exception e) {
	
				System.out.println(e);
				throw new InvalidJwtException("Verification of your refresh token has failed...");
			}
		}else {
			throw new InvalidJwtException("Verification of your refresh token has failed...");
		}
	}
	
	public String extractVerifiedTokenOwner(String token) throws InvalidJwtException {
		DecodedJWT decodedJWT = this.verifyToken(token);
		return decodedJWT.getSubject();
	}
	
//	private String createToken(Map<String, Object> claims, String subject) {
//		
//		String tkn = Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//				.setExpiration(new Date(System.currentTimeMillis() + 60000 * 2))
//				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
//		return tkn;
//	}
	
	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}

