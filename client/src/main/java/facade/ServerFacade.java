package facade;


import requests.*;
import responses.*;


public class ServerFacade {

  private final String urlString;
  private ClientCommunicator com;

  public ServerFacade(String url){
    urlString = url;
    com = new ClientCommunicator();
  }

  public RegisterResponse registerUser(RegisterRequest req){
    try{
      return com.registerUser(urlString, req);
    } catch(Exception e){
      System.out.println(e.getMessage());
      return null;
    }
  }

  public LoginResponse loginUser(LoginRequest req){
    try{
      return com.loginUser(urlString, req);
    } catch(Exception e){
      System.out.println(e.getMessage());
      return null;
    }
  }

  public boolean logoutUser(String authToken){
    try{
      return com.logoutUser(urlString, authToken);
    } catch(Exception e){
      System.out.println(e.getMessage());
      return false;
    }
  }

  public CreateGameResponse createGame(CreateGameRequest req, String authToken){
    try{
      return com.createGame(urlString, req, authToken);
    } catch(Exception e){
      System.out.println(e.getMessage());
      return null;
    }
  }

  public ListResponse listGames(String authToken){
    try{
      return com.listGames(urlString, authToken);
    } catch(Exception e){
      System.out.println(e.getMessage());
      return null;
    }
  }

  public boolean joinGame(JoinGameRequest req, String authToken){
    try{
      return com.joinGame(urlString, req, authToken);
    } catch(Exception e){
      System.out.println(e.getMessage());
      return false;
    }
  }

  public void clearServer(){
    try{
      com.clearDatabase(urlString);
    } catch(Exception e){
      System.out.println(e.getMessage());
    }
  }

}
