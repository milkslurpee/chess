package handlers;

import com.google.gson.Gson;
import responses.listResponse;
import services.ListGameService;
import spark.Request;
import spark.Response;

public class ListGamesHandler {

    private final ListGameService listGameService;
    private final Gson gson;

    public ListGamesHandler(ListGameService listGameService, Gson gson) {
        this.listGameService = listGameService;
        this.gson = gson;
    }

    public String handleList(Request request, Response response) {
        response.type("application/json");

        // Check if the authorization header is present
        if (request.headers("authorization") == null) {
            response.status(401);
            return gson.toJson(new listResponse("Error: unauthorized", null));
        }
        listResponse gameListResponse = listGameService.list();
        try {
            // Call the list service

            if (gameListResponse.getMessage() == null) {
                response.status(200);
                return gson.toJson(gameListResponse);
            } else if (gameListResponse.getMessage().equals("unauthorized")) {
                response.status(403);
                return gson.toJson(gameListResponse);
            }
        } catch (Exception e) {
            response.status(500);
            return gson.toJson(new listResponse("Error: description", null));
        }

            response.status(200);
            return gson.toJson(gameListResponse);

    }
}