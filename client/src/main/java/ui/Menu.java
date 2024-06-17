package ui;

import facade.ServerFacade;
import models.GameModel;
import requests.*;
import responses.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {

  private boolean quit = false;
  private boolean loggedIn = false;
  private boolean inPlayMode = false;

  private ServerFacade facade;

  // do i need to keep data members for things like current username?? to keep track of the current user???
  private String loggedInUsername;
  private String currentAuthToken;

  private boolean hasListedGames = false;
  private HashMap<Integer, GameModel> gamesMap;

  public Menu(){
    facade = new ServerFacade("http://localhost:8080");
  }


  // write a main method inside of this class??
  // or call this method from the main class?

  public void runMenus() {

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
        secondMenu(scanner);

        // can enter gameplay mode from here, but don't write this code out yet...
        if(inPlayMode){
          // this is where we will do stuff in phase 6
          // for now, just print out the chess board
          DrawBoard board = new DrawBoard();
          inPlayMode = false;
        }

      }

      // check that they didn't type quit, if so then set quit = true
    }
    scanner.close();
  }

  // method for 1st menu
  private void firstMenu(Scanner scanner){

    System.out.println("To choose an option, type in its ordinal number");
    System.out.println("1 - Register");
    System.out.println("2 - Login");
    System.out.println("3 - Help");
    System.out.println("4 - Quit");

    String input = scanner.nextLine();

    if (input.equals("1")) {
      registerNewUser(scanner);
    } else if (input.equals("2")) {
      loginExistingUser(scanner);
    } else if (input.equals("3")) {
      displayHelpTextMenuOne();
    } else if (input.equals("4")) {
      System.out.println("Thanks for playing, quitting the program now.");
      quit = true;
    } else {
      System.out.println("Invalid option. Please try again.");
    }

  }

  private void registerNewUser(Scanner scanner){

    System.out.print("Enter username: ");
    String username = scanner.nextLine();

    System.out.print("Enter password: ");
    String password = scanner.nextLine();

    System.out.print("Enter email: ");
    String email = scanner.nextLine();

    RegisterRequest request = new RegisterRequest(username, password, email);
    RegisterResponse response = facade.registerUser(request);

    if(response == null){
      System.out.println("Error, failed to register");
    } else {
      loggedIn = true;
      loggedInUsername = username;
      currentAuthToken = response.getAuthToken();
      System.out.println("Register Successfull! Logging in as: " + loggedInUsername);
    }
  }

  private void loginExistingUser(Scanner scanner){

    System.out.print("Enter username: ");
    String username = scanner.nextLine();

    System.out.print("Enter password: ");
    String password = scanner.nextLine();

    LoginResponse res = facade.loginUser(new LoginRequest(username, password));

    if(res == null){
      System.out.println("Error, failed to login");
    } else {
      loggedIn = true;
      loggedInUsername = username;
      currentAuthToken = res.getAuthToken();
      System.out.println("Login Successfull! Welcome " + loggedInUsername);
    }
  }

  private void secondMenu(Scanner scanner){

    // print the 4 options, read in the response
    System.out.println();
    System.out.println("To choose an option, type in its ordinal number");
    System.out.println("1 - Create game");
    System.out.println("2 - List games");
    System.out.println("3 - Join a game");
    System.out.println("4 - Observe a game");
    System.out.println("5 - Logout");
    System.out.println("6 - Help");


    String input = scanner.nextLine();

    if (input.equals("1")) {
      createNewGame(scanner);
    } else if (input.equals("2")) {
      listCurrentGames();
      hasListedGames = true;
    } else if (input.equals("3")) {
      if (!hasListedGames) {
        System.out.println("You need to print the list of game before you can join or observe one.");
      } else {
        if (joinGame(scanner)) {
          inPlayMode = true;
        }
      }
    } else if (input.equals("4")) {
      if (observeGame(scanner)) {
        inPlayMode = true;
      }
    } else if (input.equals("5")) {
      if (facade.logoutUser(currentAuthToken)) {
        System.out.println("Logged out user: " + loggedInUsername);
        loggedIn = false;
      } else {
        System.out.println("Unable to logout at this time.");
      }
    } else if (input.equals("6")) {
      displayHelpTextMenuTwo();
    } else {
      System.out.println("Invalid option. Please try again.");
    }


  }

  private void createNewGame(Scanner scanner){

    System.out.println("What would you like to name your chessgame? ");
    String newGameName = scanner.nextLine();
    CreateGameResponse resp = facade.createGame(new CreateGameRequest(newGameName), currentAuthToken);
    if(resp == null){
      //there was some error
      System.out.println("Failed to create game.");
    } else {
      System.out.println("Game created Successfully: " + newGameName);
    }
  }

  private void listCurrentGames(){

    ListResponse resp = facade.listGames(currentAuthToken);

    if(resp == null){
      System.out.println("Couldn't print the game at this time.");
    } else {
      if(resp.getGameMap() == null || resp.getGameMap().isEmpty()){
        System.out.println("No current games to show.");
      } else {
        int counter = 1;
        gamesMap = new HashMap<>();
        for (GameModel gameData : resp.getGameMap()) {
          System.out.println(counter + ". " + gameData.getGameName());
          System.out.println("White Player: " + gameData.getWhiteUsername());
          System.out.println("Black Player: " + gameData.getBlackUsername());
          System.out.println();
          gamesMap.put(counter, gameData);
          counter = counter + 1;
        }

      }
    }

  }

  private boolean joinGame(Scanner scanner){

    if(printGamesList()){

      int gameToJoin;
      while (true) {
        System.out.println("To join a game, type in its ordinal number: ");
        String input = scanner.nextLine();

        try {
          gameToJoin = Integer.parseInt(input);
          if (gamesMap.containsKey(gameToJoin)) {
            break;
          } else {
            System.out.println("Invalid game number. Please try again.");
          }
        } catch (NumberFormatException e) {
          System.out.println("Invalid input. Please enter a valid number.");
        }
      }


      System.out.println("What color would you like to join this game as?");
      System.out.println("Type BLACK or WHITE:");
      String playerColor = scanner.nextLine().trim();

      System.out.println("Attempting to join game " + gameToJoin + ": " + gamesMap.get(gameToJoin).getGameName() + " as " + playerColor);
      System.out.println("...");

      boolean joinedGame = facade.joinGame(new JoinGameRequest(playerColor, gamesMap.get(gameToJoin).getGameID()), currentAuthToken);
      if(joinedGame){
        System.out.println("Successfully joined " + gamesMap.get(gameToJoin).getGameName()+ " as " + playerColor);
        return true;
      } else {
        System.out.println("Unable to join the game, please try again.");
        return false;
      }
    }
    // if it returns false, there are NO games
    return false;
  }

  private boolean observeGame(Scanner scanner){

    if(printGamesList()){
      int gameToJoin;
      while (true) {
        System.out.println("To observe a game, type in its ordinal number: ");
        String input = scanner.nextLine();

        try {
          gameToJoin = Integer.parseInt(input);
          if (gamesMap.containsKey(gameToJoin)) {
            break;
          } else {
            System.out.println("Invalid game number. Please try again.");
          }
        } catch (NumberFormatException e) {
          System.out.println("Invalid input. Please enter a valid number.");
        }
      }
    } // else there are no games to observe

    // will add functionality in phase 6???

    return true;
  }


  private void displayHelpTextMenuTwo(){
    System.out.println("Hello, " + loggedInUsername + " sorry for the confusion. Hopefully your question will be answered below:");
    System.out.println("When creating a chess game, you must provide a name for the game.");
    System.out.println("To see all current games, choose list game.");
    System.out.println("To join or play a game, you must specify which game and which color you'd like to play as.");
    System.out.println("To observer a game, type in the game number.");
    System.out.println("To return to the previous menu, you must logout.");
  }

  private void displayHelpTextMenuOne(){
    System.out.println("Sorry for the confusion. Hopefully your question will be answered below:");
    System.out.println("To access the chess game, you must first either login or register.");
    System.out.println("To register an account, you must provide a username, password, and email.");
    System.out.println("To login, you must provide your username and password.");
    System.out.println("To end the program, select the quit option.");
  }

  private boolean printGamesList(){
    if(gamesMap == null){
      System.out.println("There are no games to join or observe.");
      return false;
    } else {
      System.out.println("Here is a list of the current games:");
      for (Map.Entry<Integer, GameModel> entry : gamesMap.entrySet()) {
        System.out.println(entry.getKey() + ". " + entry.getValue().getGameName());
      }
      return true;
    }
  }

  // method for 3rd menu (won't be fully fleshed out yet)

}
