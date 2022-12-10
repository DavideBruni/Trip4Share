package it.unipi.dii.lsmd.backend.config;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Config {

    private static String redisHost;
    private static int redisPort;
    private String mongoHost;
    private int mongoPort;

    Config(){}

    public String getRedisHost() {
        return redisHost;
    }

    public int getRedisPort() {
        return redisPort;
    }

    public String getMongoHost() {
        return mongoHost;
    }

    public int getMongoPort() {
        return mongoPort;
    }
}
