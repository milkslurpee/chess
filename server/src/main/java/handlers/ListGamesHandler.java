package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.db.*;
import responses.ListResponse;
import services.ListGameService;
import spark.Request;
import spark.Response;

public class ListGamesHandler {
    private final ListGameService listGameService;
    private final Gson gson;
    private final dbAuthDAO authDAO; // Add AuthDAO to check token validity

    public ListGamesHandler(ListGameService listGameService, Gson gson, dbAuthDAO authDAO) {
        this.listGameService = listGameService;
        this.gson = gson;
        this.authDAO = authDAO; // Initialize AuthDAO
    }

    public String handleList(Request request, Response response) throws DataAccessException {
        response.type("application/json");
        String authToken = request.headers("authorization");
        // Check if the authorization header is present
        authDAO.read(authToken);
        ListResponse gameListResponse = listGameService.list();
        try {
            // Call the list service

            if (gameListResponse.getMessage() == null) {
                response.status(200);
                //System.out.println(gameListResponse.);
                return gson.toJson(gameListResponse);
            } else if (gameListResponse.getMessage().equals("unauthorized")) {
                response.status(403);
                return gson.toJson(gameListResponse);
            }
        } catch (Exception e) {
            response.status(500);
            return gson.toJson(new ListResponse("Error: description", null));
        }

            response.status(200);
            return gson.toJson(gameListResponse);

    }
}