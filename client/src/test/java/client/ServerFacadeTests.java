package client;

import facade.ServerFacade;
import models.User;
import requests.*;
import responses.*;
import org.junit.jupiter.api.*;
import server.Server;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;
    String existingAuth;


    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + port);
    }

    @BeforeEach
    public void setup() {
        facade.clearServer(); //need to clear the database between each test

        //one user already logged in
        RegisterResponse userResponse = facade.registerUser(new RegisterRequest("user", "pass", "email"));
        existingAuth = userResponse.getAuthToken();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
        facade.clearServer();
    }


    @Test
    void register() throws Exception {
        var authData = facade.registerUser(new RegisterRequest("player1", "password", "p1@email.com"));
        assertTrue(authData.getAuthToken().length() > 10);
    }

    @Test
    @DisplayName("Register Fails")
    public void registerFails() throws Exception {
        Assertions.assertNull(facade.registerUser(new RegisterRequest("", "pass", "email")),
                "Empty field, should return null (because it threw an bad request exception)");
        Assertions.assertNull(facade.registerUser(new RegisterRequest("user", "pass", "email")),
                "Username already taken");
    }



    @Test
    @DisplayName("Login Works")
    public void loginWorks() throws Exception {

        // test that it makes a new auth token each login
        LoginResponse resp = facade.loginUser(new LoginRequest("user", "pass"));
        assertTrue(!resp.getAuthToken().equals(existingAuth), "Should make a new auth each time");

    }

    @Test
    @DisplayName("Login Fails")
    public void loginFails() throws Exception {

        Assertions.assertNull(facade.loginUser(new LoginRequest("user", "WRONG_PASSWORD")),
                "Logging in with the wrong password, should return null (because it threw an unauthorized exception)");
    }



    @Test
    @DisplayName("Logout Works")
    public void logoutWorks() throws Exception {

        Assertions.assertTrue(facade.logoutUser(existingAuth),
                "A valid logout shouldn't throw an exception (return null)");

        Assertions.assertNull(facade.createGame(new CreateGameRequest("new game"), existingAuth),
                "If there was a successful logout, this auth token should have been removed.");
    }

    @Test
    @DisplayName("Logout Fails")
    public void logoutFails() throws Exception {

        Assertions.assertFalse(facade.logoutUser("fake auth token"),
                "Fake auth token, should throw an exception (return false)");
    }



    @Test
    @DisplayName("Create Game Works")
    public void createGameWorks() throws Exception {

        facade.createGame(new CreateGameRequest("New Game"), existingAuth);
        Assertions.assertNotNull(facade.createGame(new CreateGameRequest("New Game"), existingAuth),
                "Should be able to create another game with the same name.");
    }

    @Test
    @DisplayName("Create Game Fails")
    public void createGameFails() throws Exception {
        Assertions.assertNull(facade.createGame(new CreateGameRequest(""), existingAuth),
                "Empty field, should have thrown a bad request exception (returned null)");

        Assertions.assertNull(facade.createGame(new CreateGameRequest("chess"), "beans"),
                "Fake auth token, should be unauthorized and return null");
    }



    @Test
    @DisplayName("List Games Works")
    public void listGamesWorks() throws Exception {
        facade.createGame(new CreateGameRequest("first"), existingAuth);
        facade.createGame(new CreateGameRequest("second"), existingAuth);
        facade.createGame(new CreateGameRequest("third"), existingAuth);

        ListResponse resp = facade.listGames(existingAuth);
        Assertions.assertNotNull(resp, "There are games, the list shouldn't be null");
        Assertions.assertEquals(3, resp.getGameMap().size(), "Should have 3 games in there");
    }

    @Test
    @DisplayName("List Games Fails")
    public void listGamesFails() throws Exception {

        Assertions.assertTrue(facade.listGames(existingAuth).getGameMap().isEmpty(),
                "Game list is empty, .isEmpty() should be true");

        facade.createGame(new CreateGameRequest("first"), existingAuth);
        Assertions.assertNull(facade.listGames("fake_auth"),
                "Fake auth token, should be unauthorized to list games");
    }



    @Test
    @DisplayName("Join Game Works")
    public void joinGameWorks() throws Exception {

        CreateGameResponse resp = facade.createGame(new CreateGameRequest("New Game"), existingAuth);
        facade.listGames(existingAuth);
        assertTrue(facade.joinGame(new JoinGameRequest("WHITE", resp.getGameID()), existingAuth),
                "Should be able to join this new game as white");
    }

    @Test
    @DisplayName("Join Game Fails")
    public void joinGameFails() throws Exception {

        CreateGameResponse resp = facade.createGame(new CreateGameRequest("New Game"), existingAuth);
        facade.listGames(existingAuth);
        facade.joinGame(new JoinGameRequest("WHITE", resp.getGameID()), existingAuth);
        Assertions.assertFalse(facade.joinGame(new JoinGameRequest("WHITE", resp.getGameID()), existingAuth),
                "White is already taken, shouldn't be able to join");

        Assertions.assertFalse(facade.joinGame(new JoinGameRequest("yellow", resp.getGameID()), existingAuth),
                "Misspelled color name, should throw exception");

        Assertions.assertFalse(facade.joinGame(new JoinGameRequest("BLACK", resp.getGameID()), "potatosalad"),
                "Fake auth token, should throw exception because unauthorized");
    }


}
