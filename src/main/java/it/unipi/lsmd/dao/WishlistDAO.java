package it.unipi.lsmd.dao;

import it.unipi.lsmd.model.Trip;
import it.unipi.lsmd.model.Wishlist;

import java.util.HashMap;
import java.util.Map;

public interface WishlistDAO {

    void create(Wishlist wishlist);
    void addToWishlist(String username, String trip_id, HashMap<String, Object> data);
    void removeFromWishlist(String username, String trip_id);

}
