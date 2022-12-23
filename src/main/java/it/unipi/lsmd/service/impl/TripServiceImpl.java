package it.unipi.lsmd.service.impl;

import it.unipi.lsmd.dao.DAOLocator;
import it.unipi.lsmd.dao.TripDAO;
import it.unipi.lsmd.dao.WishlistDAO;
import it.unipi.lsmd.dao.TripDetailsDAO;
import it.unipi.lsmd.dao.mongo.WishlistMongoDAO;
import it.unipi.lsmd.dao.neo4j.exceptions.Neo4jException;
import it.unipi.lsmd.dao.redis.WishlistRedisDAO;
import it.unipi.lsmd.dto.PriceDestinationDTO;
import it.unipi.lsmd.dto.TripDetailsDTO;
import it.unipi.lsmd.dto.TripSummaryDTO;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Trip;
import it.unipi.lsmd.service.TripService;
import it.unipi.lsmd.utils.TripUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.List;

public class TripServiceImpl implements TripService {

    private TripDetailsDAO tripDetailsDAO;
    private TripDAO tripDAO;
    private WishlistRedisDAO wishlistRedisDAO;
    private WishlistMongoDAO wishlistMongoDAO;

    public TripServiceImpl(){
        tripDetailsDAO = DAOLocator.getTripDetailsDAO();
        tripDAO = DAOLocator.getTripDAO();
        wishlistRedisDAO = new WishlistRedisDAO();
        wishlistMongoDAO = new WishlistMongoDAO();
    }

    @Override
    public List<TripSummaryDTO> getTripsOrganizedByFollowers(String username, int size, int page) {
        List<Trip> trips = tripDAO.getTripsOrganizedByFollower(username, size, page);
        if(trips == null || trips.isEmpty()){
            return new ArrayList<>();
        }
        List<TripSummaryDTO> tripsDTO = new ArrayList<>();
        for(Trip t : trips){
            TripSummaryDTO tripSummaryDTO = new TripSummaryDTO();
            tripSummaryDTO.setDestination(t.getDestination());
            tripSummaryDTO.setDeleted(t.getDeleted());
            tripSummaryDTO.setDepartureDate(t.getDepartureDate());
            tripSummaryDTO.setReturnDate(t.getReturnDate());
            tripSummaryDTO.setTitle(t.getTitle());
            tripSummaryDTO.setImgUrl(t.getImg());

            tripsDTO.add(tripSummaryDTO);
        }
        return tripsDTO;
    }

    public TripDetailsDTO getTrip(String id){
        Trip trip = tripDetailsDAO.getTrip(id);
        return TripUtils.tripModelToDetailedDTO(trip);
    }

    @Override
    public void addToWishlist(String username, String trip_id, TripSummaryDTO tripSummary){

        Trip trip = TripUtils.tripFromTripSummary(tripSummary);

        if(wishlistRedisDAO.addToWishlist(username, trip_id, trip)){
            wishlistMongoDAO.addToWishlist(trip_id);
        }else{
            // TODO - send error message -> trip already added
            System.out.println("Trip already in wishlist");
        }


    }

    @Override
    public void removeFromWishlist(String username, String trip_id) {
        if(wishlistRedisDAO.removeFromWishlist(username, trip_id)){
            wishlistMongoDAO.removeFromWishlist(trip_id);
        }else{
            // TODO - send error message -> trip not added yet
            System.out.println("Trip is not in wishlist");
        }

    }

    @Override
    public ArrayList<TripSummaryDTO> getWishlist(String username) {

        ArrayList<TripSummaryDTO> trips = new ArrayList<TripSummaryDTO>();

        for(Trip t : wishlistRedisDAO.getUserWishlist(username)){
            trips.add(TripUtils.tripSummaryDTOFromModel(t));
        }

        return trips;
    }

    public List<TripSummaryDTO> getTripsByDestination(String destination, String departureDate, String returnDate, int size, int page) {
        Date depDate = null;
        Date retDate = null;
        try {
            depDate =new SimpleDateFormat("dd-MM-yyyy").parse(departureDate);
            try{
                retDate = new SimpleDateFormat("dd-MM-yyyy").parse(returnDate);
            }catch (ParseException ex){ }
        } catch (ParseException e) {
            depDate = new Date();
        }finally{
            List<Trip> trips= tripDetailsDAO.getTripsByDestination(destination,depDate,retDate,size,page);
            List<TripSummaryDTO> tripsDTO = new ArrayList<>();
            for(Trip t : trips){
                TripSummaryDTO tDTO = TripUtils.parseTrip(t);
                tripsDTO.add(tDTO);
            }
            return tripsDTO;
        }

    }

