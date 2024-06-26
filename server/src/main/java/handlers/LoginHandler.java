package handlers;

import com.google.gson.Gson;
import requests.LoginRequest;
import responses.LoginResponse;
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

    public Object handleLogin(Request request, Response response) {
        response.type("application/json");

        // Deserialize the JSON request to a LoginRequest object
        LoginRequest loginRequest = gson.fromJson(request.body(), LoginRequest.class);

        //System.out.println("Received login request: " + loginRequest.getUsername() + " / " + loginRequest.getPassword());

        if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            response.status(400);
            return gson.toJson(new LoginResponse(null,null,"Error: no username or password"));
        }

        // Perform the login service
        LoginResponse loginResponse = loginService.login(loginRequest);

        // Check login response and set status accordingly
        if (loginResponse.getMessage() == null) {
            //System.out.println("Login successful for user: " + loginResponse.getUsername());
            response.status(200);
            gson.toJson(loginResponse);
            //System.out.println("Returning JSON response: " + gson.toJson(loginResponse));
            return gson.toJson(loginResponse);

        } else {
            //System.out.println("Login failed: " + loginResponse.getMessage());
            response.status(401);
            return gson.toJson(new LoginResponse(null,null,"Error: unauthorized"));
        }
    }
}
