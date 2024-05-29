package ServiceTests;

import chess.ChessGame;
import org.junit.jupiter.api.*;
import dataaccess.*;
import handlers.*;
import services.*;
import models.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class ServiceUnitTests {

    private GameDAO gameDAO;
    private AuthDAO authDAO;
    private UserDAO userDAO;
    private Authtoken authData;
    private CreateGameService createGameService;


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
    void


}
