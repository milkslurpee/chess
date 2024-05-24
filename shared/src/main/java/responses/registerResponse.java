package responses;

/**
 * The registerResponse class represents a response for a user registration operation with a success status and a message.
 * It is a subclass of the baseResponse class.
 */
public class registerResponse extends baseResponse {
    private String username;
    private String email;
    /**
     * Constructs a new registerResponse with the provided success status and message.
     *
     * @param success A boolean flag indicating the success status of the response.
     * @param message A message associated with the response.
     */
    public registerResponse(boolean success, String message) {
        super(success, message);
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}