package facade;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.google.gson.JsonObject;
import java.io.*;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.google.gson.JsonParser;
import responses.*;
import requests.*;


public class ClientCommunicator {

    String urlString;


    public RegisterResponse register(RegisterRequest request) throws Exception{

        URL url = new URL(urlString + "/user");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(5000);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", request.getUsername());
        jsonObject.addProperty("password", request.getPassword());
        jsonObject.addProperty("email", request.getEmail());

        String requestBody = jsonObject.toString();

        // Set HTTP request headers, if necessary
        // connection.addRequestProperty("Accept", "text/html");

        connection.connect();

        try(OutputStream requestBodyString = connection.getOutputStream(); OutputStreamWriter output = new OutputStreamWriter(requestBodyString, StandardCharsets.UTF_8)) {
            // Write request body to OutputStream ...
            output.append(requestBody);
            output.flush();
        }

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            // Read response body from InputStream ...
            try (InputStream responseStream = connection.getInputStream();
                 InputStreamReader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
                 BufferedReader bufferedReader = new BufferedReader(reader)) {

                StringBuilder responseBody = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    responseBody.append(line);
                }

                // Parse the response
                JsonObject responseJson = JsonParser.parseString(responseBody.toString()).getAsJsonObject();
                String message = responseJson.has("message") ? responseJson.get("message").getAsString() : null;
                String username = responseJson.has("username") ? responseJson.get("username").getAsString() : null;
                String authToken = responseJson.has("authToken") ? responseJson.get("authToken").getAsString() : null;

                return new RegisterResponse(username, authToken, message);
            }
        } else if(connection.getResponseCode() == 400){
            // Bad Request Error
            throw new Exception("There was a bad request. Make sure there are no empty fields.");
        } else if(connection.getResponseCode() == 403){
            // Already Taken Error
            throw new Exception("That username is already taken.");
        } else if(connection.getResponseCode() == 500){
            // Data Access Exception
            throw new Exception("There was a problem with the server.");
        } else {
            // Shouldn't reach here ...
            throw new Exception("Unknown Error");
        }

    }

}
