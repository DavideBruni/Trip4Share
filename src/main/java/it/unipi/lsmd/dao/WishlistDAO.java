package it.unipi.lsmd.dao;

import it.unipi.lsmd.model.Trip;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface WishlistDAO {

    Boolean addToWishlist(String username, String trip_id, Trip trip);
    Boolean removeFromWishlist(String username, String trip_id);
    ArrayList<Trip> getUserWishlist(String username, int size, int page);
    LocalDateTime getUpdateTime(String username, String trip_id);
}
