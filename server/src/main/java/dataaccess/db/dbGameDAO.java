package dataaccess.db;

import chess.ChessGame;
import dataaccess.GameDAO;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import models.GameModel;
import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class dbGameDAO implements GameDAO {

    private final Gson gson = new Gson();

    private static String tableName = "game";

    public dbGameDAO() {
        try {
            configureDatabase(createStatements);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public final String[] createStatements = {
            "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    "  `gameID` INT PRIMARY KEY," +
                    "  `whiteUsername` VARCHAR(100) NULL," +
                    "  `blackUsername` VARCHAR(100) NULL," +
                    "  `gameName` VARCHAR(100) NULL," +
                    "  `game` TEXT NULL" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci"
    };

    public void configureDatabase(final String [] createStatements) throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Unable to configure database: " + ex.getMessage());
        }
    }


    @Override
    public GameModel read(int gameID) throws DataAccessException {
        String sql = "SELECT * FROM Game WHERE gameID = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, gameID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String gameStateJson = rs.getString("gameState");
                    ChessGame chessGame = gson.fromJson(gameStateJson, ChessGame.class);
                    return new GameModel(
                            rs.getInt("gameID"),
                            rs.getString("whiteUsername"),
                            rs.getString("blackUsername"),
                            rs.getString("gameName"),
                            chessGame
                    );
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }


    @Override
    public void insert(GameModel game) throws DataAccessException {
        String sql = "INSERT INTO Game (gameID, whiteUsername, blackUsername, gameName, gameState) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, game.getGameID());
            stmt.setString(2, game.getWhiteUsername());
            stmt.setString(3, game.getBlackUsername());
            stmt.setString(4, game.getGameName());
            String gameStateJson = gson.toJson(game.getGame());
            stmt.setString(5, gameStateJson);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }


    @Override
    public void updateGame(int gameID, GameModel gameData) throws DataAccessException {
        String sql = "UPDATE Game SET whiteUsername = ?, blackUsername = ?, gameName = ?, gameState = ? WHERE gameID = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, gameData.getWhiteUsername());
            stmt.setString(2, gameData.getBlackUsername());
            stmt.setString(3, gameData.getGameName());
            String gameStateJson = gson.toJson(gameData.getGame());
            stmt.setString(4, gameStateJson);
            stmt.setInt(5, gameID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }



    @Override
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Game";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public List<GameModel> getGameList() throws DataAccessException {
        List<GameModel> games = new ArrayList<>();
        String sql = "SELECT * FROM Game";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String gameStateJson = rs.getString("gameState");
                ChessGame chessGame = gson.fromJson(gameStateJson, ChessGame.class);
                games.add(new GameModel(
                        rs.getInt("gameID"),
                        rs.getString("whiteUsername"),
                        rs.getString("blackUsername"),
                        rs.getString("gameName"),
                        chessGame
                ));
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return games;
    }

}
