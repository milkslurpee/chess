package handlers;

import com.google.gson.Gson;
import responses.logoutResponse;
import services.LogoutService;
import spark.Request;
import spark.Response;

public class LogoutHandler {

    private final LogoutService logoutService;
    private final Gson gson;

    public LogoutHandler(LogoutService logoutService, Gson gson) {
        this.logoutService = logoutService;
        this.gson = gson;
    }

    public Object handleLogout(Request request, Response response) {

        try {
            String authToken = request.headers("authorization");
            System.out.println(authToken);
            logoutResponse logoutResponse = logoutService.logout(authToken);

            if (logoutResponse.getMessage() == null) {
                response.status(200);
                return gson.toJson(logoutResponse);
            } else {
                response.status(401);
                return gson.toJson(logoutResponse);
            }
        } catch (Exception e) {
            response.status(500);
            return gson.toJson(new logoutResponse("Error: description"));
        }
    }
}