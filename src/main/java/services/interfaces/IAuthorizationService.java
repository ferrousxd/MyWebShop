package services.interfaces;

import domain.AccessToken;
import domain.LoginData;

public interface IAuthorizationService {
    AccessToken authenticateUser(LoginData data) throws Exception;
}
