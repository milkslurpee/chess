package client;

import chess.ChessGame;
import org.junit.jupiter.api.*;
import facade.ServerFacade;
import server.Server;


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

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

}
