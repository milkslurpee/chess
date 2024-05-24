package handlers;

import com.google.gson.Gson;
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

        try {
            // Deserialize the JSON request to a LoginRequest object
            LoginRequest loginRequest = (LoginRequest)gson.fromJson(String.valueOf(request), LoginRequest.class);

            // Perform the login service
            loginResponse loginResponse = loginService.login(loginRequest);

            // Check login response and set status accordingly
            if (loginResponse.isSuccess()) {
                response.status(200);
                return gson.toJson(loginResponse);
            } else {
                response.status(401);
                return gson.toJson(new loginResponse(null,null,false, "Error: unauthorized"));
            }
        } catch (Exception e) {
            response.status(500);
            return gson.toJson(new loginResponse(null,null,false, "Error: description"));
        }
    }
}