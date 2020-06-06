package repositories.interfaces;

import domain.LoginData;
import domain.User;

import java.util.List;

public interface IUserRepository extends IEntityRepository<User> {

    User getUserByID(long id);

    User getUserByLogin(LoginData data);

    User getUserByUsername(String username);

    List<User> getListOfUsers();
}
