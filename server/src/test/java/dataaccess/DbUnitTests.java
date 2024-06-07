package dataaccess;

import chess.ChessGame;
import dataaccess.db.dbAuthDAO;
import dataaccess.db.dbGameDAO;
import dataaccess.db.dbUserDAO;
import models.Authtoken;
import models.GameModel;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DbUnitTests {

    dbUserDAO users = new dbUserDAO();
    dbAuthDAO tokens = new dbAuthDAO();
    dbGameDAO games = new dbGameDAO();


    @BeforeEach
    void setup() throws DataAccessException {
        users =  new dbUserDAO();
        tokens = new dbAuthDAO();
        tokens.clear();
        games.clear();
        users.clear();
    }

    @Test
    void testClearDB() throws DataAccessException {
        assertEquals(0, users.size());
        users.insert(new User("beans", "potatoes", "tamales@quesadilla.imhungry"));
        assertEquals(1, users.size());
        users.clear();
        assertEquals(0, users.size());
    }

    @Test
    void testCreateUserPos() throws DataAccessException {
        assertEquals(0, users.size());
        users.insert(new User("beans", "potatoes", "tamales@quesadilla.imhungry"));
        assertEquals(1, users.size());
    }

    @Test
    void testCreateUserNeg() throws DataAccessException {
        assertEquals(0, users.size());
        users.insert(new User("beans", "potatoes", "tamales@quesadilla.imhungry"));
        assertEquals(1, users.size());
        assertThrows(DataAccessException.class, () -> {
            users.insert(new User("beans", "newpassword", "New@email.com"));

        });
    }

    @Test
    void testGetUserPos() throws DataAccessException {
        assertEquals(0, users.size());
        users.insert(new User("beans", "potatoes", "tamales@quesadilla.imhungry"));



        assertEquals("beans", users.read("beans").getUsername());
        assertEquals("potatoes", users.read("beans").getPassword());
    }

    @Test
    void testGetUserNullandNeg() throws DataAccessException {
        assertEquals(0, users.size());
        users.insert(new User("beans", "potatoes", "tamales@quesadilla.imhungry"));

        assertNull(users.read(null));
    }


    @Test
    void testCreateAuthPos() throws DataAccessException {

        assertEquals(0, users.size());
        assertEquals(0, tokens.size());
        users.insert(new User("beans", "potatoes", "tamales@quesadilla.imhungry"));
        tokens.insert(new Authtoken(generateAuthToken(), "beans"));
        assertEquals(1, users.size());
        assertEquals(1, tokens.size());
    }

    private String generateAuthToken() {
        return UUID.randomUUID().toString();
    }

    @Test
    void testCreateAuthNeg() throws DataAccessException {
        users.insert(new User("beans", "potatoes", "tamales@quesadilla.imhungry"));
        var thisToken = generateAuthToken();
        tokens.insert(new Authtoken(thisToken, "beans"));
        tokens.insert(new Authtoken(generateAuthToken(), "beans"));
        assertEquals(2, tokens.size());
    }

    @Test
    void testGetAuthPos() throws DataAccessException {
        users.insert(new User("beans", "potatoes", "tamales@quesadilla.imhungry"));
        var thisToken = generateAuthToken();
        var thatToken = generateAuthToken();
        tokens.insert(new Authtoken(thisToken, "beans"));
        tokens.insert(new Authtoken(thatToken, "beans"));
        assertEquals("beans", tokens.read(thisToken).getUserName());
        assertEquals("beans", tokens.read(thatToken).getUserName());

    }

    @Test
    void testGetAuthNeg() throws DataAccessException {
        users.insert(new User("beans", "potatoes", "tamales@quesadilla.imhungry"));
        var thisToken = generateAuthToken();
        tokens.insert(new Authtoken(thisToken, "beans"));

        assertThrows(DataAccessException.class, () -> {
            tokens.read(generateAuthToken());
        });

    }

    @Test
    void testDeleteAuthPos() throws DataAccessException {
        users.insert(new User("beans", "potatoes", "tamales@quesadilla.imhungry"));
        var thisToken = generateAuthToken();
        tokens.insert(new Authtoken(thisToken, "beans"));

        assertEquals(1, users.size());
        assertEquals(1, tokens.size());

        tokens.delete(thisToken);

        assertEquals(0, tokens.size());
    }

    @Test
    void testDeleteAuthNeg() throws DataAccessException {
        users.insert(new User("beans", "potatoes", "tamales@quesadilla.imhungry"));
        users.insert(new User("beans2", "potatoes2", "tamales@quesadilla.imhungry2"));

        var thisToken = generateAuthToken();
        var thatToken = generateAuthToken();
        tokens.insert(new Authtoken(thisToken, "beans"));
        tokens.insert(new Authtoken(thatToken, "beans2"));

        assertEquals(2, tokens.size());

        tokens.delete(thatToken);

        assertEquals(1, tokens.size());
        assertEquals("beans", tokens.read(thisToken).getUserName());
    }

    @Test
    void testClearUsers() throws DataAccessException {
        assertEquals(0, tokens.size());

        users.insert(new User("beans", "potatoes", "tamales@quesadilla.imhungry"));
        var thisToken = generateAuthToken();
        tokens.insert(new Authtoken(thisToken, "beans"));

        assertEquals(1, tokens.size());
        tokens.clear();
        assertEquals(0, tokens.size());

    }

    @Test
    void testClearGames() throws DataAccessException {

        assertEquals(0, games.size());
        games.insert(new GameModel(7, null, null, "gameName", new ChessGame()));
        assertEquals(1, games.size());
        games.clear();
        assertEquals(0, games.size());

    }

    @Test
    void testCreateGamePos() throws DataAccessException {

        assertEquals(0, users.size());
        assertEquals(0, games.size());
        users.insert(new User("beans", "potatoes", "tamales@quesadilla.imhungry"));
        games.insert(new GameModel(7, null, null, "gameName", new ChessGame()));
        assertEquals(1, users.size());
        assertEquals(1, games.size());
    }

    @Test
    void testCreateGameNeg() throws DataAccessException {
        users.insert(new User("beans", "potatoes", "tamales@quesadilla.imhungry"));
        games.insert(new GameModel(7, null, null, "gameName", new ChessGame()));
        assertThrows(DataAccessException.class, () -> {
            games.insert(new GameModel(7, null, null, "gameName", new ChessGame()));
        });
    }

    @Test
    void testGetGamePos() throws DataAccessException {

        users.insert(new User("beans", "potatoes", "tamales@quesadilla.imhungry"));
        games.insert(new GameModel(7, null, null, "gameName", new ChessGame()));
        assertEquals("gameName", games.read(7).getGameName());
    }

    @Test
    void testGetGameNeg() throws DataAccessException {

        users.insert(new User("beans", "potatoes", "tamales@quesadilla.imhungry"));
        games.insert(new GameModel(7, null, null, "gameName", new ChessGame()));
        assertThrows(DataAccessException.class, () -> {
            games.insert(new GameModel(-1, null, null, "gameName", new ChessGame()));
        });
    }

    @Test
    void testListGamePos() throws DataAccessException {
        games.insert(new GameModel(7, null, null, "gameName", new ChessGame()));
        assertNotNull(games.getGameList());
    }

    @Test
    void testListGameNeg() throws DataAccessException {
        assertEquals(0, games.getGameList().size());
    }

    @Test
    void testUpdateGamePos() throws DataAccessException {
        users.insert(new User("beans", "potatoes", "tamales@quesadilla.imhungry"));
        ChessGame game = new ChessGame();
        games.insert(new GameModel(7, null, null, "gameName", game));

        GameModel gameUpdate = new GameModel(7, "beans", null, "gameName", game);
        games.updateGame(7, gameUpdate);

        assertEquals("beans", games.read(7).getWhiteUsername());
    }

    @Test
    void testUpdateGameNeg() throws DataAccessException {
        users.insert(new User("beans", "potatoes", "tamales@quesadilla.imhungry"));
        ChessGame game = new ChessGame();
        games.insert(new GameModel(7, null, null, "gameName", game));

        GameModel gameUpdate = new GameModel(7, "whiteJoinTest", null, "gameName", game);
        games.updateGame(5, gameUpdate);

        assertNotEquals("whiteJoinTest", games.read(7).getWhiteUsername());

    }


}

