package handlers;

import com.google.gson.Gson;
import dataaccess.db.*;
import requests.JoinGameRequest;
import responses.JoinGameResponse;
import services.JoinGameService;
import spark.Request;
import spark.Response;

public class JoinGameHandler {

    dbUserDAO usersDAO;
    dbAuthDAO authDAO;
    dbGameDAO gameDAO;
    JoinGameService playerJoin = new JoinGameService();
    Gson gson = new Gson();

    public JoinGameHandler(dbUserDAO users, dbAuthDAO authTokens, dbGameDAO games) {
        this.usersDAO = users;
        this.authDAO = authTokens;
        this.gameDAO = games;
    }

    public Object handleJoin(Request request, Response response) throws Exception {

        String token = request.headers("authorization");
        if (!authDAO.validToken(token)) {
            response.status(401);
            return gson.toJson(new JoinGameResponse("Error: unauthorized"));
        }
        try {
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
    }}