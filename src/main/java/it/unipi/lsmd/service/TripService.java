package it.unipi.lsmd.service;

import it.unipi.lsmd.dto.*;

import java.util.List;

public interface TripService {

    List<TripSummaryDTO> getTripsOrganizedByFollowers(String username, int size, int page);

    List<TripSummaryDTO> getTripsByDestination(String destination, String departureDate, String returnDate, int size, int page);

    List<TripSummaryDTO> getTripsByTag(String tag, String departureDate, String returnDate, int size, int page);

    List<TripSummaryDTO> getTripsByPrice(double min_price, double max_price, String departureDate, String returnDate, int size, int page);

    TripDetailsDTO getTrip(String id);

    List<TripSummaryDTO> getTripsOrganizedByUser(String username, int size, int page);

    List<DestinationsDTO> mostPopularDestinations(int limit);

    List<DestinationsDTO> mostPopularDestinationsByTag(String tag, int limit);

    List<DestinationsDTO> mostPopularDestinationsByPrice(double start, double end, int limit);

    List<DestinationsDTO> mostPopularDestinationsByPeriod(String start, String end, int limit);

    List<PriceDestinationDTO> cheapestDestinationsByAvg(int objectPerPageSearch);

    List<TripDetailsDTO> cheapestTripForDestinationInPeriod(String start, String end, int objectPerPageSearch);

    List<TripSummaryDTO> getSuggestedTrips(String username, int numTrips);

    boolean addTrip(TripDetailsDTO tripDetailsDTO);

    boolean deleteTrip(String id);

    boolean updateTrip(TripDetailsDTO newTrip, TripDetailsDTO oldTrip);

    List<TripSummaryDTO> mostPopularTrips(int tripNumberIndex);

    InvolvedPeopleDTO getOrganizerAndJoiners(String id);

    boolean manageTripRequest(String id, String username, String action);

    List<TripSummaryDTO> getPastTrips(String username, int size, int page);

    String setJoin(String username, String trip_id);

    String cancelJoin(String username, String trip_id);

    String getJoinStatus(String trip_id, String username);

    List<DestinationsDTO> mostPopularTripsOverall(int suggestionsExplore);

    List<DestinationsDTO> mostExclusive(int suggestionsExplore);
}
