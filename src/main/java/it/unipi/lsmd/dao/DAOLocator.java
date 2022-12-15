package it.unipi.lsmd.dao;

import it.unipi.lsmd.dao.mongo.UserMongoDAO;

public class DAOLocator {

    public static UserDAO getUserDAO(){
        return new UserMongoDAO();
    }

}
