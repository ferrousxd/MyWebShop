package controllers;

import domain.User;
import filters.customAnnotation.JWTTokenNeeded;
import org.glassfish.jersey.media.multipart.FormDataParam;
import repositories.UserRepository;
import services.UserService;
import services.interfaces.IUserService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.Date;

@Path("/users")
public class UserController {
    private IUserService userService = new UserService();

    @GET
    public String index() {
        return "Hello from User!";
    }

    @JWTTokenNeeded
    @GET
    @Path("/{id}")
    public Response getUserByID(@PathParam("id") long id) {
        User user;
        try {
            user = userService.getUserByID(id);
        } catch (ServerErrorException ex) {
            return Response
                    .serverError().build();
        } catch (BadRequestException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Something went wrong...").build();
        }

        if (user == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("User does not exist!")
                    .build();
        } else {
            return Response
                    .status(Response.Status.OK)
                    .entity(user)
                    .build();
        }
    }

    @POST
    @Path("/register")
    public Response getUserRegister(@FormDataParam("name") String name,
                                    @FormDataParam("surname") String surname,
                                    @FormDataParam("username") String username,
                                    @FormDataParam("password") String password,
                                    @FormDataParam("birthday") String birthday)
    {
        UserRepository userrepo = new UserRepository();
        User user = new User(name, surname, username, password, Date.valueOf(birthday));
        try {
            userrepo.add(user);
        } catch (BadRequestException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("This user cannot be created!").build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(user)
                .build();
    }
}
