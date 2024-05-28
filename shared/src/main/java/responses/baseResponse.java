package responses;

/**
 * The baseResponse class represents a basic response object with success status and a message.
 */
public class baseResponse {

    /**
     * A message associated with the response.
     */
    private String message;

    /**
     * Constructs a new baseResponse with the provided success status and message.
     *
     * @param message A message associated with the response.
     */
    public baseResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}