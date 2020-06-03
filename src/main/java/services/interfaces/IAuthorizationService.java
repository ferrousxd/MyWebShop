package services.interfaces;

import domain.AccessToken;
import domain.LoginData;
import domain.User;

public interface IAuthorizationService {
    AccessToken authenticateUser(LoginData data) throws Exception;

    User getUserByUsername(String issuer);
}
