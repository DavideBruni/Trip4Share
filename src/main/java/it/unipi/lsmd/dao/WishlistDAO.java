package it.unipi.lsmd.dao;

import it.unipi.lsmd.model.Trip;
import it.unipi.lsmd.model.Wishlist;

import java.time.LocalDateTime;

public interface WishlistDAO {

    boolean addToWishlist(String username, String trip_id, Trip trip);
    boolean removeFromWishlist(String username, String trip_id);
    Wishlist getUserWishlist(String username, int size, int page);
    LocalDateTime getUpdateTime(String username, String trip_id);
    boolean flushWishlist(String username);
}
