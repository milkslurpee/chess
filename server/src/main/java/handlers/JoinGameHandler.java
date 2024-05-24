package handlers;

import com.google.gson.Gson;
import requests.JoinGameRequest;
import responses.joinGameResponse;
import services.JoinGameService;
import spark.Request;
import spark.Response;
public class JoinGameHandler {
    private final JoinGameService joinGameService;
    private final Gson gson;

    public JoinGameHandler(JoinGameService joinGameService, Gson gson) {
        this.joinGameService = joinGameService;
        this.gson = gson;
    }

    public Object handleJoin(Request request, Response response) {
        response.type("application/json");

        JoinGameRequest joinGameRequest = gson.fromJson(request.body(), JoinGameRequest.class);

        String authToken = request.headers("authorization");

        joinGameResponse joinGameResponse = joinGameService.joinGame(joinGameRequest, authToken);

        if (joinGameResponse.isSuccess()) {
            response.status(200);
            return gson.toJson(joinGameResponse); // Success response
        } else {
            // Handle failure responses
            if (joinGameResponse.getMessage().equals("Error: Game does not exist")) {
                response.status(400);
            } else if (joinGameResponse.getMessage().equals("Error: Unauthorized user")) {
                response.status(401);
            } else if (joinGameResponse.getMessage().equals("Error: Game is full")) {
                response.status(403);
            } else {
                response.status(500);
            }
            return gson.toJson(joinGameResponse); // Return the failure response
        }
    }
}