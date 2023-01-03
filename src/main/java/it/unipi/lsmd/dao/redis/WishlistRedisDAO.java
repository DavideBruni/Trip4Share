package it.unipi.lsmd.dao.redis;

import it.unipi.lsmd.dao.WishlistDAO;
import it.unipi.lsmd.dao.base.BaseDAORedis;
import it.unipi.lsmd.model.Trip;
import it.unipi.lsmd.utils.TripUtils;
import redis.clients.jedis.Jedis;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Set;

import static java.lang.Math.abs;

public class WishlistRedisDAO extends BaseDAORedis implements WishlistDAO {

    public static final String REDIS_APP_NAMESPACE = "trip4share";


    @Override
    public Boolean addToWishlist(String username, String trip_id, Trip trip) {

        long ttl = abs(ChronoUnit.DAYS.between(trip.getDepartureDate(), LocalDate.now())) * 86400; // seconds per day
        String key = REDIS_APP_NAMESPACE + ":" + username + ":" + trip_id;

        try(Jedis jedis = getConnection()){

            if(jedis.get(key) != null){
                return false;
            }
            System.out.println(trip);
            jedis.set(key, TripUtils.tripToJSONString(trip));
            jedis.expire(key, ttl);
            return true;
        }
    }

    @Override
    public Boolean removeFromWishlist(String username, String trip_id) {
        String key = REDIS_APP_NAMESPACE + ":" + username + ":" + trip_id;
        try(Jedis jedis = getConnection()){

            if(jedis.get(key) == null){
                return false;
            }

            jedis.del(key);
            return true;
        }
    }

    @Override
    public ArrayList<Trip> getUserWishlist(String username, int size, int page) {
        ArrayList<Trip> trips = new ArrayList<Trip>();

        try(Jedis jedis = getConnection()){
            String key = REDIS_APP_NAMESPACE + ":" + username + ":*";
            Set<String> keys = jedis.keys(key);
            int i = 0;
            int start_index = (page-1) * (size - 1);
            int end_index = start_index + size;
            for(String k : keys){
                if(i < start_index){
                    i++;
                    continue;
                }
                if(i >= end_index)
                    break;

                System.out.println(k);
                String raw_trip = jedis.get(k);
                trips.add(TripUtils.tripFromJSONString(raw_trip));
                i++;
            }
        }
        return trips;
    }

    @Override
    public LocalDateTime getUpdateTime(String username, String trip_id) {
        try(Jedis jedis = getConnection()){
            String key = REDIS_APP_NAMESPACE + ":" + username + ":" + trip_id;
            String raw_trip = jedis.get(key);
            if(raw_trip == null)
                return null;
            return TripUtils.tripFromJSONString(raw_trip).getLast_modified();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public void flushWishlist(String username) {
        // TODO - non testata
        String key = REDIS_APP_NAMESPACE + ":" + username + ":*";
        try(Jedis jedis = getConnection()){
            Set<String> keys = jedis.keys(key);
            for(String k : keys){
                jedis.del(k);
            }
        }

    }
}
