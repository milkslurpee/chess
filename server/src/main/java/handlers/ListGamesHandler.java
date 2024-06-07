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
    private final DbAuthDAO authDAO;
    private final DbGameDAO gameDAO;// Add AuthDAO to check token validity

    public ListGamesHandler(ListGameService listGameService, Gson gson, DbAuthDAO authDAO, DbGameDAO gameDAO) {
        this.listGameService = listGameService;
        this.gson = gson;
        this.authDAO = authDAO; // Initialize AuthDAO
        this.gameDAO = gameDAO;
    }
//
//    public Object handleList(Request request, Response response) throws DataAccessException {
//
//        try {
//            String token = request.headers("authorization");
//
//            authDAO.read(token);
//            ListResponse newResp = listGameService.list();
//            response.status(200);
//            return gson.toJson(newResp);
//        }
//        catch (Exception e) {
//            if (!(e.getMessage().equals("unauthorized"))) {
//                response.status(500);
//            }
//            else {
//                response.status(401);
//            }
//            return gson.toJson(new ListResponse("Error: description", null));
//        }
//
//
//    }
//
//

    public String handleList(Request request, Response response) throws DataAccessException {
        response.type("application/json");
        String authToken = request.headers("authorization");
        // Check if the authorization header is present

        ListResponse gameListResponse = listGameService.list();
        try {
            // Call the list service
            authDAO.read(authToken);
            if (gameListResponse.getMessage() == null) {
                response.status(200);
                //System.out.println(gameListResponse.);
                return gson.toJson(gameListResponse);
            } else if (gameListResponse.getMessage().equals("unauthorized")) {
                response.status(403);
                return gson.toJson(gameListResponse);
            }
        } catch (Exception e) {

            if (e.getMessage().equals("unauthorized")) {
                response.status(401);
                return gson.toJson(new ListResponse("Error: description", null));
            } else {
                response.status(500);
                return gson.toJson(new ListResponse("Error: description", null));
            }
        }
        response.status(200);
        return gson.toJson(gameListResponse);
    }
}