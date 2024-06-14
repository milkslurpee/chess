package facade;


import responses.*;
import requests.*;


public class ServerFacade {

    private final String urlString;
    private ClientCommunicator com;

    public ServerFacade(String url){
        urlString = url;
        com = new ClientCommunicator(urlString);
    }

    public RegisterResponse registerUser(RegisterRequest req){
        try{
            return com.register(req);
        } catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
//
//    public LoginResponse loginUser(LoginRequest req){
//        try{
//            return com.postLogin(urlString, req);
//        } catch(Exception e){
//            System.out.println(e.getMessage());
//            return null;
//        }
//    }
//
//    public boolean logoutUser(AuthRequest req){
//        try{
//            return com.deleteLogout(urlString, req);
//        } catch(Exception e){
//            System.out.println(e.getMessage());
//            return false;
//        }
//    }
//
//    public CreateGameResponse createGame(CreateGameRequest req){
//        try{
//            return com.postCreateGame(urlString, req);
//        } catch(Exception e){
//            System.out.println(e.getMessage());
//            return null;
//        }
//    }
//
//    public ListGamesResponse listGames(AuthRequest req){
//        try{
//            return com.getListGames(urlString, req);
//        } catch(Exception e){
//            System.out.println(e.getMessage());
//            return null;
//        }
//    }
//
//    public boolean joinGame(JoinGameRequest req){
//        try{
//            return com.putJoinGame(urlString, req);
//        } catch(Exception e){
//            System.out.println(e.getMessage());
//            return false;
//        }
//    }
//
//    public void clearServer(){
//        try{
//            com.deleteClear(urlString);
//        } catch(Exception e){
//            System.out.println(e.getMessage());
//        }
//    }

}
