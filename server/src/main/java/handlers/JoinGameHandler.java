package handlers;

import dataaccess.*;
import requests.JoinGameRequest;
import responses.JoinGameResponse;
import services.JoinGameService;
import services.SerializeUtils;
import spark.Request;
import spark.Response;

public class JoinGameHandler {

    private UserDAO usersDAO;
    private AuthDAO authDAO;
    private GameDAO gameDAO;
    JoinGameService playerJoin = new JoinGameService();

    public JoinGameHandler(UserDAO users, AuthDAO authTokens, GameDAO games) {
        this.usersDAO = users;
        this.authDAO = authTokens;
        this.gameDAO = games;
    }

    public Object handleJoin(Request request, Response response) throws Exception {

        String token = request.headers("authorization");
        if (!authDAO.validToken(token)) {
            response.status(401);
            return SerializeUtils.toJson(new JoinGameResponse("Error: unauthorized"));
        }
        try {
            JoinGameRequest newReq = SerializeUtils.fromJson(request.body(), JoinGameRequest.class);
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
            return SerializeUtils.toJson(new JoinGameResponse("Error: " + e.getMessage()));

        }
    }}