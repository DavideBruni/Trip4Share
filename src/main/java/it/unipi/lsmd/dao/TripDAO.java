package it.unipi.lsmd.dao;

import it.unipi.lsmd.model.Trip;

import java.util.List;

public interface TripDAO {
    List<Trip> getTripsOrganizedByFollower(String follower, int size, int page);
    List<Trip> getSuggestedTrip(String username);

}
