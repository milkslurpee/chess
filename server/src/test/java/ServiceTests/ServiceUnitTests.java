package ServiceTests;

import chess.ChessGame;
import org.junit.jupiter.api.*;
import dataaccess.*;
import requests.CreateGameRequest;
import responses.CreateGameResponse;
import services.*;
import models.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class ServiceUnitTests {

    private GameDAO gameDAO;
    private AuthDAO authDAO;
    private UserDAO userDAO;


    @BeforeEach
    public void setup() throws DataAccessException {
        gameDAO = new GameDAO();
        authDAO = new AuthDAO();
        userDAO = new UserDAO();
    }

    @Test
    @Order(1)
    void clearTest() throws DataAccessException {
        ChessGame chessGame1 = new ChessGame();
        GameModel game1 = new GameModel(123, "beans", "rice", "partysauce", chessGame1);
        gameDAO.insert(game1);
        ChessGame chessGame2 = new ChessGame();
        GameModel game2 = new GameModel(234, "eggs", "bacon", "sneakylilguy", chessGame2);
        gameDAO.insert(game2);
        ClearService clearService = new ClearService(authDAO, userDAO, gameDAO);
        clearService.clear();
        assertEquals(0, gameDAO.getGameList().size());
    }

    @Test
    @Order(2)
    public void createGameSuccess() throws DataAccessException {
        // Mocking valid authentication
        Authtoken authtoken = new Authtoken("ValidAuthToken", "User1");
        authDAO.insert(authtoken);
        CreateGameService createGameService = new CreateGameService(authDAO, userDAO, gameDAO);

        // Creating a request for a new game
     //   GameModel game = new GameModel(123, "beans", "rice", "partysauce", chessGame);
        CreateGameRequest request = new CreateGameRequest("partysauce");
        // Calling the createGame method
        CreateGameResponse response = createGameService.createGame(authtoken.getAuthToken(), request);

        // Verifying the response
        assertNull(response.getMessage());
    }

    @Test
    @Order(3)
    public void createGameFailure() throws DataAccessException {

        Authtoken authtoken = new Authtoken("ValidAuthToken", "User1");
        CreateGameService createGameService = new CreateGameService(authDAO, userDAO, gameDAO);


        CreateGameRequest request = new CreateGameRequest("partysauce");
        // Calling the createGame method
        CreateGameResponse response = createGameService.createGame(authtoken.getAuthToken(), request);

        // Verifying the response
        assertNotNull(response.getMessage());
    }

//    @Test
//    @Order(4)
//    public void registerSuccess() throws DataAccessException {
//        RegisterRequest request = new RegisterRequest("newUser", "password", "email@example.com");
//        registerResponse response = RegisterService.register(request);
//
//        // Verifying the response
//        assertNull(response.getMessage());
//        assertNotNull(response.getUsername());
//        assertNotNull(response.getAuthToken());
//
//        // Verifying the user is in the userDAO
//        User user = userDAO.read("newUser");
//        assertNotNull(user);
//        assertEquals("newUser", user.getUsername());
//    }
//
//    @Test
//    @Order(5)
//    public void registerFailure_UsernameTaken() throws DataAccessException {
//        // First, register a user with the username "existingUser"
//        User existingUser = new User("existingUser", "password", "email@example.com");
//        userDAO.insert(existingUser);
//
//        // Attempt to register another user with the same username
//        RegisterRequest request = new RegisterRequest("existingUser", "password", "newemail@example.com");
//        registerResponse response = RegisterService.register(request);
//
//        // Verifying the response
//        assertNotNull(response.getMessage());
//        assertEquals("Username already taken", response.getMessage());
//        assertNull(response.getUsername());
//        assertNull(response.getAuthToken());
//    }

}
