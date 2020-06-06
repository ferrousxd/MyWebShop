package filters;

import domain.User;
import filters.customAnnotation.JWTTokenNeeded;
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
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;

@JWTTokenNeeded
@Provider
@Priority(Priorities.AUTHORIZATION)

public class SecurityFilter implements ContainerRequestFilter {
    private final IAuthorizationService authService = new AuthorizationService();

    private static final String AUTHENTICATION_SCHEME = "Bearer ";

    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (!isTokenBasedAuthentication(authorizationHeader)) {
            abortWithUnauthorized(requestContext);
            return;
        }

        String token = authorizationHeader
                .substring(AUTHENTICATION_SCHEME.length()).trim();

        try {
            final User user = validateToken(token);

            requestContext.setSecurityContext(new SecurityContext() {
                @Override
                public Principal getUserPrincipal() {
                    return (UserPrincipal) () -> user.getUsername();
                }

                @Override
                public boolean isUserInRole(String s) {
                    return (user.getRole() != null && user.getRole().equals(s));
                }

                @Override
                public boolean isSecure() {
                    return false;
                }

                @Override
                public String getAuthenticationScheme() {
                    return null;
                }
            });

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
                        .header(HttpHeaders.WWW_AUTHENTICATE,
                                AUTHENTICATION_SCHEME + " realm=\"default\"")
                        .build());
    }

    private User validateToken(String token) throws Exception {
        String secretWord = "AFOUYGEIBEGRYR4IU3UH4TYUHOIT4Q3*@#$U!UY$@$$OFJS(@";
        Jws<Claims> result = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretWord.getBytes()))
                .build().parseClaimsJws(token);

        User user = authService.getUserByUsername(result.getBody().getIssuer());
        return user;
    }
}
