package it.unipi.lsmd.dao;

import it.unipi.lsmd.model.Trip;

import java.util.Date;
import java.util.List;

public interface TripDetailsDAO {

    Trip getTrip(String id);
    List<Trip> getTripsByTag(String tag, Date departureDate, Date returnDate, int size, int page);
    List<Trip> getTripsByDestination(String destination, Date departureDate, Date returnDate, int size, int page);

}
