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
      return com.postRegister(urlString, req);
    } catch(Exception e){
      System.out.println(e.getMessage());
      return null;
    }
  }

  public LoginResponse loginUser(LoginRequest req){
    try{
      return com.postLogin(urlString, req);
    } catch(Exception e){
      System.out.println(e.getMessage());
      return null;
    }
  }

  public boolean logoutUser(String authToken){
    try{
      return com.deleteLogout(urlString, authToken);
    } catch(Exception e){
      System.out.println(e.getMessage());
      return false;
    }
  }

  public CreateGameResponse createGame(CreateGameRequest req, String authToken){
    try{
      return com.postCreateGame(urlString, req, authToken);
    } catch(Exception e){
      System.out.println(e.getMessage());
      return null;
    }
  }

  public ListResponse listGames(String authToken){
    try{
      return com.getListGames(urlString, authToken);
    } catch(Exception e){
      System.out.println(e.getMessage());
      return null;
    }
  }

  public boolean joinGame(JoinGameRequest req, String authToken){
    try{
      return com.putJoinGame(urlString, req, authToken);
    } catch(Exception e){
      System.out.println(e.getMessage());
      return false;
    }
  }

  public void clearServer(){
    try{
      com.deleteClear(urlString);
    } catch(Exception e){
      System.out.println(e.getMessage());
    }
  }

}
