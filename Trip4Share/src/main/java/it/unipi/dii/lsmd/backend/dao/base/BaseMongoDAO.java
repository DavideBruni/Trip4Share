package it.unipi.dii.lsmd.backend.dao.base;

import com.mongodb.*;
import com.mongodb.client.*;
import it.unipi.dii.lsmd.backend.config.Loader;

public abstract class BaseMongoDAO {
    //Write concern: ACKNOWLEDGED, journaled, majority, unack, w1,w2,w3 (we choose AP, with no C)
    //Read preferences: primary, primary prefered, secondary, secondaty prefered, NEAREST (fastest, but not Consistency)
    //NOTE: some operations may need more consistency, we can turn it using more specific concern at db or write level

    private static final String MONGO_HOST = Loader.getInstance().getMongoHost();
    private static final int MONGO_PORT = Loader.getInstance().getMongoPort();

    private static final String uri = "mongodb://"+MONGO_HOST+":"+MONGO_PORT;

    private static final MongoClientSettings mcs = MongoClientSettings.builder()
            .applicationName(uri).readPreference(ReadPreference.nearest())
            .retryWrites(true)
            .writeConcern(WriteConcern.ACKNOWLEDGED).build();

    private static final MongoClient mongoClient = MongoClients.create();

    public MongoClient getMongoClient(){ return mongoClient;}
}
