package dataaccess.db;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import models.Authtoken;

import java.sql.*;

public class DbAuthDAO extends BaseDAO implements AuthDAO {

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

}
