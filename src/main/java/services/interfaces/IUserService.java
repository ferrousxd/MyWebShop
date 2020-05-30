package services.interfaces;

import domain.User;

public interface IUserService {
    User getUserByID(long id);
}
