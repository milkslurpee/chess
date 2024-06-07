package services;
import dataaccess.db.dbAuthDAO;
import dataaccess.db.dbGameDAO;
import dataaccess.db.dbUserDAO;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.DataAccessException;
import dataaccess.memory.MemoryUserDAO;
import responses.LogoutResponse;

/**
 * The LogoutService class provides a service for user logout.
 */
public class LogoutService {
    dbAuthDAO authDAO;
    dbUserDAO userDAO;
    public LogoutService(dbAuthDAO authDAO, dbUserDAO userDAO) {
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }

    /**
     * Logs out the user, terminating their current session.
     *
     * @return A logoutResponse indicating the success of the logout operation.
     */
    public LogoutResponse logout(String authToken) {
        try {
            if (authDAO.read(authToken) == null) {
                // If the auth token doesn't exist, return unauthorized response
                return new LogoutResponse("Error: unauthorized");
            }
            authDAO.delete(authToken);
            return new LogoutResponse(null);
        } catch (DataAccessException e) {
            return new LogoutResponse("Error: Failed to logout");
        }
    }
}