package ui;

import facade.ServerFacade;
import requests.RegisterRequest;
import responses.RegisterResponse;

import java.io.IOException;
import java.util.Scanner;

public class ClientUi {
    private boolean quit = false;
    private boolean loggedIn = false;
    private boolean inPlayMode = false;
    private ServerFacade facade;

    public ClientUi() {facade = new ServerFacade("http://localhost:8080");}

    public void runMenus() throws IOException {

        Scanner scanner = new Scanner(System.in);
        // start a while loop
        while(!quit){
            // throw the first menu up, return what

            if(!loggedIn){ // IF NOT LOGGED IN, CALL THE FIRST MENU
                firstMenu(scanner);
                // this menu can set logged in to TRUE
            }

            //if logged in, will stay on second menu
            while(loggedIn){

                // call second menu HERE
                //secondMenu(scanner);

                // can enter gameplay mode from here, but don't write this code out yet...
                if(inPlayMode){
                    // this is where we will do stuff in phase 6
                    // for now, just print out the chess board
                    //DrawBoard board = new DrawBoard();
                    inPlayMode = false;
                }

            }

            // check that they didn't type quit, if so then set quit = true
        }
        scanner.close();
    }

    private void firstMenu(Scanner scanner){

        // print the 4 options, read in the response
        System.out.println();
        System.out.println("Type in the number corresponding to the desired option");
        System.out.println("1 - Register a new user");
        System.out.println("2 - Login an existing user");
        System.out.println("3 - Display help text");
        System.out.println("4 - Quit the program");

        String input = scanner.nextLine();
        switch (input) {
            case "1":
                try {
                    registerNewUser(scanner);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "2":
                //loginExistingUser(scanner);
                break;
            case "3":
                //displayHelpTextMenuOne();
                break;
            case "4":
                System.out.println("Thanks for playing, quitting the program now.");
                quit = true;
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

//    private void secondMenu(Scanner scanner){
//
//        // print the 4 options, read in the response
//        System.out.println();
//        System.out.println("Type in the number corresponding to the desired option");
//        System.out.println("1 - Create a new chess game");
//        System.out.println("2 - List all current games");
//        System.out.println("3 - Join a game");
//        System.out.println("4 - Observe a game");
//        System.out.println("5 - Logout as user: " + loggedInUsername);
//        System.out.println("6 - Display help text");
//
//
//        String input = scanner.nextLine();
//
//        switch (input) {
//            case "1":
//                createNewGame(scanner);
//                break;
//            case "2":
//                listCurrentGames();
//                hasListedGames = true;
//                break;
//            case "3":
//                if(!hasListedGames){
//                    System.out.println("You need to print the list of game before you can join or observe one.");
//                } else {
//                    if(joinGame(scanner)){
//                        inPlayMode = true;
//                    } //if it returns false, we do NOT enter gameplay mode
//                }
//                break;
//            case "4":
//                if(observeGame(scanner)){
//                    inPlayMode = true;
//                }
//                break;
//            case "5":
//                if(facade.logoutUser(new AuthRequest(currentAuthToken))){
//                    System.out.println("Logged out user: " + loggedInUsername);
//                    loggedIn = false;
//                } else {
//                    System.out.println("Unable to logout at this time.");
//                }
//                break;
//            case "6":
//                displayHelpTextMenuTwo();
//                break;
//            default:
//                System.out.println("Invalid option. Please try again.");
//        }
//
//    }

    private void registerNewUser(Scanner scanner) throws IOException {

        System.out.print("Enter a new username: ");
        String username = scanner.nextLine();

        System.out.print("Enter a password: ");
        String password = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        RegisterRequest req = new RegisterRequest(username, password, email);
        RegisterResponse res = facade.register(req);

        if(res == null){
            System.out.println("Wasn't able to register a new user, please try again.");
        } else {

//            loggedIn = true;
//            loggedInUsername = username;
//            currentAuthToken = res.authToken();
//            System.out.println("Created user successfully, now logging in as: " + loggedInUsername);
        }
    }

}
