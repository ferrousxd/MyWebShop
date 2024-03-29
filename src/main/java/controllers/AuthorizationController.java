package controllers;

import domain.AccessToken;
import domain.LoginData;
import services.AuthorizationService;
import services.interfaces.IAuthorizationService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("auth")
public class AuthorizationController {
    private IAuthorizationService authService = new AuthorizationService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginData data) {
        try {
            AccessToken token = authService.authenticateUser(data);
            return Response.ok(token).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
