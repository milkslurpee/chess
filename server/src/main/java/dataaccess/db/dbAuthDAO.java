package dataaccess.db;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import models.Authtoken;

import java.sql.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class dbAuthDAO implements AuthDAO {
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
        var statement = "SELECT authtoken, username FROM auth WHERE authtoken = ?";
        try(Connection connection = DatabaseManager.getConnection();
            PreparedStatement stmt = connection.prepareStatement(statement)) {
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

    static int executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    switch (param) {
                        case String p -> ps.setString(i + 1, p);
                        case Integer p -> ps.setInt(i + 1, p);
                        case ChessGame p -> ps.setString(i + 1, p.toString());
                        case null -> ps.setNull(i + 1, NULL);
                        default -> {
                        }
                    }
                }
                ps.executeUpdate();

                var rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }

                return 0;
            }
        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException(e.getMessage());
        }
    }


    public boolean validToken(String authtoken) throws DataAccessException {
        String sql = "SELECT COUNT(*) FROM auth_tokens WHERE token = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, authtoken);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }


}
