package it.unipi.lsmd.dao.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import it.unipi.lsmd.dao.TripDAO;
import it.unipi.lsmd.dao.UserDAO;
import it.unipi.lsmd.dao.base.BaseDAOMongo;
import it.unipi.lsmd.model.Trip;
import it.unipi.lsmd.utils.TripUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;


public class TripMongoDAO extends BaseDAOMongo implements TripDAO {

    @Override
    public Trip getTrip(String id) {

        MongoDatabase database = getConnection();
        MongoCollection<Document> trips = database.getCollection("trips");

        Trip trip = null;

        try{
            Bson query = eq("_id", new ObjectId(id));
            Document result = trips.find(query).first();

            trip = TripUtils.tripFromDocument(result);

        }catch (IllegalArgumentException e){

        }

        return trip;
    }
}
