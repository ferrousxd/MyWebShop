package services;

import domain.User;
import repositories.UserRepository;
import repositories.interfaces.IUserRepository;
import services.interfaces.IUserService;

public class UserService implements IUserService {
    private IUserRepository userrepo = new UserRepository();

    @Override
    public User getUserByID(long id) {
        return userrepo.getUserByID(id);
    }
}
