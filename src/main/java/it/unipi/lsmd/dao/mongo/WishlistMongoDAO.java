package it.unipi.lsmd.dao.mongo;

import com.mongodb.client.MongoCollection;
import it.unipi.lsmd.dao.base.BaseDAOMongo;
import it.unipi.lsmd.model.Trip;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.inc;

public class WishlistMongoDAO extends BaseDAOMongo {

    private final MongoCollection<Document> collection = getConnection().getCollection("trips");
    private static final Logger logger = LoggerFactory.getLogger(WishlistMongoDAO.class);

    public boolean addToWishlist(Trip trip) {

        try{
            Bson filter = eq("_id", new ObjectId(trip.getId()));
            Bson modifier = inc("likes", 1);
            collection.updateOne(filter, modifier);
            return true;
        }catch (IllegalArgumentException e){
            logger.error("Error. Some error occurred " + e);
        }
        return false;
    }


    public boolean removeFromWishlist(Trip trip) {

        try{
            Bson filter = eq("_id", new ObjectId(trip.getId()));
            Bson modifier = inc("likes", -1);
            collection.updateOne(filter, modifier);
            return true;
        }catch (IllegalArgumentException e){
            logger.error("Error. Some error occurred " + e);
        }
        return false;
    }
}
