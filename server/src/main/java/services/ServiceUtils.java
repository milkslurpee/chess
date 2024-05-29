package services;


import dataaccess.DataAccessException;
import dataaccess.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ServiceUtils {

    public void verifyAuth(AuthDAO authDAO, String token) throws DataAccessException {
        authDAO.read(token);
    }

}