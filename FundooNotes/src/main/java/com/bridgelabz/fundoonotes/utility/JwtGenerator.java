package com.bridgelabz.fundoonotes.utility;

import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

@Component
public class JwtGenerator {
	private static final String SECRET = "897048816";
	
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
	
	public long parseJWT(String jwt)
	{
		Long userId = (long) 0;
		if (jwt != null) {
			userId = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(jwt).getClaim("id").asLong();
		}
		return userId;
		
	}

}
