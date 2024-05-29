package responses;

/**
 * The registerResponse class represents a response for a user registration operation with a success status and a message.
 * It is a subclass of the baseResponse class.
 */
public class RegisterResponse extends BaseResponse {
    private String username;
    private String authToken;

    /**
     * Constructs a new registerResponse with the provided success status and message.
     *
     * @param message A message associated with the response.
     */
    public RegisterResponse(String username, String authToken, String message) {
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
}