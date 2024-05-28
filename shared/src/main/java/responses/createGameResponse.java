package responses;

/**
 * The createGameResponse class represents a response for creating a game operation with a success status and a message.
 * It is a subclass of the baseResponse class.
 */
public class createGameResponse extends baseResponse {
    /**
     * Constructs a new createGameResponse with the provided success status and message.
     *
     * @param message A message associated with the response.
     */
    public createGameResponse(String message) {
        super(message);
    }
}