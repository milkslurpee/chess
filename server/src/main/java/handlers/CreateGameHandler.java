package handlers;

import com.google.gson.Gson;
import requests.CreateGameRequest;
import responses.CreateGameResponse;
import services.CreateGameService;
import spark.Request;
import spark.Response;

public class CreateGameHandler {
    private final CreateGameService createGameService;
    private final Gson gson;

    public CreateGameHandler(CreateGameService createGameService, Gson gson) {
        this.createGameService = createGameService;
        this.gson = gson;
    }

    public Object handleCreate(Request request, Response response) {
        response.type("application/json");

        String authToken = request.headers("authorization");

        if (authToken == null || authToken.isEmpty()) {
            response.status(401);
            return gson.toJson(new CreateGameResponse("Error: unauthorized"));
        }

        CreateGameRequest createGameRequest = gson.fromJson(request.body(), CreateGameRequest.class);
        CreateGameResponse gameResponse = createGameService.createGame(authToken, createGameRequest);

        if (gameResponse.getMessage() == null) {
            response.status(200);
            return gson.toJson(gameResponse);
        } else if (gameResponse.getMessage().equals("Error: unauthorized")) {
            response.status(401);
        } else if (gameResponse.getMessage().equals("Error: bad request")) {
            response.status(400);
        } else {
            response.status(500);
        }

        return gson.toJson(gameResponse);
    }
}
