package responses;

public class CreateGameResponse {
    private int gameID;
    private String message;

    public CreateGameResponse(Integer gameID) {
        this.gameID = gameID;
        this.message = null;
    }

    public CreateGameResponse(String message) {
        this.message = message;
        this.gameID = -1;
    }

    public int getGameID() {
        return gameID;
    }

    public String getMessage() {
        return message;
    }
}
