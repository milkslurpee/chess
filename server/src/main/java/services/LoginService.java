package services;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import models.Authtoken;
import models.User;
import requests.LoginRequest;
import responses.loginResponse;

import java.util.UUID;

/**
 * The LoginService class provides a service for user login.
 */
public class LoginService {
    AuthDAO authDAO;
    UserDAO userDAO;
    GameDAO gameDAO;
    public LoginService(AuthDAO authDAO, UserDAO userDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        this.userDAO = userDAO;
    }

    /**
     * Logs in a user based on the provided login request.
     *
     * @param loginRequest The login request containing the user's credentials.
     * @return A loginResponse indicating the success of the login operation and providing user information.
     */
    public loginResponse login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // Validate the user's credentials
        User user = null;
        try {
            user = userDAO.read(username);
        } catch (DataAccessException e) {

            System.out.println("User doesn't exist");
            return new loginResponse(null, null,false, "User doesn't exist");
        }

        if (user != null && user.getPassword().equals(password)) {
            AuthDAO authDAO = new AuthDAO();
            String authToken = generateAuthToken();
            try {
                authDAO.insert(new Authtoken(authToken, username));
            } catch (DataAccessException e) {
                throw new RuntimeException(e);
            }
            return new loginResponse(username, authToken,true, "Login successful");
        } else {
            return new loginResponse(null, null,false, "Invalid username or password");
        }
    }

    public String generateAuthToken(){
        return UUID.randomUUID().toString();
    }
}