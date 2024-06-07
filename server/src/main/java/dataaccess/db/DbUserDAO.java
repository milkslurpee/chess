package dataaccess.db;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import dataaccess.DatabaseManager;
import models.User;

import java.sql.*;

public class DbUserDAO implements UserDAO {
    int size = 0;

    @Override
    public void insert(User userInfo) throws DataAccessException {

        var statement = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        executeUpdate(statement, userInfo.getUsername(), userInfo.getPassword(), userInfo.getEmail());
        size++;
    }

    public void delete(String username) throws DataAccessException {
        String checkUserExistsSql = "SELECT COUNT(*) FROM users WHERE username = ?";
        String deleteUserSql = "DELETE FROM users WHERE username = ?";

        try (Connection conn = DatabaseManager.getConnection()) {
            // Check if the user exists
            try (PreparedStatement checkStmt = conn.prepareStatement(checkUserExistsSql)) {
                checkStmt.setString(1, username);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        throw new DataAccessException("User doesn't exist");
                    }
                }
            }

            // Delete the user
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteUserSql)) {
                deleteStmt.setString(1, username);
                deleteStmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }



    @Override
    public User read(String username) throws DataAccessException {
        var beansBaked = "SELECT username, password, email FROM users WHERE username = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(beansBaked)){
            stmt.setString(1, username);
            var sqlLine = stmt.executeQuery();
            if (sqlLine.next()) {
                var userName = sqlLine.getString(1);
                var passwordOfUser = sqlLine.getString(2);
                var emailOfUser = sqlLine.getString(3);
                return new User(userName, passwordOfUser, emailOfUser);
            }
        }
        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return null;
    }

    @Override
    public void clear() throws DataAccessException {
        var statement = "DELETE FROM users";
        executeUpdate(statement);
        size=0;

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


    public int size() {
        return size;
    }
}
