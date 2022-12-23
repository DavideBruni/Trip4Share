package it.unipi.lsmd.dao;

import it.unipi.lsmd.model.Trip;
import it.unipi.lsmd.model.Wishlist;

import java.util.ArrayList;
import java.util.HashMap;

public interface WishlistDAO {

    void addToWishlist(String username, String trip_id, Trip trip);
    void removeFromWishlist(String username, String trip_id);
    ArrayList<Trip> getUserWishlist(String username);
}
