package filters;


import domain.User;
import filters.customAnnotation.OnlyAdmin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import services.AuthorizationService;
import services.interfaces.IAuthorizationService;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;


@OnlyAdmin
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AdminFilter implements ContainerRequestFilter {
    private final IAuthorizationService authService = new AuthorizationService();

    private static final String AUTHENTICATION_SCHEME = "Bearer ";

    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (!isTokenBasedAuthentication(authorizationHeader)) {
            abortWithUnauthorized(requestContext);
            return;
        }

        String token = authorizationHeader
                .substring(AUTHENTICATION_SCHEME.length()).trim();

        try {
            validateToken(token);
        } catch (Exception e) {
            abortWithUnauthorized(requestContext);
        }
    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                .startsWith(AUTHENTICATION_SCHEME.toLowerCase());
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .build());
    }

    private void validateToken(String token) throws Exception {
        String secretWord = "AFOUYGEIBEGRYR4IU3UH4TYUHOIT4Q3*@#$U!UY$@$$OFJS(@";
        Jws<Claims> result = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretWord.getBytes()))
                .build().parseClaimsJws(token);

        User user = authService.getUserByUsername(result.getBody().getIssuer());

        if (!user.getRole().equals("admin")) {
            throw new Exception("Access denied!");
        }
    }
}
