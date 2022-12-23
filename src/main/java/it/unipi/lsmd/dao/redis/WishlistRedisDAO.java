package it.unipi.lsmd.dao.redis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unipi.lsmd.dao.WishlistDAO;
import it.unipi.lsmd.dao.base.BaseDAORedis;
import it.unipi.lsmd.model.Trip;
import it.unipi.lsmd.model.Wishlist;
import it.unipi.lsmd.utils.LocalDateAdapter;
import it.unipi.lsmd.utils.TripUtils;
import org.json.JSONObject;
import redis.clients.jedis.Jedis;

import javax.print.attribute.standard.JobKOctets;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static java.lang.Math.abs;

public class WishlistRedisDAO extends BaseDAORedis implements WishlistDAO {

    public static final String REDIS_APP_NAMESPACE = "trip4share";


    @Override
    public void addToWishlist(String username, String trip_id, Trip trip) {

        long ttl = abs(ChronoUnit.DAYS.between(trip.getDepartureDate(), LocalDate.now())) * 86400; // seconds per day
        String key = REDIS_APP_NAMESPACE + ":" + username + ":" + trip_id;

        try(Jedis jedis = getConnection()){
            jedis.set(key, TripUtils.tripToJSONString(trip));
            jedis.expire(key, ttl);
        }
    }

    @Override
    public void removeFromWishlist(String username, String trip_id) {
        String key = REDIS_APP_NAMESPACE + ":" + username + ":" + trip_id;
        try(Jedis jedis = getConnection()){
            jedis.del(key);
        }
    }

    @Override
    public ArrayList<Trip> getUserWishlist(String username) {
        ArrayList<Trip> trips = new ArrayList<Trip>();

        try(Jedis jedis = getConnection()){
            String key = REDIS_APP_NAMESPACE + ":" + username + "*";
            Set<String> keys = jedis.keys(key);
            for(String k : keys){

                String raw_trip = jedis.get(k);

                trips.add(TripUtils.tripFromJSONString(raw_trip));
            }
        }
        return trips;
    }
}
