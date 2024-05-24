package handlers;

import com.google.gson.Gson;
import requests.CreateGameRequest;
import responses.createGameResponse;
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

        CreateGameRequest createGameRequest = gson.fromJson(request.body(), CreateGameRequest.class);

        String authToken = request.headers("authorization");

        createGameResponse gameResponse = createGameService.createGame(createGameRequest);

        if (gameResponse.isSuccess()) {
            response.status(200);
            return gson.toJson(new createGameResponse(true, "Game created Successfully"));
        } else {
            response.status(500);
            return gson.toJson(new createGameResponse(false, "Game failed to initialize"));
        }
    }
}