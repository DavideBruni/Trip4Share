package it.unipi.lsmd.service.impl;

import it.unipi.lsmd.controller.AddAdminServlet;
import it.unipi.lsmd.dao.mongo.WishlistMongoDAO;
import it.unipi.lsmd.dao.redis.WishlistRedisDAO;
import it.unipi.lsmd.dto.TripSummaryDTO;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Trip;
import it.unipi.lsmd.model.Wishlist;
import it.unipi.lsmd.service.WishlistService;
import it.unipi.lsmd.utils.TripUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class WishlistServiceImpl implements WishlistService {

    private final WishlistRedisDAO wishlistRedisDAO;
    private final WishlistMongoDAO wishlistMongoDAO;
    private static Logger logger = LoggerFactory.getLogger(WishlistServiceImpl.class);


    public WishlistServiceImpl() {
        wishlistRedisDAO = new WishlistRedisDAO();
        wishlistMongoDAO = new WishlistMongoDAO();
    }

    @Override
    public void addToWishlist(String username, String trip_id, TripSummaryDTO tripSummary) {

        if(username == null || username.equals("") || trip_id == null || trip_id.equals(""))
            return;

        if(wishlistRedisDAO.addToWishlist(new RegisteredUser(username), tripSummary)){
            Trip trip = new Trip();
            trip.setId(trip_id);
            wishlistMongoDAO.addToWishlist(trip);
        }
    }

    @Override
    public void removeFromWishlist(String username, String trip_id) {

        if(username == null || username.equals("") || trip_id == null || trip_id.equals(""))
            return;

        Trip trip = new Trip();
        trip.setId(trip_id);

        if(wishlistRedisDAO.removeFromWishlist(new RegisteredUser(username), trip)){
            wishlistMongoDAO.removeFromWishlist(trip);
        }
    }

    @Override
    public ArrayList<TripSummaryDTO> getWishlist(String username, int size, int page) {

        if(username == null || username.equals(""))
            return null;

        ArrayList<TripSummaryDTO> trips = new ArrayList<>();
        Wishlist wishlist = wishlistRedisDAO.getUserWishlist(new RegisteredUser(username), size, page);

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
    public LocalDateTime wishlistUpdateTime(String username, String trip_id) {

        if(username == null || username.equals("") || trip_id == null || trip_id.equals(""))
            return null;

        Trip trip = new Trip();
        trip.setId(trip_id);

        return wishlistRedisDAO.getUpdateTime(new RegisteredUser(username), trip);
    }
}
