package dataaccess;

import dataaccess.DataAccessException;
import models.GameModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The GameDAO class provides data access methods for managing game data.
 */
public interface GameDAO {

    /**
     * Reads a game based on its ID.
     *
     * @param gameID The ID of the game to be retrieved.
     * @return The retrieved GameModel, or null if the game is not found.
     * @throws DataAccessException If there is an issue accessing the data.
     */
    public GameModel read(int gameID) throws DataAccessException;

    public void updateGame(int gameID, GameModel gameData);

    /**
     * Inserts a GameModel into the data store.
     *
     * @param game The GameModel to be inserted.
     * @throws DataAccessException If there is an issue accessing the data.
     */
    public void insert(GameModel game) throws DataAccessException;

    /**
     * Deletes a game based on its ID.
     *
     * @param gameID The ID of the game to be deleted.
     * @throws DataAccessException If there is an issue accessing the data.
     */

    /**
     * Clears all game data from the data store.
     *
     * @throws DataAccessException If there is an issue accessing the data.
     */
    public void clear();


    public List<GameModel> getGameList();
}