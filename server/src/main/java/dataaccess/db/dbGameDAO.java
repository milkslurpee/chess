package dataaccess.db;

import chess.ChessGame;
import dataaccess.GameDAO;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import models.GameModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class dbGameDAO implements GameDAO {

    @Override
    public GameModel read(int gameID) throws DataAccessException {
        String sql = "SELECT * FROM Game WHERE gameID = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, gameID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new GameModel(
                            rs.getInt("gameID"),
                            rs.getString("whiteUsername"),
                            rs.getString("blackUsername"),
                            rs.getString("gameName"),
                            // Assuming ChessGame has a constructor or method to parse from string or database
                            new ChessGame(rs.getString("gameState"))
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
            // Assuming ChessGame has a method to convert game state to string
            stmt.setString(5, game.getGame().toString());
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
            stmt.setString(4, gameData.getGame().toString());
            stmt.setInt(5, gameID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void delete(int gameID) throws DataAccessException {
        String sql = "DELETE FROM Game WHERE gameID = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, gameID);
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
                games.add(new GameModel(
                        rs.getInt("gameID"),
                        rs.getString("whiteUsername"),
                        rs.getString("blackUsername"),
                        rs.getString("gameName"),
                        new ChessGame()
                ));
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return games;
    }
}
