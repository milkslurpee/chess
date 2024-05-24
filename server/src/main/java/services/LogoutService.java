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
    GameDAO gameDAO;
    public LogoutService(AuthDAO authDAO, UserDAO userDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        this.userDAO = userDAO;
    }

    /**
     * Logs out the user, terminating their current session.
     *
     * @return A logoutResponse indicating the success of the logout operation.
     */
    public logoutResponse logout(String authToken) {
        AuthDAO authDAO = new AuthDAO();
        try {
            authDAO.delete(authToken);
            return new logoutResponse(true, "Logout successful");
        } catch (DataAccessException e) {
            return new logoutResponse(false, "Error: Failed to logout");
        }
    }
}