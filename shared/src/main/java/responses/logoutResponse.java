package responses;

/**
 * The logoutResponse class represents a response for a logout operation with a success status and a message.
 * It is a subclass of the baseResponse class.
 */
public class logoutResponse extends baseResponse {
    /**
     * Constructs a new logoutResponse with the provided success status and message.
     *
     * @param success A boolean flag indicating the success status of the response.
     * @param message A message associated with the response.
     */
    public logoutResponse(boolean success, String message) {
        super(success, message);
    }
}