package services;


import dataaccess.DataAccessException;
import dataaccess.db.*;

public class VerifyAuth {

    public void verifyAuth(dbAuthDAO authDAO, String token) throws DataAccessException {
        authDAO.read(token);
    }

}