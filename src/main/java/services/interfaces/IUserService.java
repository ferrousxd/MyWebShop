package services.interfaces;

import domain.User;

import java.util.List;

public interface IUserService {
    User getUserByID(long id);

    List<User> getListOfUsers();
}
