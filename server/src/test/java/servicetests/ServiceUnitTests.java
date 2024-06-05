package servicetests;

import chess.ChessGame;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryGameDAO;
import dataaccess.memory.MemoryUserDAO;
import org.junit.jupiter.api.*;
import dataaccess.*;
import requests.CreateGameRequest;
import responses.CreateGameResponse;
import services.*;
import models.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class ServiceUnitTests {

    private MemoryGameDAO gameDAO;
    private MemoryAuthDAO authDAO;
    private MemoryUserDAO userDAO;


    @BeforeEach
    public void setup() throws DataAccessException {
        gameDAO = new MemoryGameDAO();
        authDAO = new MemoryAuthDAO();
        userDAO = new MemoryUserDAO();
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



}
