package services;

import dataaccess.db.*;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.DataAccessException;
import dataaccess.memory.MemoryGameDAO;
import dataaccess.memory.MemoryUserDAO;
import models.Authtoken;
import models.User;
import requests.RegisterRequest;
import responses.RegisterResponse;
import org.mindrot.jbcrypt.BCrypt;
import java.util.UUID;

/**
 * The RegisterService class provides a service for user registration.
 */
public class RegisterService {
    dbAuthDAO authDAO;
    dbUserDAO userDAO;
    dbGameDAO gameDAO;
    public RegisterService(dbAuthDAO authDAO, dbUserDAO userDAO, dbGameDAO gameDAO) {
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

    public RegisterResponse register(RegisterRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();

        try {
            // Check if the username already exists
            if (userDAO.read(username) != null) {
                return new RegisterResponse(null, null, "Username already taken");
            }
        } catch (DataAccessException e) {
            // If the exception is thrown because the user does not exist, continue
            if (!e.getMessage().equals("User doesn't exist")) {
                return new RegisterResponse(null, null, "Data access error: " + e.getMessage());
            }
        }
        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        // Create a new user and save to the database
        User newUser = new User(username, password, email);
        try {
            userDAO.insert(newUser);
        } catch (DataAccessException e) {
            return new RegisterResponse(null, null, "Data access error: " + e.getMessage());
        }

        // Generate an auth token for the new user
        String authToken = generateAuthToken();
        try {
            authDAO.insert(new Authtoken(authToken, username));
        } catch (DataAccessException e) {
            return new RegisterResponse(null, null, "Data access error: " + e.getMessage());
        }

        return new RegisterResponse(username, authToken, null);
    }

    private String generateAuthToken() {
        return UUID.randomUUID().toString();
    }
}
