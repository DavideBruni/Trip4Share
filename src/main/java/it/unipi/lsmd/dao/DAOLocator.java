package it.unipi.lsmd.dao;

import it.unipi.lsmd.dao.mongo.TripMongoDAO;
import it.unipi.lsmd.dao.mongo.UserMongoDAO;
import it.unipi.lsmd.dao.neo4j.RegisteredUserDAONeo4j;
import it.unipi.lsmd.dao.redis.WishlistRedisDAO;

public class DAOLocator {

    public static UserDAO getUserDAO(){
        return new UserMongoDAO();
    }
    public static RegisteredUserDAO getRegisteredUserDAO(){return new RegisteredUserDAONeo4j();}

    public static TripDAO getTripDAO(){return new TripMongoDAO(); }

    public static WishlistDAO getWishlistDAO() { return new WishlistRedisDAO();  }
}
