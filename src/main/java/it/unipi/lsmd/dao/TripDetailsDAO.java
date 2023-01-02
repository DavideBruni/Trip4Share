package it.unipi.lsmd.dao;

import it.unipi.lsmd.dto.TripDetailsDTO;
import it.unipi.lsmd.model.Trip;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface TripDetailsDAO {

    Trip getTrip(String id);
    List<Trip> getTripsByTag(String tag, LocalDate departureDate, LocalDate returnDate, int size, int page);

    //List<Trip> getTripsByDestination(String destination, int size, int page);

    List<Trip> getTripsByDestination(String destination, LocalDate departureDate, LocalDate returnDate, int size, int page);

    List<Trip> getTripsByPrice(int min_price, int max_price, LocalDate departureDate, LocalDate returnDate, int size, int page);

    List<String> mostPopularDestinations(int limit);

    List<String> mostPopularDestinationsByTag(String tag, int limit);

    List<String> mostPopularDestinationsByPrice(double start, double end, int limit);

    List<String> mostPopularDestinationsByPeriod(LocalDate depDate, LocalDate retDate, int limit);

    List<Trip> cheapestDestinationsByAvg(int page, int objectPerPageSearch);

    List<Trip> cheapestTripForDestinationInPeriod(LocalDate start, LocalDate end, int page, int objectPerPageSearch);

    String addTrip(Trip t);

    boolean deleteTrip(Trip t);

    boolean updateTrip(Trip newTrip, Trip oldTrip);

    List<Trip> mostPopularTrips(int tripNumberIndex);
}
