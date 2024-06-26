package server;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.db.*;
import spark.Spark;
import handlers.*;
import services.*;
import com.google.gson.Gson;

public class Server {

    DbAuthDAO authDAO = new DbAuthDAO();
    DbUserDAO userDAO = new DbUserDAO();
    DbGameDAO gameDAO = new DbGameDAO();


    public int run(int desiredPort) {

        try {
            DatabaseManager.createDatabase();
        }catch (DataAccessException e) {
            System.err.println("Failed to initialie database: " + e.getMessage());
        }

        Spark.port(desiredPort);

        Gson gson = new Gson();

        Spark.staticFiles.location("web");

        Spark.delete("/db", (request, response) -> new ClearDbHandler(new ClearService(authDAO, userDAO, gameDAO), gson).handleClear(request, response));
        Spark.post("/user", (request, response) -> new RegisterHandler(new RegisterService(authDAO, userDAO, gameDAO), gson).handleRegister(request, response));
        Spark.post("/session", (request, response) -> new LoginHandler(new LoginService(authDAO, userDAO, gameDAO), gson).handleLogin(request, response));
        Spark.delete("/session", (request, response) -> new LogoutHandler(new LogoutService(authDAO, userDAO), gson).handleLogout(request, response));
        Spark.get("/game", (request, response) -> new ListGamesHandler(new ListGameService(gameDAO), gson, authDAO, gameDAO).handleList(request, response));
        Spark.post("/game", (request, response) -> new CreateGameHandler(new CreateGameService(authDAO, userDAO, gameDAO), gson).handleCreate(request, response));
        Spark.put("/game", (request, response) -> new JoinGameHandler(userDAO, authDAO, gameDAO).handleJoin(request, response));

        // Register your endpoints and handle exceptions here.

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
