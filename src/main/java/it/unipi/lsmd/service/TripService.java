package it.unipi.lsmd.service;

import it.unipi.lsmd.dto.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface TripService {

    List<TripSummaryDTO> getTripsOrganizedByFollowers(String username, int size, int page);

    List<TripSummaryDTO> getTripsByDestination(String destination, String departureDate, String returnDate, int size, int page);

    List<TripSummaryDTO> getTripsByTag(String tag, String departureDate, String returnDate, int size, int page);

    List<TripSummaryDTO> getTripsByPrice(int min_price, int max_price, String departureDate, String returnDate, int size, int page);

    TripDetailsDTO getTrip(String id);

    List<TripSummaryDTO> getTripsOrganizedByUser(String username, int size, int page);

    List<String> mostPopularDestinations(int limit);

    List<String> mostPopularDestinationsByTag(String tag, int limit);

    List<String> mostPopularDestinationsByPrice(double start, double end, int limit);

    List<String> mostPopularDestinationsByPeriod(String start, String end, int limit);

    List<PriceDestinationDTO> cheapestDestinationsByAvg(int page, int objectPerPageSearch);

    List<TripSummaryDTO> cheapestTripForDestinationInPeriod(String start, String end, int page, int objectPerPageSearch);
    List<TripSummaryDTO> getSuggestedTrips(String username, int numTrips);

    // change Parameter to DTO
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
}
