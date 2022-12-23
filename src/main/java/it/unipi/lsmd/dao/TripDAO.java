package it.unipi.lsmd.dao;

import it.unipi.lsmd.dao.neo4j.exceptions.Neo4jException;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Trip;

import java.util.List;

public interface TripDAO {
    List<Trip> getTripsOrganizedByFollower(String follower, int size, int page);
    List<Trip> getSuggestedTrip(String username);

    void addTrip(Trip t, RegisteredUser organizer) throws Neo4jException;

    void deleteTrip(Trip t) throws Neo4jException;

    void setNotDeleted(Trip t) throws Neo4jException;
}
