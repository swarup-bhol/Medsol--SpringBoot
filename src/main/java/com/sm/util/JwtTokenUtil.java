  package com.sm.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.sm.model.User;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

import static com.sm.util.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;
import static com.sm.util.Constants.SIGNING_KEY;

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @author swarupb
	 * 
	 * @purpose get username from token
	 * 
	 * @param token
	 * @return String
	 */
	public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

	
	/**
	 * 
	 * @author swarupb
	 * 
	 * @param token
	 * @return  Date
	 */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * @author swarupb
     * 
     * @param <T>
     * @param token
     * @param claimsResolver
     * @return
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * @author swarupb
     * 
     * @param token
     * @return Claim
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    
    /**
     * @author swarupb
     * 
     * @param token
     * @return Boolean
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    
    /**
     * @author swarupb
     * 
     * @param user
     * @return String
     */
    public String generateToken(User user) {
        return doGenerateToken(user.getUserEmail());
    }

    
    /**
     * @author swarupb
     * 
     * @param subject
     * @return String
     */
    private String doGenerateToken(String subject) {

        Claims claims = Jwts.claims().setSubject(subject);
        claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ADMIN")));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }

    
    /**
     * @author swarupb
     * 
     * @param token
     * @param userDetails
     * @return boolean
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (
              username.equals(userDetails.getUsername())
                    && !isTokenExpired(token));
    }

}
