package it.unipi.lsmd.dao;

import it.unipi.lsmd.dto.TripSummaryDTO;
import it.unipi.lsmd.dto.TripWishlistDTO;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Trip;
import it.unipi.lsmd.model.Wishlist;

import java.time.LocalDateTime;

public interface WishlistDAO {

    boolean addToWishlist(RegisteredUser user, String trip_id, TripWishlistDTO trip);
    boolean removeFromWishlist(RegisteredUser user, Trip trip);
    Wishlist getUserWishlist(RegisteredUser user, int size, int page);
    LocalDateTime getUpdateTime(RegisteredUser user, Trip trip);
    boolean flushWishlist(RegisteredUser user);
}
