package handlers;

import com.google.gson.Gson;
import requests.LoginRequest;
import requests.RegisterRequest;
import responses.registerResponse;
import services.RegisterService;
import spark.Request;
import spark.Response;

public class RegisterHandler {

    private final RegisterService registerService;
    private final Gson gson;

    public RegisterHandler(RegisterService registerService, Gson gson) {
        this.registerService = registerService;
        this.gson = gson;
    }

    public String handleRegister(Request request, Response response) {
        response.type("application/json");

        try {
            // Deserialize the JSON request to a RegisterRequest object
            RegisterRequest registerRequest = gson.fromJson(request.body(), RegisterRequest.class);

            // Perform validation on the registerRequest
            if (registerRequest.getUsername() == null || registerRequest.getPassword() == null || registerRequest.getEmail() == null) {
                response.status(400);
                return gson.toJson(new registerResponse(null, null, false, "Error: bad request"));
            }

            // Perform the register service
            registerResponse registerResponse = registerService.register(registerRequest);

            // Check the register response and set status accordingly
            if (registerResponse.isSuccess()) {
                response.status(200);
                return gson.toJson(registerResponse);
            } else if (registerResponse.getMessage().equals("Username already taken")) {
                response.status(403);
                return gson.toJson(new registerResponse(null, null, false, "Error: already taken"));
            } else {
                response.status(500);
                return gson.toJson(new registerResponse(null, null, false, "Error: " + registerResponse.getMessage()));
            }
        } catch (Exception e) {
            response.status(500);
            return gson.toJson(new registerResponse(null, null, false, "Error: " + e.getMessage()));
        }
    }
}