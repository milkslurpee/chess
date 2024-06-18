package facade;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import requests.*;
import responses.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ClientCommunicator {

  private static final int TIMEOUT = 5000;
  private static final Gson GSON = new Gson();

  public RegisterResponse registerUser(String url, RegisterRequest request) throws Exception {
    HttpURLConnection connection = createPostConnection(url + "/user");

    JsonObject json = new JsonObject();
    json.addProperty("username", request.getUsername());
    json.addProperty("password", request.getPassword());
    json.addProperty("email", request.getEmail());

    sendRequestBody(connection, json.toString());

    return handleResponse(connection, RegisterResponse.class);
  }

  public LoginResponse loginUser(String url, LoginRequest request) throws Exception {
    HttpURLConnection connection = createPostConnection(url + "/session");

    JsonObject json = new JsonObject();
    json.addProperty("username", request.getUsername());
    json.addProperty("password", request.getPassword());

    sendRequestBody(connection, json.toString());

    return handleResponse(connection, LoginResponse.class);
  }

  public boolean logoutUser(String url, String authToken) throws Exception {
    HttpURLConnection connection = createDeleteConnection(url + "/session");
    connection.setRequestProperty("Authorization", authToken);

    return handleSimpleResponse(connection);
  }

  public CreateGameResponse createGame(String url, CreateGameRequest request, String authToken) throws Exception {
    HttpURLConnection connection = createPostConnection(url + "/game");
    connection.setRequestProperty("Authorization", authToken);

    JsonObject json = new JsonObject();
    json.addProperty("gameName", request.getGameName());

    sendRequestBody(connection, json.toString());

    return handleResponse(connection, CreateGameResponse.class);
  }

  public ListResponse listGames(String url, String authToken) throws Exception {
    HttpURLConnection connection = createGetConnection(url + "/game");
    connection.setRequestProperty("Authorization", authToken);

    return handleResponse(connection, ListResponse.class);
  }

  public boolean joinGame(String url, JoinGameRequest request, String authToken) throws Exception {
    HttpURLConnection connection = createPutConnection(url + "/game");
    connection.setRequestProperty("Authorization", authToken);

    JsonObject json = new JsonObject();
    json.addProperty("playerColor", request.playerColor().toUpperCase());
    json.addProperty("gameID", request.gameID());

    sendRequestBody(connection, json.toString());

    return handleSimpleResponse(connection);
  }

  public boolean clearDatabase(String url) throws Exception {
    HttpURLConnection connection = createDeleteConnection(url + "/db");

    return handleSimpleResponse(connection);
  }

  private HttpURLConnection createPostConnection(String urlString) throws IOException {
    URL url = new URL(urlString);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setReadTimeout(TIMEOUT);
    connection.setRequestMethod("POST");
    connection.setDoOutput(true);
    return connection;
  }

  private HttpURLConnection createPutConnection(String urlString) throws IOException {
    URL url = new URL(urlString);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setReadTimeout(TIMEOUT);
    connection.setRequestMethod("PUT");
    connection.setDoOutput(true);
    return connection;
  }

  private HttpURLConnection createGetConnection(String urlString) throws IOException {
    URL url = new URL(urlString);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setReadTimeout(TIMEOUT);
    connection.setRequestMethod("GET");
    return connection;
  }

  private HttpURLConnection createDeleteConnection(String urlString) throws IOException {
    URL url = new URL(urlString);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setReadTimeout(TIMEOUT);
    connection.setRequestMethod("DELETE");
    return connection;
  }

  private void sendRequestBody(HttpURLConnection connection, String requestBody) throws IOException {
    try (OutputStream os = connection.getOutputStream()) {
      byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
      os.write(input, 0, input.length);
    }
  }

  private <T> T handleResponse(HttpURLConnection connection, Class<T> responseType) throws Exception {
    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
      try (InputStream responseBody = connection.getInputStream();
           BufferedReader reader = new BufferedReader(new InputStreamReader(responseBody, StandardCharsets.UTF_8))) {
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
          response.append(line);
        }
        return GSON.fromJson(response.toString(), responseType);
      }
    } else {
      handleErrorResponse(connection);
      return null; // This will never be reached because handleErrorResponse always throws an exception
    }
  }

  private boolean handleSimpleResponse(HttpURLConnection connection) throws Exception {
    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
      return true;
    } else {
      handleErrorResponse(connection);
      return false; // This will never be reached because handleErrorResponse always throws an exception
    }
  }

  private void handleErrorResponse(HttpURLConnection connection) throws Exception {
    switch (connection.getResponseCode()) {
      case HttpURLConnection.HTTP_BAD_REQUEST:
        throw new Exception("Bad request. Please ensure all fields are filled correctly.");
      case HttpURLConnection.HTTP_UNAUTHORIZED:
        throw new Exception("Unauthorized request.");
      case HttpURLConnection.HTTP_FORBIDDEN:
        throw new Exception("Forbidden. The requested action is not allowed.");
      case HttpURLConnection.HTTP_INTERNAL_ERROR:
        throw new Exception("Internal server error.");
      default:
        throw new Exception("Unexpected error occurred.");
    }
  }
}
