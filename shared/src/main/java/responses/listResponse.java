package responses;

import models.GameModel;
import java.util.Map;

/**
 * The listResponse class represents a response for listing items with a success status and a message.
 * It is a subclass of the baseResponse class.
 */
public class listResponse extends baseResponse {
    private Map<Integer, GameModel> gameMap;
    /**
     * Constructs a new listResponse with the provided success status and message.
     *
     * @param success A boolean flag indicating the success status of the response.
     * @param message A message associated with the response.
     */
    public listResponse(boolean success, String message, Map<Integer, GameModel> map) {
        super(success, message);
        this.gameMap = map;
    }
    public Map<Integer, GameModel> getGameMap() {
        return gameMap;
    }
    public void setGameMap(Map<Integer, GameModel> gameMap) {
        this.gameMap = gameMap;
    }
}