package it.unipi.lsmd.service;

import it.unipi.lsmd.dto.TripDetailsDTO;
import it.unipi.lsmd.dto.TripSummaryDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface TripService {
    public List<TripSummaryDTO> getTripsOrganizedByFollowers(String username);
    public TripDetailsDTO getTrip(String id);

    public void addToWishlist(String username, String trip_id, HashMap<String, Object> data);

    public void removeFromWishlist(String username, String trip_id);

    public ArrayList<TripDetailsDTO> getWishlist(String username);
}
