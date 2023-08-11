package cl.bci.bcitest.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
  private static  String SECRET_KEY = "bci-test";
  private static  final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

  public  String generateToken(final String userName, final String secret) {
    return JWT.create()
        .withSubject(userName)
        .withIssuer(secret)
        .withIssuedAt(new Date())
        .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10)))
        .sign(ALGORITHM);
  }
}
