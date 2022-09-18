package server.main.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

	//---------------------------------------------------------------
	private static final long serialVersionUID = -2550185165626007488L;

	//---- Validity of the token in seconds
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 *60;

	//---- Wire up the value of the secret from the application.properties
	@Value("${jwt.secret}")
	private String secret;
	//---------------------------------------------------------------

	/**
	 * Retrieves user name from JWT token.
	 * @param token the issued JWT token
	 * @return plain user name, not encrypted
	 */
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}


	/**
	 * This returns the expiration date of the JWT token. (currently not used)
	 * @param token the issued JWT token
	 * @return the expiration date as Date class
	 */
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	/**
	 * Generates claim from token.
	 * @param token
	 * @param claimsResolver
	 * @param <T>
	 * @return
	 */
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * Sets secret key for the specified token for retrieving information from the token.
	 * @param token the issued token
	 * @return token claims
	 */
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	/**
	 * Check token expiration date.
	 * @param token the issued token
	 * @return true if not ex
	 */
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	/**
	 * Generates JWT token from user details.
	 * @param userDetails user name and password
	 * @return JWT token as String
	 */
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}


	/**
	 * Generates JWT token from claims, expiration date, issuer etc.
	 * 1. Primes the token builder with passed parameters: claims, subject
	 * 2. Signs the JWT token using HS512 algoirthm and secret key.
	 * 3. According to JWS Compact Serialization compaction of the JWT to a URL-safe string
	 * @param claims
	 * @param subject
	 * @return
	 */
	private String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	/**
	 * Validates JWT token from user details and the token.
	 * @param token the issued token
	 * @param userDetails user name, password
	 * @return true, if vaildated successfully, else false.
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}