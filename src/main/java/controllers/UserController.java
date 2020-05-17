package controllers;

import domain.User;
import repositories.entities.UserRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("user")
public class UserController {

    @GET
    public String hello() {
        return "Hello, World!";
    }

    @GET
    @Path("/{id}")
    public User getUserByID(@PathParam("id") long id) {
        UserRepository userrepo = new UserRepository();
        Iterable<User> users = userrepo.query("SELECT * FROM users WHERE id = " + id);
        for (User user : users) {
            return user;
        }
        return null;
    }
}
