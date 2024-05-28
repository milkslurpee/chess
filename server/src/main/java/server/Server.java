package server;
import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import spark.Spark;
import handlers.*;
import services.*;
import com.google.gson.Gson;

import spark.*;

public class Server {
    AuthDAO authDAO = new AuthDAO();
    UserDAO userDAO = new UserDAO();
    GameDAO gameDAO = new GameDAO();

    public int run(int desiredPort) {

        Spark.port(desiredPort);

        Gson gson = new Gson();

        Spark.staticFiles.location("web");

        Spark.delete("/db", new ClearDbHandler(new ClearService(authDAO, userDAO, gameDAO), gson)::handleClear);
        Spark.post("/user", new RegisterHandler(new RegisterService(authDAO, userDAO, gameDAO), gson)::handleRegister);
        Spark.post("/session", new LoginHandler(new LoginService(authDAO, userDAO, gameDAO), gson)::handleLogin);
        Spark.delete("/session", (request, response) -> new LogoutHandler(new LogoutService(authDAO, userDAO), gson).handleLogout(request, response));
        Spark.get("/game", new ListGamesHandler(new ListGameService(gameDAO), gson)::handleList);
        Spark.post("/game", new CreateGameHandler(new CreateGameService(authDAO, userDAO, gameDAO), gson)::handleCreate);
        Spark.put("/game", new JoinGameHandler(new JoinGameService(authDAO, userDAO, gameDAO), gson)::handleJoin);

        // Register your endpoints and handle exceptions here.

        Spark.awaitInitialization();
        return Spark.port();
    }


    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
