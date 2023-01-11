package it.unipi.lsmd.service.impl;

import it.unipi.lsmd.dao.DAOLocator;
import it.unipi.lsmd.dao.WishlistDAO;
import it.unipi.lsmd.dao.mongo.WishlistMongoDAO;
import it.unipi.lsmd.dao.neo4j.exceptions.Neo4jException;
import it.unipi.lsmd.dto.TripDetailsDTO;
import it.unipi.lsmd.dto.TripSummaryDTO;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Trip;
import it.unipi.lsmd.model.Wishlist;
import it.unipi.lsmd.service.WishlistService;
import it.unipi.lsmd.utils.TripUtils;

import java.util.ArrayList;

public class WishlistServiceImpl implements WishlistService {

    private final WishlistDAO wishlistDAO;
    private final WishlistMongoDAO wishlistMongoDAO;


    public WishlistServiceImpl() {
        wishlistDAO = DAOLocator.getWishlistDAO();
        wishlistMongoDAO = new WishlistMongoDAO();
    }

    @Override
    public boolean addToWishlist(String username, TripDetailsDTO tripDetailsDTO) {

        if(username == null || username.equals("") || tripDetailsDTO==null)
            return false;

        Trip trip = TripUtils.tripModelFromTripDetailsDTO(tripDetailsDTO);
        try {
            if(wishlistDAO.addToWishlist(new RegisteredUser(username), trip)){
                if(wishlistMongoDAO.addToWishlist(trip))
                    return true;
                else {
                    wishlistDAO.removeFromWishlist(new RegisteredUser(username), trip);
                }
            }
            return false;
        } catch (Neo4jException e) {
            return false;
        }
    }

    @Override
    public boolean removeFromWishlist(String username, TripDetailsDTO tripDetailsDTO) {

        if(username == null || username.equals("") || tripDetailsDTO==null)
            return false;

        Trip trip = TripUtils.tripModelFromTripDetailsDTO(tripDetailsDTO);
        try {
            if(wishlistDAO.removeFromWishlist(new RegisteredUser(username), trip)){
                if(wishlistMongoDAO.removeFromWishlist(trip))
                    return true;
                else{
                    wishlistDAO.addToWishlist(new RegisteredUser(username), trip);
                }
            }
            return false;
        } catch (Neo4jException e) {
            return false;
        }
    }

    @Override
    public boolean removeFromWishlist(String username, String trip_id) {
        return removeFromWishlist(username,new TripDetailsDTO(trip_id));
    }

    @Override
    public ArrayList<TripSummaryDTO> getWishlist(String username, int size, int page) {

        if(username == null || username.equals(""))
            return null;

        ArrayList<TripSummaryDTO> trips = new ArrayList<>();
        Wishlist wishlist = wishlistDAO.getUserWishlist(new RegisteredUser(username), size + 1, page);

        try{
            for(Trip trip : wishlist.getWishlist()){
                trips.add(TripUtils.tripSummaryDTOFromModel(trip));
            }
        }catch (NullPointerException e){
            trips = null;
        }

        return trips;
    }

    @Override
    public boolean isInWishlist(String username, TripDetailsDTO tripDetailsDTO) {
        if(username == null || username.equals("") || tripDetailsDTO==null)
            return false;

        Trip trip = TripUtils.tripModelFromTripDetailsDTO(tripDetailsDTO);
        try {
            return wishlistDAO.isInWishlist(new RegisteredUser(username), trip);
        } catch (Neo4jException e) {
            return false;
        }
    }
}
