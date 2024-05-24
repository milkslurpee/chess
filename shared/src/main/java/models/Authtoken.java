package models;

/**
 * The Authtoken class represents an authentication token associated with a user.
 */
public class Authtoken {
    /**
     * The authentication token.
     */
    private String authToken;

    /**
     * The username associated with the token.
     */
    private String userName;

    /**
     * Constructs a new Authtoken with the provided authentication token and username.
     *
     * @param authToken The authentication token.
     * @param userName The username associated with the token.
     */
    public Authtoken(String authToken, String userName) {
        this.authToken = authToken;
        this.userName = userName;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}