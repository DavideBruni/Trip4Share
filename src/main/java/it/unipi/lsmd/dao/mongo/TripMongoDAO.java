package it.unipi.lsmd.dao.mongo;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import it.unipi.lsmd.dao.base.BaseDAOMongo;
import it.unipi.lsmd.model.Trip;
import it.unipi.lsmd.utils.TripUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import javax.print.Doc;
import java.util.*;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;

public class TripMongoDAO extends BaseDAOMongo implements TripDAO{

    public List<Trip> getTripsByDestination(String destination, Date departureDate, Date returnDate, int size, int page) {
        MongoDatabase database = getConnection();
        MongoCollection<Document> collection = database.getCollection("trips");
        Bson m1;
        if (returnDate == null) {
            m1 = match(and(eq("destination", destination), gte("departureDate", departureDate)));
        } else {
            m1 = match(and(eq("destination", destination), gte("departureDate", departureDate),
                    lte("returnDate", returnDate)));
        }
        Bson l1 = limit(size);
        Bson p1 = project(fields(excludeId(), include("destination", "title", "departureDate", "returnDate")));
        AggregateIterable<Document> res;
        if (page != 1) {
            Bson s1 = skip((page - 1) * size);
            res = collection.aggregate(Arrays.asList(m1, l1, s1, p1));
        } else {
            res = collection.aggregate(Arrays.asList(m1, l1, p1));
        }
        List<Trip> trips = new ArrayList<>();
        MongoCursor<Document> it = res.iterator();
        while (it.hasNext()) {
            Document doc = it.next();
            Trip t = TripUtils.tripFromDocument(doc);
            trips.add(t);
        }
        return trips;
    }

    public List<Trip> getTripsByTag(String tag, Date departureDate, Date returnDate, int size, int page) {
        MongoDatabase database = getConnection();
        MongoCollection<Document> collection = database.getCollection("trips");
        Bson m1;
        if (returnDate == null) {
            m1 = match(and(in("tags", tag), gte("departureDate", departureDate)));
        } else {
            m1 = match(and(in("tags", tag), gte("departureDate", departureDate),
                    lte("returnDate", returnDate)));
        }
        Bson l1 = limit(size);
        Bson p1 = project(fields(excludeId(), include("destination", "title", "departureDate", "returnDate")));
        AggregateIterable<Document> res;
        if (page != 1) {
            Bson s1 = skip((page - 1) * size);
            res = collection.aggregate(Arrays.asList(m1, l1, s1, p1));
        } else {
            res = collection.aggregate(Arrays.asList(m1, l1, p1));
        }
        List<Trip> trips = new ArrayList<>();
        MongoCursor<Document> it = res.iterator();
        while (it.hasNext()) {
            Document doc = it.next();
            Trip t = TripUtils.tripFromDocument(doc);
            trips.add(t);
        }
        return trips;
    }

    @Override
    public Trip getTrip(String id) {

        MongoDatabase database = getConnection();
        MongoCollection<Document> trips = database.getCollection("trips");

        Trip trip = null;

        Bson query = eq("_id", new ObjectId(id));
        Document result = trips.find(query).first();

        trip = TripUtils.tripFromDocument(result);

        return trip;
    }
}
