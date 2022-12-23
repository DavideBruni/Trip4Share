package it.unipi.lsmd.service;

import it.unipi.lsmd.dto.OtherUserDTO;
import it.unipi.lsmd.dto.PriceDestinationDTO;
import it.unipi.lsmd.dto.TripDetailsDTO;
import it.unipi.lsmd.dto.TripSummaryDTO;
import java.util.ArrayList;
import java.util.List;

public interface TripService {

    void addToWishlist(String username, String trip_id, TripSummaryDTO tripSummary);
    void removeFromWishlist(String username, String trip_id);
    ArrayList<TripSummaryDTO> getWishlist(String username);

    List<TripSummaryDTO> getTripsOrganizedByFollowers(String username, int size, int page);
    List<TripSummaryDTO> getTripsByDestination(String destination, String departureDate, String returnDate, int size, int page);
    List<TripSummaryDTO> getTripsByTag(String tag, String departureDate, String returnDate, int size, int page);
    TripDetailsDTO getTrip(String id);

    List<String> mostPopularDestinations(int page, int objectPerPageSearch);

    List<String> mostPopularDestinationsByTag(String tag, int page, int objectPerPageSearch);

    List<String> mostPopularDestinationsByPrice(double start, double end, int page, int objectPerPageSearch);

    List<String> mostPopularDestinationsByPeriod(String start, String end, int page, int objectPerPageSearch);

    List<PriceDestinationDTO> cheapestDestinationsByAvg(int page, int objectPerPageSearch);

    List<TripSummaryDTO> cheapestTripForDestinationInPeriod(String start, String end, int page, int objectPerPageSearch);
    List<TripSummaryDTO> getSuggestedTrips(String username, int numTrips);

    // change Parameter to DTO
    boolean addTrip(TripDetailsDTO tripDetailsDTO, OtherUserDTO organizer);

    boolean deleteTrip(TripDetailsDTO t);

    boolean updateTrip(TripDetailsDTO newTrip, TripDetailsDTO oldTrip);
}
