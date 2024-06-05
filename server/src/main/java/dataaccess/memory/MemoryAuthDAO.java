package dataaccess.memory;

import dataaccess.DataAccessException;
import models.Authtoken;

import java.util.*;

/**
 * The AuthDAO class provides data access methods for handling authentication tokens.
 */
public class MemoryAuthDAO {
    private final Map<String, Authtoken> authMap;

    public MemoryAuthDAO() {
        this.authMap = new HashMap<>(); // Instantiating the userMap
    }
    /**
     * Reads an authentication token based on its ID.
     *
     * @param authtokenID The ID of the authentication token to be retrieved.
     * @return The retrieved authentication token, or null if not found.
     * @throws DataAccessException If there is an issue accessing the data.
     */

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
        String authtokenID = authtoken.getAuthToken();
        //System.out.println(authtokenID + " " + authtoken.getUserName());
        if(!Objects.equals(authtoken.getAuthToken(), authtokenID)){
            throw new DataAccessException("Authentification failed");
        }
        else {
            authMap.put(authtokenID, authtoken);
        }
    }

    /**
     * Deletes an authentication token based on its ID.
     *
     * @param authtoken The ID of the authentication token to be deleted.
     * @throws DataAccessException If there is an issue accessing the data.
     */
    public void delete(String authtoken) throws DataAccessException {
        // Find the key by the authtoken value
        String key = getKeyByAuthtoken(authMap, authtoken);

        if (key == null) {
            throw new DataAccessException("Authtoken doesn't exist");
        } else {
            authMap.remove(key);
        }
    }

    private String getKeyByAuthtoken(Map<String, Authtoken> map, String authtoken) {
        for (Map.Entry<String, Authtoken> entry : map.entrySet()) {
            if (Objects.equals(authtoken, entry.getValue().getAuthToken())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void verifyAuth(MemoryAuthDAO authDAO, String token) throws DataAccessException {
        authDAO.read(token);
    }


    /**
     * Clears all authentication tokens from the data store.
     *
     * @throws DataAccessException If there is an issue accessing the data.
     */
    public void clear() {
        authMap.clear();
    }

    public boolean validToken(String authtoken){
        return authMap.containsKey(authtoken);
    }
}
