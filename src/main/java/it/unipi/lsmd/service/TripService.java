package it.unipi.lsmd.service;

import it.unipi.lsmd.dto.TripDTO;
import it.unipi.lsmd.dto.TripHomeDTO;
import java.util.List;

public interface TripService {
    List<TripHomeDTO> getTripsOrganizedByFollowers(String username);
    List<TripHomeDTO> getTripsByDestination(String destination, String departureDate, String returnDate, int size, int page);

    public List<TripHomeDTO> getTripsByTag(String tag, String departureDate, String returnDate, int size, int page);

    public List<TripHomeDTO> getTripsOrganizedByFollowers(String username);

    public TripDTO getTrip(String id);
}
