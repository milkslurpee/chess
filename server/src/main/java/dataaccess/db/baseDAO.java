package dataaccess.db;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;

import java.sql.*;

public class baseDAO {

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
