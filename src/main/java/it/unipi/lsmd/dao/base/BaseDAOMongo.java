package it.unipi.lsmd.dao.base;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public abstract class BaseDAOMongo {

    private static final String MONGO_HOST = "localhost";
    private static final Integer MONGO_PORT = 27017;
    private static final String MONGO_DB = "Trip4Share";
    private static MongoClient client = null;

    public static void init(){
        String url = "mongodb://"+MONGO_HOST+":"+MONGO_PORT;
        ConnectionString uri = new ConnectionString(url);
        // TODO - add auth ?

        client = MongoClients.create(uri);
        System.out.println("MongoDB: Connected");
    }

    public static MongoDatabase getConnection(){
        MongoDatabase database = client.getDatabase(MONGO_DB);
        return database;
    }


}
