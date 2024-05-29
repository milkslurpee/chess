package responses;

/**
 * The loginResponse class represents a response for a login operation with user information, success status, and a message.
 * It is a subclass of the baseResponse class.
 */
public class loginResponse extends BaseResponse {

    /**
     * The username associated with the login response.
     */
    private String username;

    /**
     * The authentication token associated with the login response.
     */
    private String authToken;

    /**
     * Constructs a new loginResponse with the provided username, authentication token, success status, and message.
     *
     * @param username The username associated with the login response.
     * @param authToken The authentication token associated with the login response.
     * @param message A message associated with the response.
     */
    public loginResponse(String username, String authToken, String message) {
        super(message);
        this.username = username;
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}