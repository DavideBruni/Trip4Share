package it.unipi.lsmd.service;

import it.unipi.lsmd.dto.TripDTO;
import it.unipi.lsmd.dto.TripHomeDTO;
import it.unipi.lsmd.model.Trip;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TripService {
    public List<TripHomeDTO> getTripsOrganizedByFollowers(String username);
    public TripDTO getTrip(String id);

    public void addToWishlist(String username, String trip_id, HashMap<String, Object> data);

    public void removeFromWishlist(String username, String trip_id);
}
