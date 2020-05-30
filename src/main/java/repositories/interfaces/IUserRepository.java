package repositories.interfaces;

import domain.LoginData;
import domain.User;

public interface IUserRepository extends IEntityRepository<User> {

    User getUserByID(long id);

    User getUserByLogin(LoginData data);
}
