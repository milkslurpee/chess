package responses;

import models.GameModel;

import java.util.List;

/**
 * The listResponse class represents a response for listing items with a success status and a message.
 * It is a subclass of the baseResponse class.
 */
public class ListResponse extends BaseResponse {
    private List<GameModel> games;
    /**
     * Constructs a new listResponse with the provided success status and message.
     *
     * @param message A message associated with the response.
     */
    public ListResponse(String message, List<GameModel> gamelist) {
        super(message);
        this.games = gamelist;
    }
    public List<GameModel> getGameMap() {
        return games;
    }
}