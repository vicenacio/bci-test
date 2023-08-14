package cl.bci.bcitest.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
  private final String secret;

  public JwtUtil(@Value("${jwt.secret}") final String secret) {
    this.secret = secret;
  }

  public String generateToken(final String id) {
    return JWT.create()
        .withSubject(id)
        .withIssuer(secret)
        .withIssuedAt(new Date())
        .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(3)))
        .sign(Algorithm.HMAC256(secret));
  }

  public boolean isTokenValid(final String jwt) {
    try {
      JWT.require(Algorithm.HMAC256(secret)).build().verify(jwt);
      return true;
    } catch (JWTVerificationException e) {
      return false;
    }
  }

  public String getIdFromToken(final String jwt) {
    try {
      return JWT.require(Algorithm.HMAC256(secret)).build().verify(jwt).getSubject();
    } catch (JWTVerificationException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  public boolean isTokenExpired(final String jwt) {
    try {
      final DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secret)).build().verify(jwt);
      return decodedJWT.getExpiresAt().before(new Date());
    } catch (TokenExpiredException e) {
      return true;
    }
  }
}
