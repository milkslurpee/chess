package services;


import dataaccess.DataAccessException;
import dataaccess.*;

public class VerifyAuth {

    public void verifyAuth(AuthDAO authDAO, String token) throws DataAccessException {
        authDAO.read(token);
    }

}