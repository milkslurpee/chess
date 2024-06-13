package client;

import chess.ChessGame;
import org.junit.jupiter.api.*;
import facade.ServerFacade;
import requests.*;
import responses.*;
import server.Server;

import java.io.IOException;

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
    public void setup() throws IOException {
        // facade.clearServer(); //need to clear the database between each test

        //one user already logged in
        RegisterResponse userResponse = facade.register(new RegisterRequest("user", "pass", "email"));
        existingAuth = userResponse.getAuthToken();
    }

    @Test
    @DisplayName("Register Works")
    public void registerWorks() throws Exception {

        RegisterResponse regResp = facade.register(new RegisterRequest("user1", "pass", "email"));
        String auth1 = regResp.getAuthToken();
        assertTrue(auth1.length() > 10, "Auth token should be longer than 10 characters");
        assertEquals("user1", regResp.getUsername(), "Should return the same username");
        Assertions.assertNotEquals(existingAuth, auth1, "Auth tokens should be different");
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

}
