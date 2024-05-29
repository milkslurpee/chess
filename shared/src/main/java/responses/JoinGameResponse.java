package responses;

/**
 * The joinGameResponse class represents a response for joining a game operation with a success status and a message.
 * It is a subclass of the baseResponse class.
 */
public class JoinGameResponse extends BaseResponse {
    /**
     * Constructs a new joinGameResponse with the provided success status and message.
     *
     * @param message A message associated with the response.
     */
    public JoinGameResponse(String message) {
        super(message);
    }
}