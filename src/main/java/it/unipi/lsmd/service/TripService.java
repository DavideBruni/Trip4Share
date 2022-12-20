package it.unipi.lsmd.service;

import it.unipi.lsmd.dto.PriceDestinationDTO;
import it.unipi.lsmd.dto.TripDTO;
import it.unipi.lsmd.dto.TripHomeDTO;

import java.util.List;

public interface TripService {
    List<TripHomeDTO> getTripsOrganizedByFollowers(String username, int size, int page);
    List<TripHomeDTO> getTripsByDestination(String destination, String departureDate, String returnDate, int size, int page);
    List<TripHomeDTO> getTripsByTag(String tag, String departureDate, String returnDate, int size, int page);
    TripDTO getTrip(String id);

    List<String> mostPopularDestinations(int page, int objectPerPageSearch);

    List<String> mostPopularDestinationsByTag(String tag, int page, int objectPerPageSearch);

    List<String> mostPopularDestinationsByPrice(double start, double end, int page, int objectPerPageSearch);

    List<String> mostPopularDestinationsByPeriod(String start, String end, int page, int objectPerPageSearch);

    List<PriceDestinationDTO> cheapestDestinationsByAvg(int page, int objectPerPageSearch);

    List<TripHomeDTO> cheapestTripForDestinationInPeriod(String start, String end, int page, int objectPerPageSearch);
    List<TripHomeDTO> getSuggestedTrips(String username);
}
