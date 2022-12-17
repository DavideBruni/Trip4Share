package it.unipi.lsmd.dao;

import it.unipi.lsmd.dao.mongo.TripMongoDAO;
import it.unipi.lsmd.dao.mongo.UserMongoDAO;
import it.unipi.lsmd.dao.neo4j.RegisteredUserDAONeo4j;

public class DAOLocator {

    public static UserDAO getUserDAO(){
        return new UserMongoDAO();
    }
    public static RegisteredUserDAO getRegisteredUserDAO(){return new RegisteredUserDAONeo4j();}

    public static TripDAO getTripDAO(){return new TripMongoDAO(); }
}
