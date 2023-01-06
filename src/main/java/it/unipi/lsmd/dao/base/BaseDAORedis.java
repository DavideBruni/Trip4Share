package it.unipi.lsmd.dao.base;

import it.unipi.lsmd.dao.exceptions.ConnectionToDatabaseException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

public abstract class BaseDAORedis {

    private static final String REDIS_HOST = "172.16.5.22";
    private static final Integer REDIS_PORT = 6379;
    private static JedisPool pool;

    public static void initPool(){
        pool = new JedisPool(REDIS_HOST, REDIS_PORT);
    }

    public Jedis getConnection() throws ConnectionToDatabaseException {
        try {
            Jedis connection = pool.getResource();
            connection.auth("Trip4Share");
            return connection;
        }catch(JedisConnectionException je){
            throw new ConnectionToDatabaseException();
        }
    }

    public static void closePool(){
        if (!pool.isClosed()){
            pool.close();
        }
    }

}
