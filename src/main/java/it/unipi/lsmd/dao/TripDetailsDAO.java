package it.unipi.lsmd.dao;

import it.unipi.lsmd.model.Trip;

import java.util.Date;
import java.util.List;

public interface TripDetailsDAO {

    Trip getTrip(String id);
    List<Trip> getTripsByTag(String tag, Date departureDate, Date returnDate, int size, int page);

    List<Trip> getTripsByDestination(String destination, int size, int page);

    List<Trip> getTripsByDestination(String destination, Date departureDate, Date returnDate, int size, int page);

    List<String> mostPopularDestinations(int page, int objectPerPageSearch);

    List<String> mostPopularDestinationsByTag(String tag, int page, int objectPerPageSearch);

    List<String> mostPopularDestinationsByPrice(double start, double end, int page, int objectPerPageSearch);

    List<String> mostPopularDestinationsByPeriod(Date depDate, Date retDate, int page, int objectPerPageSearch);

    List<Trip> cheapestDestinationsByAvg(int page, int objectPerPageSearch);

    List<Trip> cheapestTripForDestinationInPeriod(Date start, Date end, int page, int objectPerPageSearch);

    String addTrip(Trip t);

    boolean deleteTrip(Trip t);
}
