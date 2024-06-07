package dataaccess.db;

import chess.ChessGame;
import dataaccess.GameDAO;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import models.GameModel;
import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbGameDAO implements GameDAO {

    int size = 0;


    @Override
    public void insert(GameModel gameData) throws DataAccessException {
        var statement = "INSERT INTO games (gameID, whiteUsername, blackUsername, gameName, chessGame) VALUES (?, ?, ?, ?, ?)";
        var game = new Gson().toJson(gameData.getGame());
        executeUpdate(statement, gameData.getGameID(), gameData.getWhiteUsername(), gameData.getBlackUsername(), gameData.getGameName(), game);
        size++;
    }

    @Override
    public GameModel read(int gameID) throws DataAccessException {
        var statement = "SELECT * FROM games WHERE gameID = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(statement)) {
            stmt.setInt(1, gameID);
            var sqlLine = stmt.executeQuery();
            if (sqlLine.next()) {
                var gameIDSQL = sqlLine.getInt(1);
                var whiteUsername = sqlLine.getString(2);
                var blackUsername = sqlLine.getString(3);
                var gameName = sqlLine.getString(4);
                var chessGameString = sqlLine.getString(5);
                ChessGame chessgame = new Gson().fromJson(chessGameString, ChessGame.class);
                return new GameModel(gameIDSQL, whiteUsername, blackUsername, gameName, chessgame);
            }

        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }

        return null;
    }

    static int executeUpdate(String sql, Object... params) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = prepareStatement(conn, sql, params)) {

            int rowsAffected = stmt.executeUpdate();
            int generatedKey = 0;

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedKey = rs.getInt(1);
                }
            }

            return generatedKey;
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return 0; // Default return value if no generated key
    }

    private static PreparedStatement prepareStatement(Connection conn, String sql, Object... params) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < params.length; i++) {
            setParameter(stmt, i + 1, params[i]);
        }
        return stmt;
    }

    private static void setParameter(PreparedStatement stmt, int index, Object param) throws SQLException {
        if (param instanceof String) {
            stmt.setString(index, (String) param);
        } else if (param instanceof Integer) {
            stmt.setInt(index, (Integer) param);
        } else if (param instanceof ChessGame) {
            stmt.setString(index, param.toString());
        } else if (param == null) {
            stmt.setNull(index, Types.NULL);
        }
    }

    private static void handleSQLException(SQLException e) throws DataAccessException {
        e.printStackTrace();
        throw new DataAccessException(e.getMessage());
    }


    @Override
    public List<GameModel> getGameList() throws DataAccessException {
        List<GameModel> listOfGames = new ArrayList<>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM games";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int gameID = rs.getInt(1);
                        GameModel gameData = read(gameID);
                        listOfGames.add(gameData);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return listOfGames;
    }


    @Override
    public void updateGame(int gameID, GameModel gameData) throws DataAccessException {
        var statement = "UPDATE games SET whiteUsername = ?, blackUsername = ?, chessGame = ? WHERE gameID = ?";
        var jsonOfGame = new Gson().toJson(gameData.getGame());
        executeUpdate(statement, gameData.getWhiteUsername(), gameData.getBlackUsername(), jsonOfGame, gameID);

    }

    @Override
    public void clear() throws DataAccessException {
        var statement = "DELETE FROM games";
        executeUpdate(statement);
        size=0;

    }

    public int size() {
        return size;
    }
}
