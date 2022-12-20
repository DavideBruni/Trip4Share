package it.unipi.lsmd.dao;

import it.unipi.lsmd.model.Trip;
import it.unipi.lsmd.model.Wishlist;

import java.util.ArrayList;
import java.util.HashMap;

public interface WishlistDAO {

    void addToWishlist(String user_id, String trip_id, HashMap<String, Object> data);
    void removeFromWishlist(String user_id, String trip_id);
    ArrayList<Trip> viewUserWishlist(String user_id);
}
