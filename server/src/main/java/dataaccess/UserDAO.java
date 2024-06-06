package dataaccess;

import dataaccess.DataAccessException;
import models.User;

import java.util.HashMap;
import java.util.Map;

/**
 * The UserDAO class provides data access methods for managing user data.
 */
public interface UserDAO {

    /**
     * Reads a user based on their name.
     *
     * @param name The name of the user to be retrieved.
     * @return The retrieved User, or null if the user is not found.
     * @throws DataAccessException If there is an issue accessing the data.
     */

    public User read(String name) throws DataAccessException;

    /**
     * Inserts a User into the data store.
     *
     * @param user The User to be inserted.
     * @throws DataAccessException If there is an issue accessing the data.
     */
    public void insert(User user) throws DataAccessException;

    /**
     * Deletes a user based on their name.
     *
     * @param name The name of the user to be deleted.
     * @throws DataAccessException If there is an issue accessing the data.
     */
    public void delete(String name) throws DataAccessException;

    /**
     * Clears all user data from the data store.
     *
     * @throws DataAccessException If there is an issue accessing the data.
     */
    public void clear() throws DataAccessException;
}
