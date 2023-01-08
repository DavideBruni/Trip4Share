package it.unipi.lsmd.service;

import it.unipi.lsmd.dto.TripSummaryDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface WishlistService {

    void addToWishlist(String username, String trip_id, TripSummaryDTO tripSummary);

    void removeFromWishlist(String username, String trip_id, boolean decrease_counter);

    ArrayList<TripSummaryDTO> getWishlist(String username, int size, int page);

    LocalDateTime wishlistUpdateTime(String username, String trip_id);

}
