package responses;

public class CreateGameResponse {
    private Integer gameID;
    private String message;

    public CreateGameResponse(Integer gameID) {
        this.gameID = gameID;
        this.message = null;
    }

    public CreateGameResponse(String message) {
        this.message = message;
        this.gameID = null;
    }

    public Integer getGameID() {
        return gameID;
    }

    public String getMessage() {
        return message;
    }
}
