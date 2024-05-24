package responses;

/**
 * The baseResponse class represents a basic response object with success status and a message.
 */
public class baseResponse {
    /**
     * A boolean flag indicating the success status of the response.
     */
    private boolean success;

    /**
     * A message associated with the response.
     */
    private String message;

    /**
     * Constructs a new baseResponse with the provided success status and message.
     *
     * @param success A boolean flag indicating the success status of the response.
     * @param message A message associated with the response.
     */
    public baseResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}