package it.unipi.lsmd.dao;

import it.unipi.lsmd.dao.neo4j.exceptions.Neo4jException;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Trip;
import it.unipi.lsmd.model.Wishlist;


public interface WishlistDAO {

    boolean addToWishlist(RegisteredUser user, Trip trip) throws Neo4jException;
    boolean removeFromWishlist(RegisteredUser user, Trip trip) throws Neo4jException;
    Wishlist getUserWishlist(RegisteredUser user, int size, int page);
    boolean flushWishlist(RegisteredUser user) throws Neo4jException;
    boolean isInWishlist(RegisteredUser user, Trip trip) throws Neo4jException;
}
