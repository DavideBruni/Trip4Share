package it.unipi.lsmd.dao;

import it.unipi.lsmd.model.Tag;
import it.unipi.lsmd.model.Trip;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.time.LocalDate;
import java.util.List;

public interface TripDetailsDAO {

    Trip getTrip(String id);

    List<Trip> getTripsByTag(Tag tag, LocalDate departureDate, LocalDate returnDate, int size, int page);

    List<Trip> getTripsByDestination(String destination, LocalDate departureDate, LocalDate returnDate, int size, int page);

    List<Trip> getTripsByPrice(double min_price, double max_price, LocalDate departureDate, LocalDate returnDate, int size, int page);

    List<Pair<String, Integer>> mostPopularDestinations(int limit);

    List<Pair<String, Integer>> mostPopularDestinationsByTag(String tag, int limit);

    List<Pair<String, Integer>> mostPopularDestinationsByPrice(double start, double end, int limit);

    List<Pair<String, Integer>> mostPopularDestinationsByPeriod(LocalDate depDate, LocalDate retDate, int limit);

    List<Trip> cheapestDestinationsByAvg(int objectPerPageSearch);

    List<Trip> cheapestTripForDestinationInPeriod(LocalDate start, LocalDate end, int page, int objectPerPageSearch);

    String addTrip(Trip t);

    boolean deleteTrip(Trip t);

    boolean updateTrip(Trip newTrip, Trip oldTrip);

    List<Trip> mostPopularTrips(int tripNumberIndex);

    List<Triplet<String, Integer, Integer>> mostPopularDestinationsOverall(int limit);

    List<Triplet<String, Integer, Integer>> mostExclusive(int limit);
}
