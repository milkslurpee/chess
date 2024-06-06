package dataaccess.db;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import models.Authtoken;

import java.sql.*;

public class dbAuthDAO implements AuthDAO {

    private static String tableName = "auth";

    public dbAuthDAO() {
        try {
            configureDatabase(createStatements);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public final String [] createStatements = {
            "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    " authToken VARCHAR(200) PRIMARY KEY," +
                    " username VARCHAR(200) NOT NULL" +
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
    public Authtoken read(String authtokenID) throws DataAccessException {
        Authtoken authtoken = null;
        String sql = "SELECT * FROM authtokens WHERE token = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authtokenID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    authtoken = new Authtoken(rs.getString("token"), rs.getString("username"));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while finding authtoken");
        }
        return authtoken;
    }

    @Override
    public void insert(Authtoken authtoken) throws DataAccessException {
        String sql = "INSERT INTO authtokens (token, username) VALUES (?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authtoken.getAuthToken());
            stmt.setString(2, authtoken.getUserName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting authtoken");
        }
    }

    @Override
    public void delete(String authtoken) throws DataAccessException {
        String sql = "DELETE FROM authtokens WHERE token = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authtoken);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while deleting authtoken");
        }
    }

    @Override
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM authtokens";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while clearing authtokens");
        }
    }

    @Override
    public boolean validToken(String authtoken) throws DataAccessException {
        String sql = "SELECT 1 FROM authtokens WHERE token = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authtoken);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while validating authtoken");
        }
    }
}
