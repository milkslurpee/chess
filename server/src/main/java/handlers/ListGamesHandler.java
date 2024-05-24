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
            return gson.toJson(new listResponse(false,"Error: unauthorized", null));
        }

        try {
            // Call the list service
            listResponse gameListResponse = listGameService.list();

            if (gameListResponse.isSuccess()) {
                response.status(200);
                return gson.toJson(gameListResponse);
            } else {
                response.status(500);
                return gson.toJson(new listResponse(false, "Error: description", null));
            }
        } catch (Exception e) {
            response.status(500);
            return gson.toJson(new listResponse(false, "Error: description", null));
        }
    }
}