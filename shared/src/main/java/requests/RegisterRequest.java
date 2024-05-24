package requests;

/**
 * The RegisterRequest class represents a request to register a new user with a specified username, password, and email.
 */
public class RegisterRequest {
    /**
     * The desired username for registration.
     */
    private String username;

    /**
     * The desired password for registration.
     */
    private String password;

    /**
     * The email address provided for registration.
     */
    private String email;

    /**
     * Constructs a new RegisterRequest with the provided username, password, and email.
     *
     * @param username The desired username for registration.
     * @param password The desired password for registration.
     * @param email The email address provided for registration.
     */
    public RegisterRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}