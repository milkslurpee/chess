package dataaccess;

import models.Authtoken;

import java.util.HashMap;
import java.util.Map;

/**
 * The AuthDAO class provides data access methods for handling authentication tokens.
 */
public class AuthDAO {
    private final Map<String, Authtoken> authMap;
    /**
     * Reads an authentication token based on its ID.
     *
     * @param authtokenID The ID of the authentication token to be retrieved.
     * @return The retrieved authentication token, or null if not found.
     * @throws DataAccessException If there is an issue accessing the data.
     */

    public AuthDAO() {
        this.authMap = new HashMap<>(); // Instantiating the userMap
    }

    public Authtoken read(String authtokenID) throws DataAccessException {
        if(!authMap.containsKey(authtokenID)){
            throw new DataAccessException("Authtoken doesn't exist");
        }
        else {
            return authMap.get(authtokenID);
        }
    }

    /**
     * Inserts an authentication token into the data store.
     *
     * @param authtoken The authentication token to be inserted.
     * @throws DataAccessException If there is an issue accessing the data.
     */
    public void insert(Authtoken authtoken) throws DataAccessException {
        String authtokenID = authtoken.getUserName();
        if(authMap.containsKey(authtokenID)){
            throw new DataAccessException("Authtoken already exists");
        }
        else {
            authMap.put(authtokenID, authtoken);
        }
    }

    /**
     * Deletes an authentication token based on its ID.
     *
     * @param authtokenID The ID of the authentication token to be deleted.
     * @throws DataAccessException If there is an issue accessing the data.
     */
    public void delete(String authtokenID) throws DataAccessException {
        if(!authMap.containsKey(authtokenID)){
            throw new DataAccessException("Authtoken doesn't exist");
        }
        else {
            authMap.remove(authtokenID);
        }
    }

    /**
     * Clears all authentication tokens from the data store.
     *
     * @throws DataAccessException If there is an issue accessing the data.
     */
    public void clear() throws DataAccessException {
        if(authMap.isEmpty()){
            throw new DataAccessException("No Authtokens to clear");
        }
        else {
            authMap.clear();
        }
    }

    public boolean validToken(Authtoken authtoken){
        String username = authtoken.getUserName();
        String authToken = authtoken.getAuthToken();

        return authMap.get(username) == authtoken;
    }
}
