package services;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import models.Authtoken;
import models.User;
import requests.RegisterRequest;
import responses.registerResponse;

import java.util.UUID;

/**
 * The RegisterService class provides a service for user registration.
 */
public class RegisterService {
    AuthDAO authDAO;
    UserDAO userDAO;
    GameDAO gameDAO;
    public RegisterService(AuthDAO authDAO, UserDAO userDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        this.userDAO = userDAO;
    }

    /**
     * Registers a new user based on the provided registration request.
     *
     * @param request The registration request containing the user's information.
     * @return A registerResponse indicating the success of the registration operation.
     */

    public registerResponse register(RegisterRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();

        try {
            // Check if the username already exists
            if (userDAO.read(username) != null) {
                return new registerResponse(null, null, false, "Username already taken");
            }
        } catch (DataAccessException e) {
            // If the exception is thrown because the user does not exist, continue
            if (!e.getMessage().equals("User doesn't exist")) {
                return new registerResponse(null, null, false, "Data access error: " + e.getMessage());
            }
        }

        // Create a new user and save to the database
        User newUser = new User(username, password, email);
        try {
            userDAO.insert(newUser);
        } catch (DataAccessException e) {
            return new registerResponse(null, null, false, "Data access error: " + e.getMessage());
        }

        // Generate an auth token for the new user
        String authToken = generateAuthToken();
        try {
            authDAO.insert(new Authtoken(authToken, username));
        } catch (DataAccessException e) {
            return new registerResponse(null, null, false, "Data access error: " + e.getMessage());
        }

        return new registerResponse(username, authToken, true, "Registration successful");
    }

    private String generateAuthToken() {
        return UUID.randomUUID().toString();
    }
}
