package models;

/**
 * The User class represents a user with a username, password, and email.
 */
public class User {
    /**
     * The username of the user.
     */
    String username;

    /**
     * The password of the user.
     */
    String password;

    /**
     * The email address of the user.
     */
    String email;

    /**
     * Constructs a new User with the provided username, password, and email.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @param email The email address of the user.
     */
    public User(String username, String password, String email) {
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
