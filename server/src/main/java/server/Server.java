package server;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.db.*;
import spark.Spark;
import handlers.*;
import services.*;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.SQLException;

public class Server {

    dbAuthDAO authDAO;
    dbUserDAO userDAO;
    dbGameDAO gameDAO;


    public int run(int desiredPort) {

        initializeDatabase();

        Spark.port(desiredPort);

        Gson gson = new Gson();

        Spark.staticFiles.location("web");

        Spark.delete("/db", (request, response) -> new ClearDbHandler(new ClearService(authDAO, userDAO, gameDAO), gson).handleClear(request, response));
        Spark.post("/user", (request, response) -> new RegisterHandler(new RegisterService(authDAO, userDAO, gameDAO), gson).handleRegister(request, response));
        Spark.post("/session", (request, response) -> new LoginHandler(new LoginService(authDAO, userDAO, gameDAO), gson).handleLogin(request, response));
        Spark.delete("/session", (request, response) -> new LogoutHandler(new LogoutService(authDAO, userDAO), gson).handleLogout(request, response));
        Spark.get("/game", (request, response) -> new ListGamesHandler(new ListGameService(gameDAO), gson, authDAO).handleList(request, response));
        Spark.post("/game", (request, response) -> new CreateGameHandler(new CreateGameService(authDAO, userDAO, gameDAO), gson).handleCreate(request, response));
        Spark.put("/game", (request, response) -> new JoinGameHandler(userDAO, authDAO, gameDAO).handleJoin(request, response));

        // Register your endpoints and handle exceptions here.

        Spark.awaitInitialization();
        return Spark.port();
    }

    private void initializeDatabase() {
        try {
            // Check if the database exists
            if (!DatabaseManager.databaseExists()) {
                // If the database doesn't exist, create it
                DatabaseManager.createDatabase();
            }

            // Connect to the database
            try (Connection conn = DatabaseManager.getConnection()) {
                // DAO

                // Check if tables exist
                if (!DatabaseManager.tableExists(conn, "game")) {
                    // If the game table doesn't exist, create it
                    this.gameDAO = new dbGameDAO();
                    this.gameDAO.configureDatabase(this.gameDAO.createStatements);
                } else {
                    // If the game table exists, use it
                    this.gameDAO = new dbGameDAO();
                }

                if (!DatabaseManager.tableExists(conn, "user")) {
                    // If the user table doesn't exist, create it
                    this.userDAO = new dbUserDAO();
                    this.userDAO.configureDatabase(this.userDAO.createStatements);
                } else {
                    // If the user table exists, use it
                    this.userDAO = new dbUserDAO();
                }

                if (!DatabaseManager.tableExists(conn, "auth")) {
                    // If the auth table doesn't exist, create it
                    this.authDAO = new dbAuthDAO();
                    this.authDAO.configureDatabase(this.authDAO.createStatements);
                } else {
                    // If the auth table exists, use it
                    this.authDAO = new dbAuthDAO();
                }
            }
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
    }


    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
