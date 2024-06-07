package dataaccess.db;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import models.Authtoken;

import java.sql.*;

public class DbAuthDAO implements AuthDAO {

    int size = 0;


    @Override
    public void insert (Authtoken authData) throws DataAccessException {
        var statement = "INSERT INTO auth (authtoken, username) VALUES (?, ?)";
        try(Connection connection = DatabaseManager.getConnection();
            PreparedStatement stmt = connection.prepareStatement(statement)) {
            stmt.setString(1, authData.getAuthToken());
            stmt.setString(2, authData.getUserName());
            stmt.executeUpdate();
            size++;
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }


    }

    @Override
    public Authtoken read(String authToken) throws DataAccessException {
        var beansBaked = "SELECT authtoken, username FROM auth WHERE authtoken = ?";
        try(Connection connection = DatabaseManager.getConnection();
            PreparedStatement stmt = connection.prepareStatement(beansBaked)) {
            stmt.setString(1, authToken);
            var sqlLine = stmt.executeQuery();
            if (sqlLine.next()) {
                var token = sqlLine.getString(1);
                var user = sqlLine.getString(2);
                if (token == null || user == null) {
                    throw new DataAccessException("unauthorized");
                }
                return new Authtoken(token, user);
            }
            else {
                throw new DataAccessException("unauthorized");
            }

        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void delete(String authToken) throws DataAccessException {
        var statement = "DELETE FROM auth WHERE authToken = ?";
        executeUpdate(statement, authToken);
        size--;

    }

    @Override
    public void clear() throws DataAccessException {
        var statement = "DELETE FROM auth";
        executeUpdate(statement);
        size=0;

    }

    public int size() {
        return size;
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



}
