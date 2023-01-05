package it.unipi.lsmd.service.impl;

import it.unipi.lsmd.controller.AddAdminServlet;
import it.unipi.lsmd.dao.DAOLocator;
import it.unipi.lsmd.dao.TripDAO;
import it.unipi.lsmd.dao.TripDetailsDAO;
import it.unipi.lsmd.dao.mongo.WishlistMongoDAO;
import it.unipi.lsmd.dao.neo4j.TripNeo4jDAO;
import it.unipi.lsmd.dao.neo4j.exceptions.Neo4jException;
import it.unipi.lsmd.dao.redis.WishlistRedisDAO;
import it.unipi.lsmd.dto.*;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Tag;
import it.unipi.lsmd.model.Trip;
import it.unipi.lsmd.model.enums.Status;
import it.unipi.lsmd.service.TripService;
import it.unipi.lsmd.utils.TripUtils;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.exceptions.JedisConnectionException;
import org.javatuples.Triplet;

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
    private final TripNeo4jDAO organizerNeoDAO;
    private static Logger logger = LoggerFactory.getLogger(TripServiceImpl.class);


    public TripServiceImpl(){
        tripDetailsDAO = DAOLocator.getTripDetailsDAO();
        tripDAO = DAOLocator.getTripDAO();
        organizerNeoDAO = new TripNeo4jDAO();
    }

    @Override
    public List<TripSummaryDTO> getTripsOrganizedByFollowers(String username, int size, int page) {
        List<Trip> trips = tripDAO.getTripsOrganizedByFollower(username, size, page);
        if(trips == null || trips.isEmpty()){
            logger.error("Error. No trip found");
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
        if(trip == null){
            logger.error("Error. No trip found");
            return null;
        }
        try {
            trip.setOrganizer(organizerNeoDAO.getOrganizer(trip));
        } catch (Neo4jException e) {
            System.out.println(e);
            trip.setOrganizer(null);
        }
        return TripUtils.tripModelToDetailedDTO(trip);
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
    public List<TripSummaryDTO> getTripsByTag(String value, String departureDate, String returnDate, int size, int page) {

        LocalDate depDate;
        LocalDate retDate = null;
        try{
            depDate = LocalDate.parse(departureDate);
            retDate = LocalDate.parse(returnDate);
        }catch (DateTimeParseException e){
            depDate = LocalDate.now();
        }
        Tag tag = new Tag(value);
        List<Trip> trips= tripDetailsDAO.getTripsByTag(tag, depDate, retDate, size, page);
        List<TripSummaryDTO> tripsDTO = new ArrayList<>();
        for(Trip t : trips){
            TripSummaryDTO tDTO = TripUtils.tripSummaryDTOFromModel(t);
            tripsDTO.add(tDTO);
        }
        return tripsDTO;
    }

    @Override
    public List<TripSummaryDTO> getTripsByPrice(double min_price, double max_price, String departureDate, String returnDate, int size, int page) {

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
    public List<DestinationsDTO> mostPopularDestinations(int limit) {
        List<Pair<String, Integer>> trips= tripDetailsDAO.mostPopularDestinations(limit);
        List<DestinationsDTO> res = new ArrayList<>();
        for(Pair<String, Integer> x : trips){
            res.add(new DestinationsDTO(x.getValue0(),x.getValue1()));
        }
        return res;
    }

    @Override
    public List<DestinationsDTO> mostPopularDestinationsByTag(String tag, int limit) {
        List<Pair<String, Integer>> trips= tripDetailsDAO.mostPopularDestinationsByTag(tag, limit);
        List<DestinationsDTO> res = new ArrayList<>();
        for(Pair<String, Integer> x : trips){
            res.add(new DestinationsDTO(x.getValue0(),x.getValue1()));
        }
        return res;
    }

    @Override
    public List<DestinationsDTO> mostPopularDestinationsByPrice(double start, double end, int limit) {
        List<Pair<String, Integer>> trips= tripDetailsDAO.mostPopularDestinationsByPrice(start, end, limit);
        List<DestinationsDTO> res = new ArrayList<>();
        for(Pair<String, Integer> x : trips){
            res.add(new DestinationsDTO(x.getValue0(),x.getValue1()));
        }
        return res;
    }

    @Override
    public List<DestinationsDTO> mostPopularDestinationsByPeriod(String departureDate, String returnDate, int limit) {
        LocalDate depDate;
        LocalDate retDate;
        List<Pair<String, Integer>> mostPop = new ArrayList<>();
        List<DestinationsDTO> result = new ArrayList<>();
        try{
            depDate = LocalDate.parse(departureDate);
            retDate = LocalDate.parse(returnDate);
            mostPop = tripDetailsDAO.mostPopularDestinationsByPeriod(depDate, retDate, limit);
        }catch (DateTimeParseException e){
            System.out.println("No date available");
            mostPop = tripDetailsDAO.mostPopularDestinations(limit);
        }catch (Exception e){
            return null;
        }finally {
            for(Pair<String, Integer> x : mostPop){
                result.add(new DestinationsDTO(x.getValue0(),x.getValue1()));
            }
        }
        return result;

    }

    @Override
    public List<PriceDestinationDTO> cheapestDestinationsByAvg(int objectPerPageSearch) {
        List<Trip> trips = tripDetailsDAO.cheapestDestinationsByAvg(objectPerPageSearch);
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
        LocalDate depDate;
        LocalDate retDate;
        try {
            depDate =LocalDate.parse(start);
            try{
                retDate = LocalDate.parse(end);
            }catch (Exception ex){
                retDate=null;
            }
        } catch (Exception e) {
            depDate = LocalDate.now();
            retDate=null;
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
        if(t.getDepartureDate().isBefore(LocalDate.now()) || t.getDepartureDate().isAfter(t.getReturnDate())){
            logger.error("Error. Invalid dates");
            return false;
        }
        String id = tripDetailsDAO.addTrip(t);
        if(id!=null){
            t.setId(id);
            try {
                RegisteredUser r = new RegisteredUser();
                r.setUsername(t.getOrganizer().getUsername());
                tripDAO.addTrip(t,r);
                logger.info("New Trip added: " + t);
                return true;
            } catch (Neo4jException e) {
                logger.error("Error. Error while adding new Trip on Neo4J " + e);
                if(!tripDetailsDAO.deleteTrip(t)){
                    logger.error("Error. Error while rollback new Trip on MongoDB");
                }
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean deleteTrip(String id) {
        Trip trip = tripDetailsDAO.getTrip(id);
        if(trip!= null && LocalDate.now().isBefore(trip.getDepartureDate())){

            try {
                tripDAO.deleteTrip(trip);
                if (tripDetailsDAO.deleteTrip(trip)) {
                    return true;
                } else {
                    tripDAO.setNotDeleted(trip);
                }
            } catch (Neo4jException e) {
                logger.error("Error. Error while deleting Trip on Neo4J " + e);
                return false;
            }
            logger.info("Trip deleted: " + trip);
            return true;
        }
        logger.error("Error. Invalid trip");
        return false;
    }

    @Override
    public boolean updateTrip(TripDetailsDTO newTrip, TripDetailsDTO oldTrip){
        if(newTrip.getId()==oldTrip.getId()) {
            Trip n_trip = TripUtils.tripModelFromTripDetailsDTO(newTrip);
            Trip o_trip = TripUtils.tripModelFromTripDetailsDTO(oldTrip);
            boolean flag = tripDetailsDAO.updateTrip(n_trip,o_trip);
            if(flag){
                try {
                    tripDAO.updateTrip(n_trip,o_trip);
                    logger.info("Trip updated: " + n_trip);
                    return true;
                } catch (Neo4jException e) {
                    logger.error("Error. Error while updating Trip on Neo4J " + e);
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
                    if(action.equals("accept")){
                        logger.info("User " + r.getUsername() + " has been accepted to join trip " + t.getId());
                        tripDAO.setStatusJoin(t,r,Status.accepted);
                    }
                    else if(action.equals("reject")){
                        logger.info("User " + r.getUsername() + " has been rejected to join trip " + t.getId());
                        tripDAO.setStatusJoin(t,r,Status.rejected);
                    }
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
                if(action.equals("set")){
                    tripDAO.createJoin(t,r);
                    logger.info("User " + username + " joined trip " + trip_id + " correctly");
                }
                else{
                    tripDAO.cancelJoin(t,r);
                    logger.info("User " + username + " cancelled join request from trip " + trip_id + " correctly");
                }
                return "OK";
            }catch(Neo4jException ne){
                logger.error("Error on setJoin/cancelJoin " + ne);
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
                    logger.error("Error. Status not found" + ne);
                    return null;
                }
            }catch (Neo4jException neo4jException){
                logger.error("Error while getting join status " + neo4jException);
                return null;
            }
        }
        logger.error("Error. Invalid username or trip_id");
        return null;
    }

    @Override
    public List<DestinationsDTO> mostPopularTripsOverall(int suggestionsExplore) {
        List<Triplet<String, Integer, Integer>> dests = tripDetailsDAO.mostPopularDestinationsOverall(suggestionsExplore);
        List<DestinationsDTO> destinationsDTOS = new ArrayList<>();
        for(Triplet<String, Integer, Integer> t : dests){
            destinationsDTOS.add(new DestinationsDTO(t.getValue0(),t.getValue1(),t.getValue2()));
        }
        return destinationsDTOS;
    }

    @Override
    public List<DestinationsDTO> mostExclusive(int suggestionsExplore) {
        List<Triplet<String, Integer, Integer>> dests = tripDetailsDAO.mostExclusive(suggestionsExplore);
        List<DestinationsDTO> destinationsDTOS = new ArrayList<>();
        for(Triplet<String, Integer, Integer> t : dests){
            destinationsDTOS.add(new DestinationsDTO(t.getValue0(),t.getValue1(),t.getValue2()));
        }
        return destinationsDTOS;
    }
}
