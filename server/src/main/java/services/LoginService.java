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

    public loginResponse login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // Validate the user's credentials
        User user = null;
        try {
            user = userDAO.read(username);
            System.out.println("User found: " + user.getUsername());
        } catch (DataAccessException e) {
            System.out.println("User doesn't exist");
            return new loginResponse(null, null, false, "User doesn't exist");
        }

        if (user.getPassword().equals(password)) {
            String authToken = generateAuthToken();

            try {
                authDAO.insert(new Authtoken(authToken, username));
            } catch (DataAccessException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Successful login of user " + user.getUsername());
            return new loginResponse(user.getUsername(), authToken, true, "Login successful");
        } else {
            System.out.println("Invalid username or password");
            return new loginResponse(null, null, false, "Invalid username or password");
        }
    }

    public String generateAuthToken() {
        return UUID.randomUUID().toString();
    }
}
