package services;

import domain.AccessToken;
import domain.SigninData;
import domain.User;
import repositories.entities.UserRepository;

public class AuthorizationService {
    private final UserRepository userepo = new UserRepository();

    public AccessToken signIn(SigninData data) throws Exception {
        User autorizeUser = userepo.findUserByLogin(data);
        if (autorizeUser == null) {
            throw new Exception("Authorization Failed!");
        }
        AccessToken token = new AccessToken(getToken(autorizeUser));
        return token;
    }

    private String getToken(User user) {
        String token = user.getUsername() + ":" + user.getPassword();
        return token;
    }
}
