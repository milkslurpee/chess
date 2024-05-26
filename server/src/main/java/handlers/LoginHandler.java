package handlers;

import com.google.gson.Gson;
import requests.JoinGameRequest;
import requests.LoginRequest;
import responses.loginResponse;
import services.LoginService;
import spark.Request;
import spark.Response;

public class LoginHandler {

    private final LoginService loginService;
    private final Gson gson;

    public LoginHandler(LoginService loginService, Gson gson) {
        this.loginService = loginService;
        this.gson = gson;
    }

    public String handleLogin(Request request, Response response) {
        response.type("application/json");


            // Deserialize the JSON request to a LoginRequest object
        LoginRequest loginRequest = gson.fromJson(request.body(), LoginRequest.class);


        if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            response.status(400);
            return gson.toJson(new loginResponse(null,null,false, "Error: no username or password"));
        }

            // Perform the login service
        loginResponse loginResponse = loginService.login(loginRequest);

        System.out.println(loginResponse.getUsername() + "\n" + loginResponse.getAuthToken() + "\n");
        System.out.println(loginRequest.getUsername() + "\n" + loginRequest.getPassword());



            // Check login response and set status accordingly
            if (loginResponse.isSuccess()) {
                response.status(200);
                return gson.toJson(loginResponse);
            } else {
                response.status(401);
                return gson.toJson(new loginResponse(null,null,false, "Error: beans unauthorized"));
            }

    }
}