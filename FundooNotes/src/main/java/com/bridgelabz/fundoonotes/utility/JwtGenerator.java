package com.bridgelabz.fundoonotes.utility;

import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
/**
 * 
 * @author Srijan Kumar
 *
 */
@Component
public class JwtGenerator {
	private static final String SECRET = "897048816";
	/*
	 * Method that is used to generate the jwt token 
	 */
	public String JwtToken(long l) {
		String token = null;
		try {
			token = JWT.create().withClaim("id", l).sign(Algorithm.HMAC256(SECRET));
		}catch(IllegalArgumentException | JWTCreationException e)
		{
			e.printStackTrace();
		}
		return token;
	}
	/*
	 * Method used to parse of the jwt Token
	 */
	public long parseJWT(String jwt)
	{
		Long userId = (long) 0;
		if (jwt != null) {
			userId = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(jwt).getClaim("id").asLong();
		}
		return userId;
		
	}

}
