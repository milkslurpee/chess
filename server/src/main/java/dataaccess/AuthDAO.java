package dataaccess;

import models.Authtoken;

public interface AuthDAO {
    /**
     * Reads an authentication token based on its ID.
     *
     * @param authtokenID The ID of the authentication token to be retrieved.
     * @return The retrieved authentication token, or null if not found.
     * @throws DataAccessException If there is an issue accessing the data.
     */
    Authtoken read(String authtokenID) throws DataAccessException;

    /**
     * Inserts an authentication token into the data store.
     *
     * @param authtoken The authentication token to be inserted.
     * @throws DataAccessException If there is an issue accessing the data.
     */
    void insert(Authtoken authtoken) throws DataAccessException;

    /**
     * Deletes an authentication token based on its ID.
     *
     * @param authtoken The ID of the authentication token to be deleted.
     * @throws DataAccessException If there is an issue accessing the data.
     */
    void delete(String authtoken) throws DataAccessException;

    /**
     * Clears all authentication tokens from the data store.
     *
     * @throws DataAccessException If there is an issue accessing the data.
     */
    void clear() throws DataAccessException;

    /**
     * Verifies the existence of a token.
     *
     * @param token The token to be verified.
     * @throws DataAccessException If there is an issue accessing the data.
     */

    /**
     * Checks if the given token is valid.
     *
     * @param authtoken The token to be checked.
     * @return true if the token is valid, false otherwise.
     */


}
