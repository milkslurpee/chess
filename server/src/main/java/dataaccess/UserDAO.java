package dataaccess;

import models.User;

import java.util.HashMap;
import java.util.Map;

/**
 * The UserDAO class provides data access methods for managing user data.
 */
public class UserDAO {

    private Map<String, User> userMap;
    /**
     * Reads a user based on their name.
     *
     * @param name The name of the user to be retrieved.
     * @return The retrieved User, or null if the user is not found.
     * @throws DataAccessException If there is an issue accessing the data.
     */

    public UserDAO() {
        this.userMap = new HashMap<>(); // Instantiating the userMap
    }

    public User read(String name) throws DataAccessException {
        if(!userMap.containsKey(name)){
            throw new DataAccessException("User doesn't exist");
        }
        else{
            return userMap.get(name);
        }
    }

    /**
     * Inserts a User into the data store.
     *
     * @param user The User to be inserted.
     * @throws DataAccessException If there is an issue accessing the data.
     */
    public void insert(User user) throws DataAccessException {
        String username = user.getUsername();
        if(userMap.containsKey(username)){
            throw new DataAccessException("Username already Taken");
        }
        else {
            userMap.put(username, user);
        }
    }

    /**
     * Deletes a user based on their name.
     *
     * @param name The name of the user to be deleted.
     * @throws DataAccessException If there is an issue accessing the data.
     */
    public void delete(String name) throws DataAccessException {
        if(!userMap.containsKey(name)){
            throw new DataAccessException("User doesn't exist");
        }
        else{
            userMap.remove(name);
        }
    }

    /**
     * Clears all user data from the data store.
     *
     * @throws DataAccessException If there is an issue accessing the data.
     */
    public void clear() throws DataAccessException {
        if(userMap.isEmpty()){
            throw new DataAccessException("There are no Users to clear");
        }
        else{
            userMap.clear();
        }
    }
}
