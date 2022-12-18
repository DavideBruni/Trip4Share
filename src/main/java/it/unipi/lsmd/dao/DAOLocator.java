package it.unipi.lsmd.dao;

import it.unipi.lsmd.dao.mongo.TripMongoDAO;
import it.unipi.lsmd.dao.mongo.UserMongoDAO;
import it.unipi.lsmd.dao.neo4j.RegisteredUserNeo4jDAO;
import it.unipi.lsmd.dao.neo4j.TripNeo4jDAO;

public class DAOLocator {

    public static UserDAO getUserDAO(){
        return new UserMongoDAO();
    }
    public static RegisteredUserDAO getRegisteredUserDAO(){return new RegisteredUserNeo4jDAO();}

    public static TripDetailsDAO getTripDetailsDAO(){return new TripMongoDAO(); }
    public static TripDAO getTripDAO(){return new TripNeo4jDAO(); }
}
