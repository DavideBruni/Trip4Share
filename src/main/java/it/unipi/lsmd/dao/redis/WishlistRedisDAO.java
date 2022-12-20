package it.unipi.lsmd.dao.redis;

import it.unipi.lsmd.dao.WishlistDAO;
import it.unipi.lsmd.dao.base.BaseDAORedis;
import it.unipi.lsmd.model.Trip;
import it.unipi.lsmd.model.Wishlist;
import org.json.JSONObject;
import redis.clients.jedis.Jedis;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static java.lang.Math.abs;

public class WishlistRedisDAO extends BaseDAORedis implements WishlistDAO {

    public static final String REDIS_APP_NAMESPACE = "trip4share";

    private String getShoppingCartKeyInNS(String username){
        return REDIS_APP_NAMESPACE + ":" + username;
    }


    @Override
    public void addToWishlist(String user_id, String trip_id, HashMap<String, Object> data) {

        LocalDate departure_date = (LocalDate) data.get("departure_date");
        long ttl = abs(ChronoUnit.DAYS.between(departure_date, LocalDate.now())) * 86400; // seconds per day
        System.out.println(ttl);

        String key = user_id+":"+trip_id;

        try(Jedis jedis = getConnection()){
            // TODO - save a TripHomeDTO object
            JSONObject json = new JSONObject(data);

            jedis.set(key, String.valueOf(json));
            jedis.expire(key, ttl);
        }

    }

    @Override
    public void removeFromWishlist(String user_id, String trip_id) {
        String key = user_id+":"+trip_id;
        try(Jedis jedis = getConnection()){
            jedis.del(key);
        }
    }

    @Override
    public ArrayList<Trip> viewUserWishlist(String user_id) {
        ArrayList<Trip> trips = new ArrayList<Trip>();

        try(Jedis jedis = getConnection()){
            Set<String> keys = jedis.keys(user_id+":*");
            for(String trip : keys){
                System.out.println(trip);
                System.out.println(jedis.get(trip));
            }
        }

        return trips;
    }
}
