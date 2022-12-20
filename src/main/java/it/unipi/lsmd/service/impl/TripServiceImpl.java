package it.unipi.lsmd.service.impl;

import it.unipi.lsmd.dao.DAOLocator;
import it.unipi.lsmd.dao.TripDAO;
import it.unipi.lsmd.dao.WishlistDAO;
import it.unipi.lsmd.dao.neo4j.TripDAONeo4j;
import it.unipi.lsmd.dto.TripDetailsDTO;
import it.unipi.lsmd.dto.TripSummaryDTO;
import it.unipi.lsmd.model.Trip;
import it.unipi.lsmd.service.TripService;
import it.unipi.lsmd.utils.TripUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TripServiceImpl implements TripService {

    private TripDAO tripDAO;
    private WishlistDAO wishlistDAO;

    public TripServiceImpl(){
        tripDAO = DAOLocator.getTripDAO();
        wishlistDAO = DAOLocator.getWishlistDAO();
    }

    @Override
    public List<TripSummaryDTO> getTripsOrganizedByFollowers(String username) {
        TripDAONeo4j tripDAONeo4j = new TripDAONeo4j();
        List<Trip> trips = tripDAONeo4j.getTripsOrganizedByFollower(username);
        if(trips == null || trips.isEmpty()){
            return new ArrayList<TripSummaryDTO>();
        }
        List<TripSummaryDTO> tripsDTO = new ArrayList<>();
        for(Trip t : trips){
            TripSummaryDTO tripHomeDTO = new TripSummaryDTO();
            tripHomeDTO.setDestination(t.getDestination());
            tripHomeDTO.setDeleted(t.getDeleted());
            tripHomeDTO.setDepartureDate(t.getDepartureDate());
            tripHomeDTO.setReturnDate(t.getReturnDate());
            tripHomeDTO.setTitle(t.getTitle());
            tripHomeDTO.setImgUrl(t.getImg());

            tripsDTO.add(tripHomeDTO);
        }
        return tripsDTO;
    }

    public TripDetailsDTO getTrip(String id){
        Trip trip = tripDAO.getTrip(id);
        return TripUtils.tripModelToDetailedDTO(trip);
    }

    @Override
    public void addToWishlist(String username, String trip_id, HashMap<String, Object> data){
        wishlistDAO.addToWishlist(username, trip_id, data);
    }

    @Override
    public void removeFromWishlist(String username, String trip_id) {
        wishlistDAO.removeFromWishlist(username, trip_id);
    }

    @Override
    public ArrayList<TripDetailsDTO> getWishlist(String username) {
        // TODO cast Trip to TripDTO
        wishlistDAO.viewUserWishlist(username);
        return null;
    }
}
