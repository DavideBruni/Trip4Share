package it.unipi.lsmd.dao.redis;

import it.unipi.lsmd.dao.WishlistDAO;
import it.unipi.lsmd.dao.base.BaseDAORedis;
import it.unipi.lsmd.dto.TripSummaryDTO;
import it.unipi.lsmd.model.Trip;
import it.unipi.lsmd.model.Wishlist;
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
    public boolean addToWishlist(String username, String trip_id, TripSummaryDTO trip) {

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
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean removeFromWishlist(String username, String trip_id) {
        String key = REDIS_APP_NAMESPACE + ":" + username + ":" + trip_id;
        try(Jedis jedis = getConnection()){

            if(jedis.get(key) == null){
                return false;
            }

            jedis.del(key);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Wishlist getUserWishlist(String username, int size, int page) {

        Wishlist wishlist = new Wishlist();


        try(Jedis jedis = getConnection()){
            String key = REDIS_APP_NAMESPACE + ":" + username + ":*";
            Set<String> keys = jedis.keys(key);
            int i = 0;
            int start_index = (page-1) * size;
            int end_index = start_index + size + 1;
            for(String k : keys){
                if(i < start_index){
                    i++;
                    continue;
                }
                if(i >= end_index)
                    break;

                String raw_trip = jedis.get(k);
                TripSummaryDTO tripSummaryDTO = TripUtils.tripFromJSONString(raw_trip);
                wishlist.addToWishlist(TripUtils.tripFromTripSummary(tripSummaryDTO));
                i++;
            }
        }catch (Exception e){
            return null;
        }
        return wishlist;
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
    public boolean flushWishlist(String username) {
        // TODO - non testata
        String key = REDIS_APP_NAMESPACE + ":" + username + ":*";
        try(Jedis jedis = getConnection()){
            Set<String> keys = jedis.keys(key);
            for(String k : keys){
                jedis.del(k);
            }
        }catch (Exception e){
            return false;
        }
        return true;

    }
}
