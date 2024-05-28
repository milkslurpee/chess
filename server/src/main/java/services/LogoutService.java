package services;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import responses.logoutResponse;

/**
 * The LogoutService class provides a service for user logout.
 */
public class LogoutService {
    AuthDAO authDAO;
    UserDAO userDAO;

    public LogoutService(AuthDAO authDAO, UserDAO userDAO) {
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }

    /**
     * Logs out the user, terminating their current session.
     *
     * @return A logoutResponse indicating the success of the logout operation.
     */
    public logoutResponse logout(String authToken) {
        System.out.println(authToken);
        try {
            authDAO.delete(authToken);
            return new logoutResponse(null);
        } catch (DataAccessException e) {
            return new logoutResponse("Error: Failed to logout");
        }
    }
}