package services;

import domain.AccessToken;
import domain.LoginData;
import domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import repositories.UserRepository;
import repositories.interfaces.IUserRepository;
import services.interfaces.IAuthorizationService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Random;

public class AuthorizationService implements IAuthorizationService {
    private IUserRepository userrepo = new UserRepository();

    @Override
    public AccessToken authenticateUser(LoginData data) throws Exception {
        User user = signIn(data);
        AccessToken token = new AccessToken(getToken(user));
        return token;
    }

    private User signIn(LoginData data) throws Exception {
        User user = userrepo.getUserByLogin(data);
        if (user == null) {
            throw new Exception("User not found!");
        }
        return user;
    }

    private String getToken(User user) {
        Instant now = Instant.now();
        String secret = "AFOUYGEIBEGRYR4IU3UH4TYUHOIT4Q3*@#$U!UY$@$$OFJS(@";
        String jwtToken = Jwts.builder()
                .setIssuer(user.getUsername())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(10, ChronoUnit.MINUTES)))
                .claim("1D20", new Random().nextInt(20) + 1)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
        return jwtToken;
    }
}
