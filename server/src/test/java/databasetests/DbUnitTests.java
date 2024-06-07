package databasetests;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.db.dbAuthDAO;
import dataaccess.db.dbGameDAO;
import dataaccess.db.dbUserDAO;
import models.Authtoken;
import models.GameModel;
import models.User;
import org.junit.jupiter.api.AfterEach;
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
        users.insert(new User("testUser", "password", "taco@tuesdays.com"));
        assertEquals(1, users.size());
        users.clear();
        assertEquals(0, users.size());
    }

    @Test
    void testCreateUserPos() throws DataAccessException {
        assertEquals(0, users.size());
        users.insert(new User("testUser", "password", "taco@tuesdays.com"));
        assertEquals(1, users.size());
    }

    @Test
    void testCreateUserNeg() throws DataAccessException {
        assertEquals(0, users.size());
        users.insert(new User("testUser", "password", "taco@tuesdays.com"));
        assertEquals(1, users.size());
        assertThrows(DataAccessException.class, () -> {
            users.insert(new User("testUser", "newpassword", "New@email.com"));

        });
    }

    @Test
    void testGetUserPos() throws DataAccessException {
        assertEquals(0, users.size());
        users.insert(new User("testUser", "password", "taco@tuesdays.com"));



        assertEquals("testUser", users.read("testUser").getUsername());
        assertEquals("password", users.read("testUser").getPassword());
    }

    @Test
    void testGetUserNullandNeg() throws DataAccessException {
        assertEquals(0, users.size());
        users.insert(new User("testUser", "password", "taco@tuesdays.com"));

        assertNull(users.read(null));
    }


    @Test
    void testCreateAuthPos() throws DataAccessException {

        assertEquals(0, users.size());
        assertEquals(0, tokens.size());
        users.insert(new User("testUser", "password", "taco@tuesdays.com"));
        tokens.insert(new Authtoken(generateAuthToken(), "testUser"));
        assertEquals(1, users.size());
        assertEquals(1, tokens.size());
    }

    private String generateAuthToken() {
        return UUID.randomUUID().toString();
    }

    @Test
    void testCreateAuthNeg() throws DataAccessException {
        users.insert(new User("testUser", "password", "taco@tuesdays.com"));
        var thisToken = generateAuthToken();
        tokens.insert(new Authtoken(thisToken, "testUser"));
        tokens.insert(new Authtoken(generateAuthToken(), "testUser"));
        assertEquals(2, tokens.size());
    }

    @Test
    void testGetAuthPos() throws DataAccessException {
        users.insert(new User("testUser", "password", "taco@tuesdays.com"));
        var thisToken = generateAuthToken();
        var thatToken = generateAuthToken();
        tokens.insert(new Authtoken(thisToken, "testUser"));
        tokens.insert(new Authtoken(thatToken, "testUser"));
        assertEquals("testUser", tokens.read(thisToken).getUserName());
        assertEquals("testUser", tokens.read(thatToken).getUserName());

    }

    @Test
    void testGetAuthNeg() throws DataAccessException {
        users.insert(new User("testUser", "password", "taco@tuesdays.com"));
        var thisToken = generateAuthToken();
        tokens.insert(new Authtoken(thisToken, "testUser"));

        assertThrows(DataAccessException.class, () -> {
            tokens.read(generateAuthToken());
        });

    }

    @Test
    void testDeleteAuthPos() throws DataAccessException {
        users.insert(new User("testUser", "password", "taco@tuesdays.com"));
        var thisToken = generateAuthToken();
        tokens.insert(new Authtoken(thisToken, "testUser"));

        assertEquals(1, users.size());
        assertEquals(1, tokens.size());

        tokens.delete(thisToken);

        assertEquals(0, tokens.size());
    }

    @Test
    void testDeleteAuthNeg() throws DataAccessException {
        users.insert(new User("testUser", "password", "taco@tuesdays.com"));
        users.insert(new User("testUser2", "password", "bacon@tuesdays.com"));

        var thisToken = generateAuthToken();
        var thatToken = generateAuthToken();
        tokens.insert(new Authtoken(thisToken, "testUser"));
        tokens.insert(new Authtoken(thatToken, "testUser2"));

        assertEquals(2, tokens.size());

        tokens.delete(thatToken);

        assertEquals(1, tokens.size());
        assertEquals("testUser", tokens.read(thisToken).getUserName());
    }

    @Test
    void testClearUsers() throws DataAccessException {
        assertEquals(0, tokens.size());

        users.insert(new User("testUser", "password", "taco@tuesdays.com"));
        var thisToken = generateAuthToken();
        tokens.insert(new Authtoken(thisToken, "testUser"));

        assertEquals(1, tokens.size());
        tokens.clear();
        assertEquals(0, tokens.size());

    }

    @Test
    void testClearGames() throws DataAccessException {

        assertEquals(0, games.size());
        games.insert(new GameModel(7, null, null, "nameOfGame", new ChessGame()));
        assertEquals(1, games.size());
        games.clear();
        assertEquals(0, games.size());

    }

    @Test
    void testCreateGamePos() throws DataAccessException {

        assertEquals(0, users.size());
        assertEquals(0, games.size());
        users.insert(new User("testUser", "password", "taco@tuesdays.com"));
        games.insert(new GameModel(7, null, null, "nameOfGame", new ChessGame()));
        assertEquals(1, users.size());
        assertEquals(1, games.size());
    }

    @Test
    void testCreateGameNeg() throws DataAccessException {
        users.insert(new User("testUser", "password", "taco@tuesdays.com"));
        games.insert(new GameModel(7, null, null, "nameOfGame", new ChessGame()));
        assertThrows(DataAccessException.class, () -> {
            games.insert(new GameModel(7, null, null, "nameOfGame", new ChessGame()));
        });
    }

    @Test
    void testGetGamePos() throws DataAccessException {

        users.insert(new User("testUser", "password", "taco@tuesdays.com"));
        games.insert(new GameModel(7, null, null, "nameOfGame", new ChessGame()));
        assertEquals("nameOfGame", games.read(7).getGameName());
    }

    @Test
    void testGetGameNeg() throws DataAccessException {

        users.insert(new User("testUser", "password", "taco@tuesdays.com"));
        games.insert(new GameModel(7, null, null, "nameOfGame", new ChessGame()));
        assertThrows(DataAccessException.class, () -> {
            games.insert(new GameModel(-1, null, null, "nameOfGame", new ChessGame()));
        });
    }

    @Test
    void testListGamePos() throws DataAccessException {
        games.insert(new GameModel(7, null, null, "nameOfGame", new ChessGame()));
        assertNotNull(games.getGameList());
    }

    @Test
    void testListGameNeg() throws DataAccessException {
        assertEquals(0, games.getGameList().size());
    }

    @Test
    void testUpdateGamePos() throws DataAccessException {
        users.insert(new User("whiteJoinTest", "password", "taco@tuesdays.com"));
        ChessGame game = new ChessGame();
        games.insert(new GameModel(7, null, null, "nameOfGame", game));

        GameModel gameUpdate = new GameModel(7, "whiteJoinTest", null, "nameOfGame", game);
        games.updateGame(7, gameUpdate);

        assertEquals("whiteJoinTest", games.read(7).getWhiteUsername());
    }

    @Test
    void testUpdateGameNeg() throws DataAccessException {
        users.insert(new User("whiteJoinTest", "password", "taco@tuesdays.com"));
        ChessGame game = new ChessGame();
        games.insert(new GameModel(7, null, null, "nameOfGame", game));

        GameModel gameUpdate = new GameModel(7, "whiteJoinTest", null, "nameOfGame", game);
        games.updateGame(5, gameUpdate);

        assertNotEquals("whiteJoinTest", games.read(7).getWhiteUsername());

    }


}

