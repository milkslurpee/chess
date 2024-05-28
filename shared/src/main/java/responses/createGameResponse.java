package responses;

public class createGameResponse {
    private Integer gameID;
    private String message;

    public createGameResponse(Integer gameID) {
        this.gameID = gameID;
        this.message = null;
    }

    public createGameResponse(String message) {
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
