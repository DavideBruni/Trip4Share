package it.unipi.lsmd.service.impl;

import it.unipi.lsmd.dao.DAOLocator;
import it.unipi.lsmd.dao.TripDAO;
import it.unipi.lsmd.dao.TripDetailsDAO;
import it.unipi.lsmd.dao.mongo.WishlistMongoDAO;
import it.unipi.lsmd.dao.neo4j.TripNeo4jDAO;
import it.unipi.lsmd.dao.neo4j.exceptions.Neo4jException;
import it.unipi.lsmd.dao.redis.WishlistRedisDAO;
import it.unipi.lsmd.dto.*;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Trip;
import it.unipi.lsmd.model.enums.Status;
import it.unipi.lsmd.service.TripService;
import it.unipi.lsmd.utils.TripUtils;
import org.javatuples.Pair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TripServiceImpl implements TripService {

    private final TripDetailsDAO tripDetailsDAO;
    private final TripDAO tripDAO;
    private final WishlistRedisDAO wishlistRedisDAO;
    private final WishlistMongoDAO wishlistMongoDAO;

    private final TripNeo4jDAO organizerNeoDAO;

    public TripServiceImpl(){
        tripDetailsDAO = DAOLocator.getTripDetailsDAO();
        tripDAO = DAOLocator.getTripDAO();
        wishlistRedisDAO = new WishlistRedisDAO();
        wishlistMongoDAO = new WishlistMongoDAO();
        organizerNeoDAO = new TripNeo4jDAO();
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
            tripSummaryDTO.setId(t.getId());
            tripSummaryDTO.setOrganizer(t.getOrganizer().getUsername());
            tripsDTO.add(tripSummaryDTO);
        }
        return tripsDTO;
    }

    @Override
    public TripDetailsDTO getTrip(String id){
        Trip trip = tripDetailsDAO.getTrip(id);

        try {
            trip.setOrganizer(organizerNeoDAO.getOrganizer(trip));
        } catch (Neo4jException e) {
            System.out.println(e);
            trip.setOrganizer(null);
        }
        return TripUtils.tripModelToDetailedDTO(trip);
    }

    public LocalDateTime wishlistUpdateTime(String username, String trip_id){
        return wishlistRedisDAO.getUpdateTime(username, trip_id);
    }


    public List<TripSummaryDTO> getTripsOrganizedByUser(String username, int size, int page){
        List<Trip> trips_model = tripDAO.getTripOrganizedByUser(username, size, page);
        List<TripSummaryDTO> trips = new ArrayList<TripSummaryDTO>();
        for(Trip trip : trips_model){
            TripSummaryDTO tripSummaryDTO = TripUtils.tripSummaryDTOFromModel(trip);
            trips.add(tripSummaryDTO);
        }
        return trips;
    }

    public List<TripSummaryDTO> getPastTrips(String username, int size, int page){
        List<Trip> trips_model = tripDAO.getPastTrips(username, size, page);
        List<TripSummaryDTO> trips = new ArrayList<TripSummaryDTO>();
        for(Trip trip : trips_model){
            TripSummaryDTO tripSummaryDTO = TripUtils.tripSummaryDTOFromModel(trip);
            trips.add(tripSummaryDTO);
        }
        return trips;
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
    public ArrayList<TripSummaryDTO> getWishlist(String username, int size, int page) {

        ArrayList<TripSummaryDTO> trips = new ArrayList<TripSummaryDTO>();

        for(Trip trip : wishlistRedisDAO.getUserWishlist(username, size, page)){
            trips.add(TripUtils.tripSummaryDTOFromModel(trip));
        }

        return trips;
    }


    public List<TripSummaryDTO> getTripsByDestination(String destination, String departureDate, String returnDate, int size, int page) {

        LocalDate depDate;
        LocalDate retDate = null;
        try{
            depDate = LocalDate.parse(departureDate);
            retDate = LocalDate.parse(returnDate);
        }catch (DateTimeParseException e){
            depDate = LocalDate.now();
        }

        List<Trip> trips= tripDetailsDAO.getTripsByDestination(destination, depDate, retDate, size, page);
        List<TripSummaryDTO> tripsDTO = new ArrayList<>();
        for(Trip t : trips){
            TripSummaryDTO tDTO = TripUtils.tripSummaryDTOFromModel(t);
            tripsDTO.add(tDTO);
        }
        return tripsDTO;

    }

    @Override
    public List<TripSummaryDTO> getTripsByTag(String tag, String departureDate, String returnDate, int size, int page) {

        LocalDate depDate;
        LocalDate retDate = null;
        try{
            depDate = LocalDate.parse(departureDate);
            retDate = LocalDate.parse(returnDate);
        }catch (DateTimeParseException e){
            depDate = LocalDate.now();
        }

        List<Trip> trips= tripDetailsDAO.getTripsByTag(tag, depDate, retDate, size, page);
        List<TripSummaryDTO> tripsDTO = new ArrayList<>();
        for(Trip t : trips){
            TripSummaryDTO tDTO = TripUtils.tripSummaryDTOFromModel(t);
            tripsDTO.add(tDTO);
        }
        return tripsDTO;
    }

    @Override
    public List<TripSummaryDTO> getTripsByPrice(int min_price, int max_price, String departureDate, String returnDate, int size, int page) {

        LocalDate depDate;
        LocalDate retDate = null;
        try{
            depDate = LocalDate.parse(departureDate);
            retDate = LocalDate.parse(returnDate);
        }catch (DateTimeParseException e){
            depDate = LocalDate.now();
        }

        List<Trip> trips= tripDetailsDAO.getTripsByPrice(min_price, max_price, depDate, retDate, size, page);
        List<TripSummaryDTO> tripsDTO = new ArrayList<>();
        for(Trip t : trips){
            TripSummaryDTO tDTO = TripUtils.tripSummaryDTOFromModel(t);
            tripsDTO.add(tDTO);
        }
        return tripsDTO;
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
        } catch (Exception e) {
            depDate = new Date();
        }
        List<Trip> trips = tripDetailsDAO.cheapestTripForDestinationInPeriod(depDate,retDate,page, objectPerPageSearch);
        List<TripSummaryDTO> tripsDTO = new ArrayList<>();
        for(Trip t : trips){
            TripSummaryDTO tDTO = TripUtils.tripSummaryDTOFromModel(t);
            tripsDTO.add(tDTO);
        }
        return tripsDTO;
    }

    @Override
    public List<TripSummaryDTO> getSuggestedTrips(String username, int numTrips) {
        List<Trip> trips_model = tripDAO.getSuggestedTrip(username, numTrips);
        List<TripSummaryDTO> trips = new ArrayList<>();
        for(Trip t : trips_model){
            TripSummaryDTO tripSummaryDTO = TripUtils.tripSummaryDTOFromModel(t);
            trips.add(tripSummaryDTO);
        }
        return trips;
    }

    @Override
    public boolean addTrip(TripDetailsDTO tripDetailsDTO){
        Trip t = TripUtils.tripModelFromTripDetailsDTO(tripDetailsDTO);
        if(t.getDepartureDate().isAfter(LocalDate.now()) || t.getDepartureDate().isAfter(t.getReturnDate())){
            return false;
        }
        String id = tripDetailsDAO.addTrip(t);
        if(id!=null){
            t.setId(id);
            try {
                RegisteredUser r = new RegisteredUser();
                r.setUsername(t.getOrganizer().getUsername());
                tripDAO.addTrip(t,r);
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
    public boolean deleteTrip(TripDetailsDTO t) {
        if (LocalDate.now().isBefore(t.getDepartureDate())) {
            try {
                Trip trip = TripUtils.tripModelFromTripDetailsDTO(t);
                tripDAO.deleteTrip(trip);
                if (tripDetailsDAO.deleteTrip(trip)) {
                    return true;
                } else {
                    tripDAO.setNotDeleted(trip);
                }
            } catch (Neo4jException e) {
                System.err.println("Errore neo4j");
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean updateTrip(TripDetailsDTO newTrip, TripDetailsDTO oldTrip){
        if(newTrip.getId()==oldTrip.getId()) {
            Trip n_trip = TripUtils.tripModelFromTripDetailsDTO(newTrip);
            Trip o_trip = TripUtils.tripModelFromTripDetailsDTO(oldTrip);
            boolean flag = DAOLocator.getTripDetailsDAO().updateTrip(n_trip,o_trip);
            if(flag){
                try {
                    DAOLocator.getTripDAO().updateTrip(n_trip,o_trip);
                    return true;
                } catch (Neo4jException e) {
                    //logger errore neo4j
                    return false;
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public List<TripSummaryDTO> mostPopularTrips(int tripNumberIndex) {
        List<Trip> trips = tripDetailsDAO.mostPopularTrips(tripNumberIndex);
        List<TripSummaryDTO> ret = new ArrayList<>();
        for(Trip t : trips){
            ret.add(TripUtils.tripSummaryDTOFromModel(t));
        }
        return ret;
    }

    @Override
    public InvolvedPeopleDTO getOrganizerAndJoiners(String id){
        if(id!=null) {
            Trip t = new Trip();
            t.setId(id);
            t = tripDAO.getJoinersAndOrganizer(t);
            if(t != null) {
                InvolvedPeopleDTO inv = new InvolvedPeopleDTO();
                inv.setOrganizer(t.getOrganizer().getUsername());
                List<Pair<RegisteredUser, Status>> joiners = t.getJoiners();
                for (Pair<RegisteredUser, Status> x : joiners) {
                    OtherUserDTO o = new OtherUserDTO();
                    o.setUsername(x.getValue0().getUsername());
                    o.setPic(x.getValue0().getProfile_pic());
                    Pair<OtherUserDTO, Status> j = new Pair<>(o, x.getValue1());
                    inv.addJoiners(j);
                }
                return inv;
            }
        }
        return null;
    }

    @Override
    public boolean manageTripRequest(String id, String username, String action) {
            try{
                Trip t = new Trip();
                t.setId(id);
                RegisteredUser r = new RegisteredUser(username);
                if(action.equals("delete")) {
                    tripDAO.removeJoin(t,r);
                }else{
                    if(action.equals("accept"))
                        tripDAO.setStatusJoin(t,r,Status.accepted);
                    else if(action.equals("reject"))
                        tripDAO.setStatusJoin(t,r,Status.rejected);
                    else
                        return false;
                }
            }catch (Neo4jException exc){
                return false;
            }
            return true;
    }

    @Override
    public String setJoin(String username, String trip_id){
        return manageJoin(username, trip_id, "set");
    }
    @Override
    public String cancelJoin(String username, String trip_id){
        return manageJoin(username, trip_id, "cancel");
    }

    private String manageJoin(String username, String trip_id, String action){
        if(trip_id!=null && username!= null) {
            Trip t = new Trip();
            t.setId(trip_id);
            RegisteredUser r = new RegisteredUser(username);
            try{
                if(action.equals("set"))
                    tripDAO.createJoin(t,r);
                else
                    tripDAO.cancelJoin(t,r);
                return "OK";
            }catch(Neo4jException ne){
                return "Error";
            }
        }else{
            return "Error";
        }
    }

    @Override
    public String getJoinStatus(String trip_id, String username) {
        if(trip_id!=null && username!=null) {
            Trip t = new Trip();
            t.setId(trip_id);
            try {
                Status status = tripDAO.getJoinStatus(t, new RegisteredUser(username));
                try {
                    return status.name();
                } catch (NullPointerException ne) {
                    return null;
                }
            }catch (Neo4jException neo4jException){
                return null;
            }
        }
        return null;
    }
}
