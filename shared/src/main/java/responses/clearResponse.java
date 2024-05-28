package responses;

/**
 * The clearResponse class represents a response for clearing an operation with a success status and a message.
 * It is a subclass of the baseResponse class.
 */
public class clearResponse extends baseResponse {
    /**
     * Constructs a new clearResponse with the provided success status and message.
     *
     * @param message A message associated with the response.
     */
    public clearResponse(String message) {
        super(message);
    }
}