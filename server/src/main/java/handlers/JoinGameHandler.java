package handlers;

import com.google.gson.Gson;
import dataaccess.db.*;
import requests.JoinGameRequest;
import responses.JoinGameResponse;
import services.JoinGameService;
import spark.Request;
import spark.Response;

public class JoinGameHandler {

    DbUserDAO usersDAO;
    DbAuthDAO authDAO;
    DbGameDAO gameDAO;
    JoinGameService playerJoin = new JoinGameService();
    Gson gson = new Gson();

    public JoinGameHandler(DbUserDAO users, DbAuthDAO authTokens, DbGameDAO games) {
        this.usersDAO = users;
        this.authDAO = authTokens;
        this.gameDAO = games;
    }

    public Object handleJoin(Request request, Response response) throws Exception {

        String token = request.headers("authorization");

        try {
            authDAO.read(token);
            JoinGameRequest newReq = gson.fromJson(request.body(), JoinGameRequest.class);
            playerJoin.join(gameDAO, authDAO, token, newReq);
            response.status(200);
            return "{}";
        }
        catch(Exception e) {
            if(e.getMessage().equals("bad request")) {
                response.status(400);
            }
            else if (e.getMessage().equals("unauthorized")) {
                response.status(401);
            }
            else if (e.getMessage().equals("already taken")) {
                response.status(403);
            }
            else {
                response.status(500);
            }
            return gson.toJson(new JoinGameResponse("Error: " + e.getMessage()));

        }
    }
}