package controllers;

import domain.User;
import filters.customAnnotation.JWTTokenNeeded;
import org.glassfish.jersey.media.multipart.FormDataParam;
import repositories.UserRepository;
import services.UserService;
import services.interfaces.IUserService;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.util.List;

@Path("/users")
public class UserController {
    private IUserService userService = new UserService();

    @GET
    public Response getListOfUsers() {
        List<User> users;
        try {
            users = userService.getListOfUsers();
        } catch (ServerErrorException ex) {
            return Response
                    .serverError()
                    .build();
        }

        if (users == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Users list is empty!")
                    .build();
        } else {
            return Response
                    .status(Response.Status.OK)
                    .entity(users)
                    .build();
        }
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
                    .serverError()
                    .build();
        } catch (BadRequestException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Something went wrong...")
                    .build();
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
                    .entity("This user cannot be created!")
                    .build();
        }
        return Response
                .status(Response.Status.OK)
                .entity("User successfully created!")
                .build();
    }

    @JWTTokenNeeded
    @POST
    @Path("/update")
    public Response getUserUpdate(@FormDataParam("id") int id,
                                  @FormDataParam("name") String name,
                                  @FormDataParam("surname") String surname,
                                  @FormDataParam("password") String password,
                                  @Context ContainerRequestContext requestContext)
    {
        UserRepository userrepo = new UserRepository();
        User user = new User(id, name, surname, password);

        if (!requestContext.getSecurityContext().isUserInRole("admin") &&
                !requestContext.getSecurityContext().getUserPrincipal().getName().equals(user.getUsername())) {
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .build();
        }

        try {
            userrepo.update(user);
        } catch (BadRequestException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Cannot update user!")
                    .build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(user)
                .build();
    }

    @JWTTokenNeeded
    @POST
    @Path("/delete")
    public Response deleteUser(@FormDataParam("id") int id,
                               @FormDataParam("username") String username,
                               @Context ContainerRequestContext requestContext)
    {
        UserRepository userRepo = new UserRepository();
        User user = new User(id, username);

        if (!requestContext.getSecurityContext().isUserInRole("admin") &&
                !requestContext.getSecurityContext().getUserPrincipal().getName().equals(user.getUsername())) {
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity("You do not permission to delete this account!")
                    .build();
        }

        try {
            userRepo.remove(user);
        } catch (BadRequestException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Can not delete the user")
                    .build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(user)
                .build();
    }
}
