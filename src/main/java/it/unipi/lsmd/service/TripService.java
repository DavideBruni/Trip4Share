package it.unipi.lsmd.service;

import it.unipi.lsmd.dto.TripDTO;
import it.unipi.lsmd.dto.TripHomeDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface TripService {
    public List<TripHomeDTO> getTripsOrganizedByFollowers(String username);
    public TripDTO getTrip(String id);

    public void addToWishlist(String username, String trip_id, HashMap<String, Object> data);

    public void removeFromWishlist(String username, String trip_id);

    public ArrayList<TripDTO> getWishlist(String username);
}
