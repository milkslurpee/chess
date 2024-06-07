package services;

import dataaccess.db.dbAuthDAO;
import dataaccess.db.dbGameDAO;
import dataaccess.db.dbUserDAO;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.DataAccessException;
import dataaccess.memory.MemoryGameDAO;
import dataaccess.memory.MemoryUserDAO;
import models.Authtoken;
import models.User;
import org.mindrot.jbcrypt.BCrypt;
import requests.LoginRequest;
import responses.LoginResponse;

import java.util.UUID;

/**
 * The LoginService class provides a service for user login.
 */
public class LoginService {
    dbAuthDAO authDAO;
    dbUserDAO userDAO;
    dbGameDAO gameDAO;
    public LoginService(dbAuthDAO authDAO, dbUserDAO userDAO, dbGameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        this.userDAO = userDAO;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // Validate the user's credentials
        User user;
        try {
            user = userDAO.read(username);
          //  System.out.println("User found: " + user.getUsername());
        } catch (DataAccessException e) {
           // System.out.println("User doesn't exist");
            return new LoginResponse(null, null, "User doesn't exist");
        }

        if (user!= null && BCrypt.checkpw(password, user.getPassword())) {
            String authToken = generateAuthToken();

            try {
                authDAO.insert(new Authtoken(authToken, username));
            } catch (DataAccessException e) {
                throw new RuntimeException(e);
            }
           // System.out.println("Successful login of user " + user.getUsername());
            return new LoginResponse(user.getUsername(), authToken, null);
        } else {
            System.out.println("Invalid username or password");
            return new LoginResponse(null, null, "Invalid username or password");
        }
    }

    public String generateAuthToken() {
        return UUID.randomUUID().toString();
    }
}
