package responses;

/**
 * The createGameResponse class represents a response for creating a game operation with a success status and a message.
 * It is a subclass of the baseResponse class.
 */
public class createGameResponse extends baseResponse {
    /**
     * Constructs a new createGameResponse with the provided success status and message.
     *
     * @param success A boolean flag indicating the success status of the response.
     * @param message A message associated with the response.
     */
    public createGameResponse(boolean success, String message) {
        super(success, message);
    }
}