import chess.*;
import ui.ClientUi;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("♕ 240 Chess Client");
        ClientUi client = new ClientUi();
        client.runMenus();
    }
}