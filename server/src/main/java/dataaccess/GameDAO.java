package dataaccess;

import models.GameModel;

import java.util.HashMap;
import java.util.Map;

/**
 * The GameDAO class provides data access methods for managing game data.
 */
public class GameDAO {

    private Map<Integer, GameModel> gameMap = new HashMap<>();

    public GameDAO() {
        // Initialize the gameMap in the constructor

    }

    /**
     * Reads a game based on its ID.
     *
     * @param gameID The ID of the game to be retrieved.
     * @return The retrieved GameModel, or null if the game is not found.
     * @throws DataAccessException If there is an issue accessing the data.
     */
    public GameModel read(int gameID) throws DataAccessException {
        GameModel neededGame = gameMap.get(gameID);
        if(neededGame == null){
            throw new DataAccessException("bad request");
        }
        return neededGame;
    }

    public void updateGame(int gameID, GameModel gameData) {
        gameMap.replace(gameID, gameData);
    }

    /**
     * Inserts a GameModel into the data store.
     *
     * @param game The GameModel to be inserted.
     * @throws DataAccessException If there is an issue accessing the data.
     */
    public void insert(GameModel game) throws DataAccessException {
        int gameID = game.getGameID();
        if(gameMap.containsKey(gameID)){
            throw new DataAccessException("Game already exists");
        }
        else{
            gameMap.put(gameID, game);
        }
    }

    /**
     * Deletes a game based on its ID.
     *
     * @param gameID The ID of the game to be deleted.
     * @throws DataAccessException If there is an issue accessing the data.
     */
    public void delete(int gameID) throws DataAccessException {
        if(!gameMap.containsKey(gameID)){
            throw new DataAccessException("Game doesn't exist");
        }
        else{
            gameMap.remove(gameID);
        }
    }

    /**
     * Clears all game data from the data store.
     *
     * @throws DataAccessException If there is an issue accessing the data.
     */
    public void clear() {
        gameMap.clear();
    }

    public Map<Integer, GameModel> getGameMap() throws DataAccessException{
        if(!gameMap.isEmpty()) {
            return gameMap;
        }
        else{
            throw new DataAccessException("There are no Games to list");
        }
    }
}
