package responses;

/**
 * The joinGameResponse class represents a response for joining a game operation with a success status and a message.
 * It is a subclass of the baseResponse class.
 */
public class joinGameResponse extends baseResponse {
    /**
     * Constructs a new joinGameResponse with the provided success status and message.
     *
     * @param success A boolean flag indicating the success status of the response.
     * @param message A message associated with the response.
     */
    public joinGameResponse(boolean success, String message) {
        super(success, message);
    }
}