package services;


import dataaccess.DataAccessException;
import dataaccess.memory.MemoryAuthDAO;

public class VerifyAuth {

    public void verifyAuth(MemoryAuthDAO authDAO, String token) throws DataAccessException {
        authDAO.read(token);
    }

}