    @Override
    public List<TripSummaryDTO> getTripsByTag(String tag, String departureDate, String returnDate, int size, int page) {
        Date depDate = null;
        Date retDate = null;
        try {
            depDate =new SimpleDateFormat("dd-MM-yyyy").parse(departureDate);
            try{
                retDate = new SimpleDateFormat("dd-MM-yyyy").parse(returnDate);
            }catch (ParseException ex){ }
        } catch (ParseException e) {
            depDate = new Date();
        }finally{
            List<Trip> trips= tripDetailsDAO.getTripsByTag(tag,depDate,retDate,size,page);
            List<TripSummaryDTO> tripsDTO = new ArrayList<>();
            for(Trip t : trips){
                TripSummaryDTO tDTO = TripUtils.parseTrip(t);
                tripsDTO.add(tDTO);
            }
            return tripsDTO;
        }
    }

    @Override
    public List<String> mostPopularDestinations(int page, int objectPerPageSearch) {
        return tripDetailsDAO.mostPopularDestinations(page, objectPerPageSearch);
    }

    @Override
    public List<String> mostPopularDestinationsByTag(String tag, int page, int objectPerPageSearch) {
        List<String> trips= tripDetailsDAO.mostPopularDestinationsByTag(tag,page,objectPerPageSearch );
        return trips;
    }

    @Override
    public List<String> mostPopularDestinationsByPrice(double start, double end, int page, int objectPerPageSearch) {
        return tripDetailsDAO.mostPopularDestinationsByPrice(start, end,page,objectPerPageSearch);
    }

    @Override
    public List<String> mostPopularDestinationsByPeriod(String start, String end, int page, int objectPerPageSearch) {
        Date depDate = null;
        Date retDate = null;
        try {
            depDate =new SimpleDateFormat("dd-MM-yyyy").parse(start);
            try{
                retDate = new SimpleDateFormat("dd-MM-yyyy").parse(end);
            }catch (ParseException ex){ }
        } catch (ParseException e) {
            depDate = new Date();
        }
        return tripDetailsDAO.mostPopularDestinationsByPeriod(depDate, retDate,page,objectPerPageSearch);
    }

    @Override
    public List<PriceDestinationDTO> cheapestDestinationsByAvg(int page, int objectPerPageSearch) {
        List<Trip> trips = tripDetailsDAO.cheapestDestinationsByAvg(page, objectPerPageSearch);
        List<PriceDestinationDTO> dest = new ArrayList<>();
        for(Trip trip : trips) {
            PriceDestinationDTO d = new PriceDestinationDTO();
            d.setPrice(trip.getPrice());
            d.setDestination(trip.getDestination());
            dest.add(d);
        }
        return dest;

    }

    @Override
    public  List<TripSummaryDTO> cheapestTripForDestinationInPeriod(String start, String end, int page, int objectPerPageSearch) {
        Date depDate = null;
        Date retDate = null;
        try {
            depDate =new SimpleDateFormat("dd-MM-yyyy").parse(start);
            try{
                retDate = new SimpleDateFormat("dd-MM-yyyy").parse(end);
            }catch (ParseException ex){ }
        } catch (ParseException e) {
            depDate = new Date();
        }
        List<Trip> trips = tripDetailsDAO.cheapestTripForDestinationInPeriod(depDate,retDate,page, objectPerPageSearch);
        List<TripSummaryDTO> tripsDTO = new ArrayList<>();
        for(Trip t : trips){
            TripSummaryDTO tDTO = TripUtils.parseTrip(t);
            tripsDTO.add(tDTO);
        }
        return tripsDTO;
    }

    @Override
    public List<TripSummaryDTO> getSuggestedTrips(String username) {
        List<Trip> trips_model = tripDAO.getSuggestedTrip(username);
        List<TripSummaryDTO> trips = new ArrayList<>();
        for(Trip t : trips_model){
            TripSummaryDTO tripSummaryDTO = TripUtils.parseTrip(t);
            trips.add(tripSummaryDTO);
        }
        return trips;
    }

    // change Parameter to DTO
    @Override
    public boolean addTrip(Trip t, RegisteredUser organizer){
        String id = tripDetailsDAO.addTrip(t);
        if(id!=null){
            t.setId(id);
            try {
                tripDAO.addTrip(t,organizer);
                return true;
            } catch (Neo4jException e) {
                //logger errore neo4j
                if(!tripDetailsDAO.deleteTrip(t))
                    System.err.println("Errore mongo");
                return false;
            }
        }
        return false;
    }

    // change Parameter to DTO
    @Override
    public boolean deleteTrip(Trip t){
        try {
            tripDAO.deleteTrip(t);
            if(tripDetailsDAO.deleteTrip(t)){
                return true;
            }else{
                tripDAO.setNotDeleted(t);
            }
        } catch (Neo4jException e) {
            System.err.println("Errore neo4j");
            return false;
        }
        return true;
    }
}
