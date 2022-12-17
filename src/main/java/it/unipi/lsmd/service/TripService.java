package it.unipi.lsmd.service;

import it.unipi.lsmd.dto.TripDTO;
import it.unipi.lsmd.dto.TripHomeDTO;

import java.util.List;

public interface TripService {
    public List<TripHomeDTO> getTripsOrganizedByFollowers(String username);

    public TripDTO getTrip(String id);
}
