package responses;

import models.GameModel;

import java.util.List;
import java.util.Map;

/**
 * The listResponse class represents a response for listing items with a success status and a message.
 * It is a subclass of the baseResponse class.
 */
public class listResponse extends baseResponse {
    private List<GameModel> gamelist;
    /**
     * Constructs a new listResponse with the provided success status and message.
     *
     * @param message A message associated with the response.
     */
    public listResponse(String message, List<GameModel> gamelist) {
        super(message);
        this.gamelist = gamelist;
    }
    public List<GameModel> getGameMap() {
        return gamelist;
    }
}