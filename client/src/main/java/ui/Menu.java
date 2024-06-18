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

  // Track the current user details
  private String loggedInUsername;
  private String currentAuthToken;

  private HashMap<Integer, GameModel> gamesMap;

  public Menu(){
    facade = new ServerFacade("http://localhost:8080");
  }

  public void runMenus() {
    Scanner scanner = new Scanner(System.in);
    while(!quit){
      if(!loggedIn){
        firstMenu(scanner);
      } else {
        secondMenu(scanner);
        if(inPlayMode){
          DrawBoard board = new DrawBoard();
          inPlayMode = false;
        }
      }
    }
    scanner.close();
  }

  private void firstMenu(Scanner scanner) {
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
      System.out.println("Register Successful! Logging in as: " + loggedInUsername);
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
      System.out.println("Login Successful! Welcome " + loggedInUsername);
    }
  }

  private void secondMenu(Scanner scanner) {
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
    } else if (input.equals("3")) {
      joinGame(scanner);
    } else if (input.equals("4")) {
      observeGame(scanner);
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
    System.out.print("What would you like to name your chess game? ");
    String newGameName = scanner.nextLine();
    CreateGameResponse resp = facade.createGame(new CreateGameRequest(newGameName), currentAuthToken);
    if(resp == null){
      System.out.println("Failed to create game.");
    } else {
      System.out.println("Game created successfully: " + newGameName);
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
          counter++;
        }
      }
    }
  }

  private void joinGame(Scanner scanner){
    listCurrentGames();
    if(gamesMap == null){
      return;
    }
    int gameToJoin;
    while (true) {
      System.out.print("To join a game, type in its ordinal number: ");
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

    System.out.print("What color would you like to join this game as? Type BLACK or WHITE: ");
    String playerColor = scanner.nextLine().trim();

    System.out.println("Attempting to join game " + gameToJoin + ": " + gamesMap.get(gameToJoin).getGameName() + " as " + playerColor);
    System.out.println("...");

    boolean joinedGame = facade.joinGame(new JoinGameRequest(playerColor, gamesMap.get(gameToJoin).getGameID()), currentAuthToken);
    if(joinedGame){
      System.out.println("Successfully joined " + gamesMap.get(gameToJoin).getGameName() + " as " + playerColor);
      inPlayMode = true;
    } else {
      System.out.println("Unable to join the game, please try again.");
    }
  }

  private void observeGame(Scanner scanner){
    listCurrentGames();
    if(gamesMap == null){
      return;
    }
    int gameToJoin;
    while (true) {
      System.out.print("To observe a game, type in its ordinal number: ");
      String input = scanner.nextLine();

      try {
        gameToJoin = Integer.parseInt(input);
        if (gamesMap.containsKey(gameToJoin)) {
          inPlayMode = true;
          break;
        } else {
          System.out.println("Invalid game number. Please try again.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a valid number.");
      }
    }
    // Add observe functionality in phase 6
  }

  private void displayHelpTextMenuTwo(){
    System.out.println("Hello, " + loggedInUsername + " sorry for the confusion. Hopefully your question will be answered below:");
    System.out.println("When creating a chess game, you must provide a name for the game.");
    System.out.println("To see all current games, choose list game.");
    System.out.println("To join or play a game, you must specify which game and which color you'd like to play as.");
    System.out.println("To observe a game, type in the game number.");
    System.out.println("To return to the previous menu, you must logout.");
  }

  private void displayHelpTextMenuOne(){
    System.out.println("Sorry for the confusion. Hopefully your question will be answered below:");
    System.out.println("To access the chess game, you must register or login.");
    System.out.println("After logging in, you can create, list, join, or observe games.");
    System.out.println("Use the help option to get more detailed instructions.");
  }
}


