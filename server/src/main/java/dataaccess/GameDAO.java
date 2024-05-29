package dataaccess;

import models.GameModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    /**
     * Clears all game data from the data store.
     *
     * @throws DataAccessException If there is an issue accessing the data.
     */
    public void clear() {
        gameMap.clear();
    }


    public List<GameModel> getGameList() {
        List<GameModel> listOfGames = new ArrayList<>();
        gameMap.forEach((integer, gameData) -> listOfGames.add(gameData));
        return listOfGames;
    }
}
