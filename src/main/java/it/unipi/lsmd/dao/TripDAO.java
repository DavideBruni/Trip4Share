package it.unipi.lsmd.dao;

import it.unipi.lsmd.dao.neo4j.exceptions.Neo4jException;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Trip;
import it.unipi.lsmd.model.enums.Status;

import java.util.List;

public interface TripDAO {
    List<Trip> getTripsOrganizedByFollower(String follower, int size, int page);
    List<Trip> getSuggestedTrip(String username, int numTrips);

    void addTrip(Trip t, RegisteredUser organizer) throws Neo4jException;

    void deleteTrip(Trip t) throws Neo4jException;

    void setNotDeleted(Trip t) throws Neo4jException;

    void updateTrip(Trip newTrip, Trip oldTrip) throws Neo4jException;

    Trip getJoinersAndOrganizer(Trip t);

    void removeJoin(Trip t, RegisteredUser r) throws Neo4jException;

    // TODO: set pending
    void setStatusJoin(Trip t, RegisteredUser r, Status status) throws Neo4jException;
    RegisteredUser getOrganizer(Trip trip) throws Neo4jException;

    List<Trip> getTripOrganizedByUser(String organizer, int size, int page);

    List<Trip> getPastTrips(String organizer, int size, int page);

    void createJoin(Trip t, RegisteredUser r) throws Neo4jException;

    void cancelJoin(Trip t, RegisteredUser r) throws Neo4jException;

    Status getJoinStatus(Trip t, RegisteredUser registeredUser) throws Neo4jException;
}
