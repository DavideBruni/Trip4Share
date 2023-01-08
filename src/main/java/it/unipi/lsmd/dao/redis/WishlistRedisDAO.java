package it.unipi.lsmd.dao.redis;

import it.unipi.lsmd.dao.WishlistDAO;
import it.unipi.lsmd.dao.base.BaseDAORedis;
import it.unipi.lsmd.dto.TripSummaryDTO;
import it.unipi.lsmd.dto.TripWishlistDTO;
import it.unipi.lsmd.model.RegisteredUser;
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
    public boolean addToWishlist(RegisteredUser user, String trip_id, TripWishlistDTO trip) {

        long ttl = abs(ChronoUnit.DAYS.between(trip.getDepartureDate(), LocalDate.now())) * 86400; // seconds per day
        String key = REDIS_APP_NAMESPACE + ":" + user.getUsername() + ":" + trip_id;

        try(Jedis jedis = getConnection()){

            if(jedis.get(key) != null){
                return false;
            }

            jedis.set(key, TripUtils.tripToJSONString(trip));
            jedis.expire(key, ttl);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean removeFromWishlist(RegisteredUser user, Trip trip) {
        String key = REDIS_APP_NAMESPACE + ":" + user.getUsername() + ":" + trip.getId();
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
    public Wishlist getUserWishlist(RegisteredUser user, int size, int page) {

        Wishlist wishlist = new Wishlist();

        try(Jedis jedis = getConnection()){
            String key = REDIS_APP_NAMESPACE + ":" + user.getUsername() + ":*";
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
                TripWishlistDTO tripWishlistDTO = TripUtils.tripFromJSONString(raw_trip);
                Trip trip = TripUtils.tripFromTripWishlist(tripWishlistDTO);
                trip.setId(k.split(":")[2]);
                wishlist.addToWishlist(trip);
                i++;
            }
        }catch (Exception e){
            return null;
        }
        return wishlist;
    }

    @Override
    public LocalDateTime getUpdateTime(RegisteredUser user, Trip trip) {
        try(Jedis jedis = getConnection()){
            String key = REDIS_APP_NAMESPACE + ":" + user.getUsername() + ":" + trip.getId();
            String raw_trip = jedis.get(key);
            if(raw_trip == null)
                return null;
            return TripUtils.tripFromJSONString(raw_trip).getLast_modified();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public boolean flushWishlist(RegisteredUser user) {
        // TODO - non testata
        String key = REDIS_APP_NAMESPACE + ":" + user.getUsername() + ":*";
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
