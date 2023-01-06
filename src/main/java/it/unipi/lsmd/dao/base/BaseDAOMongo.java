package it.unipi.lsmd.dao.base;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public abstract class BaseDAOMongo {

    private static final String MONGO_HOST = "172.16.5.20";
    private static final Integer MONGO_PORT = 27017;
    private static final String MONGO_DB = "Trip4Share";
    private static MongoClient client = null;

    public static void init(){
        String url = "mongodb://"+MONGO_HOST+":"+MONGO_PORT;
        ConnectionString uri = new ConnectionString(url);
        MongoClientSettings mcs = MongoClientSettings.builder()
                .applyConnectionString(uri).readPreference(ReadPreference.nearest())
                .retryWrites(true).writeConcern(WriteConcern.ACKNOWLEDGED).build();

        client = MongoClients.create(mcs);
        System.out.println("MongoDB: Connected");
    }

    public static MongoDatabase getConnection(){
        return client.getDatabase(MONGO_DB);
    }


}
