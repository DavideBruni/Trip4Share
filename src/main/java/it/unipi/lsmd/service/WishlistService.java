package it.unipi.lsmd.service;

import it.unipi.lsmd.dto.TripDetailsDTO;
import it.unipi.lsmd.dto.TripSummaryDTO;

import java.util.ArrayList;

public interface WishlistService {

    boolean addToWishlist(String username, TripDetailsDTO tripDetailsDTO);

    boolean removeFromWishlist(String username, TripDetailsDTO tripDetailsDTO);

    boolean removeFromWishlist(String username, String trip_id);

    ArrayList<TripSummaryDTO> getWishlist(String username, int size, int page);

    boolean isInWishlist(String username, TripDetailsDTO tripDetailsDTO);
    boolean isInWishlist(String username, String trip_id);
}
