package services;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import models.User;
import requests.RegisterRequest;
import responses.registerResponse;

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
            User existingUser = userDAO.read(username);
            if (existingUser != null) {
                return new registerResponse(false, "Error: Username already taken");
            } else {
                User newUser = new User(username, password, email);
                userDAO.insert(newUser);
                return new registerResponse(true, "Registration successful");
            }
        } catch (DataAccessException e) {
            return new registerResponse(false, "Error: Registration failed");
        }
    }
}