package it.unipi.lsmd.dao.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import it.unipi.lsmd.dao.WishlistDAO;
import it.unipi.lsmd.dao.base.BaseDAOMongo;
import it.unipi.lsmd.model.Trip;
import it.unipi.lsmd.utils.TripUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.inc;

public class WishlistMongoDAO extends BaseDAOMongo {

    public void addToWishlist(String trip_id) {
        MongoDatabase database = getConnection();
        MongoCollection<Document> trips = database.getCollection("trips");

        try{
            Bson filter = eq("_id", new ObjectId(trip_id));
            Bson modifier = inc("likes", 1);
            trips.updateOne(filter, modifier);

        }catch (IllegalArgumentException e){ }

    }


    public void removeFromWishlist(String trip_id) {
        MongoDatabase database = getConnection();
        MongoCollection<Document> trips = database.getCollection("trips");

        try{
            Bson filter = eq("_id", new ObjectId(trip_id));
            Bson modifier = inc("likes", -1);
            trips.updateOne(filter, modifier);

        }catch (IllegalArgumentException e){ }
    }
}
