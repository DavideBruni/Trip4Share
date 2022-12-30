package it.unipi.lsmd.dao.mongo;

import com.mongodb.MongoException;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import it.unipi.lsmd.dao.TripDetailsDAO;
import it.unipi.lsmd.dao.base.BaseDAOMongo;
import it.unipi.lsmd.dto.TripDetailsDTO;
import it.unipi.lsmd.model.Trip;
import it.unipi.lsmd.utils.TripUtils;
import it.unipi.lsmd.utils.exceptions.IncompleteTripException;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.*;

import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;

public class TripMongoDAO extends BaseDAOMongo implements TripDetailsDAO {

    private final MongoCollection<Document> collection = getConnection().getCollection("trips");

    public List<Trip> getTripsByDestination(String destination, LocalDate departureDate, LocalDate returnDate, int size, int page) {

        Bson m1;
        if (returnDate == null) {
            m1 = match(and(eq("destination", destination), gte("departureDate", departureDate)));
        } else {
            m1 = match(and(eq("destination", destination), gte("departureDate", departureDate),
                    lte("returnDate", returnDate)));
        }
        Bson l1 = limit(size);
        Bson p1 = project(fields(include("_id", "destination", "title", "departureDate", "returnDate")));
        Bson srt = sort(ascending("departureDate"));

        AggregateIterable<Document> res;
        if (page != 1) {
            Bson s1 = skip((page - 1) * size);
            res = collection.aggregate(Arrays.asList(m1, srt, s1, l1, p1));
        } else {
            res = collection.aggregate(Arrays.asList(m1, srt, l1, p1));
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

    public List<Trip> getTripsByTag(String tag, LocalDate departureDate, LocalDate returnDate, int size, int page) {

        Bson m1;
        if (returnDate == null) {
            m1 = match(and(in("tags", tag), gte("departureDate", departureDate)));
        } else {
            m1 = match(and(in("tags", tag), gte("departureDate", departureDate),
                    lte("returnDate", returnDate)));
        }
        Bson l1 = limit(size);
        Bson p1 = project(fields(include("_id", "destination", "title", "departureDate", "returnDate")));
        Bson srt = sort(ascending("departureDate"));

        AggregateIterable<Document> res;
        if (page != 1) {
            Bson s1 = skip((page - 1) * size);
            res = collection.aggregate(Arrays.asList(m1, srt, s1, l1,  p1));
        } else {
            res = collection.aggregate(Arrays.asList(m1, srt, l1, p1));
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

    public List<Trip> getTripsByPrice(int min_price, int max_price, LocalDate departureDate, LocalDate returnDate, int size, int page) {

        Bson m1;
        if (returnDate == null) {
            if(max_price > 0){
                m1 = match(and(gte("price", min_price), lte("price", max_price), gte("departureDate", departureDate)));
            }else{
                m1 = match(and(gte("price", min_price), gte("departureDate", departureDate)));
            }
        } else {
            if(max_price > 0){
                m1 = match(and(gte("price", min_price), lte("price", max_price), gte("departureDate", departureDate),
                        lte("returnDate", returnDate)));
            }else{
                m1 = match(and(gte("price", min_price), gte("departureDate", departureDate), lte("returnDate", returnDate)));
            }

        }
        Bson l1 = limit(size);
        Bson p1 = project(fields(include("_id", "destination", "title", "departureDate", "returnDate")));
        Bson srt = sort(ascending("price"));


        AggregateIterable<Document> res;
        if (page != 1) {
            Bson s1 = skip((page - 1) * size);
            res = collection.aggregate(Arrays.asList(m1, srt, s1, l1,  p1));
        } else {
            res = collection.aggregate(Arrays.asList(m1, srt, l1, p1));
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
        try{
            Bson query = eq("_id", new ObjectId(id));
            Document result = trips.find(query).first();

            trip = TripUtils.tripFromDocument(result);

        }catch (IllegalArgumentException e){ }

        return trip;
    }

    @Override
    public List<String> mostPopularDestinations(int page, int objectPerPageSearch) {

        // aggregate([{$group : {"_id":"$destination" total_like:{$sum:"$like"}}}, {$sort: {total_like : -1}}, {$limit : 5}])
        Bson g1 = group("$destination",sum("total_like","$like"));
        Bson s1 = sort(descending("total_like"));
        Bson l1 = limit(objectPerPageSearch);
        AggregateIterable<Document> res;
        if (page != 1) {
            Bson sk1 = skip((page - 1) * objectPerPageSearch);
            res = collection.aggregate(Arrays.asList(g1, s1, sk1, l1));
        } else {
            res = collection.aggregate(Arrays.asList(g1, s1, l1));
        }
        List<String> dest = new ArrayList<>();
        MongoCursor<Document> it = res.iterator();
        while (it.hasNext()) {
            Document doc = it.next();
            String d = doc.getString("_id");
            dest.add(d);
        }
        return dest;
    }

    @Override
    public List<String> mostPopularDestinationsByTag(String tag, int page, int objectPerPageSearch) {


        // aggregate([{$match : { tags : {$eq : ... }}}, {$group : {"_id":"$destination",
        // total_like:{$sum:"$like"}}}, {$sort: {total_like : -1}}, {$limit : 5}])
        Bson m1 = match(eq("tags",tag));
        Bson g1 = group("$destination",sum("total_like","$like"));
        Bson s1 = sort(descending("total_like"));
        Bson l1 = limit(objectPerPageSearch);
        AggregateIterable<Document> res;
        if (page != 1) {
            Bson sk1 = skip((page - 1) * objectPerPageSearch);
            res = collection.aggregate(Arrays.asList(m1, g1, s1, sk1, l1));
        } else {
            res = collection.aggregate(Arrays.asList(m1, g1, s1, l1));
        }

        List<String> dest = new ArrayList<>();
        MongoCursor<Document> it = res.iterator();
        while (it.hasNext()) {
            Document doc = it.next();
            String d = doc.getString("_id");
            dest.add(d);
        }
        return dest;
    }

    @Override
    public List<String> mostPopularDestinationsByPrice(double start, double end, int page, int objectPerPageSearch) {



        Bson m1 = match(and(gte("price",start),lte("price",end)));
        Bson g1 = group("$destination",sum("total_like","$like"));
        Bson s1 = sort(descending("total_like"));
        Bson l1 = limit(objectPerPageSearch);
        AggregateIterable<Document> res;
        if (page != 1) {
            Bson sk1 = skip((page - 1) * objectPerPageSearch);
            res = collection.aggregate(Arrays.asList(m1, g1, s1, sk1, l1));
        } else {
            res = collection.aggregate(Arrays.asList(m1, g1, s1, l1));
        }

        List<String> destinations = new ArrayList<>();
        MongoCursor<Document> it = res.iterator();
        while (it.hasNext()) {
            Document doc = it.next();
            String s = doc.getString("_id");
            destinations.add(s);
        }
        return destinations;
    }

    @Override
    public List<String> mostPopularDestinationsByPeriod(Date depDate, Date retDate, int page, int objectPerPageSearch) {

        Bson m1 = match(and(gte("departureDate",depDate),lte("returnDate",retDate)));
        Bson g1 = group("$destination",sum("total_like","$like"));
        Bson s1 = sort(descending("total_like"));
        Bson l1 = limit(objectPerPageSearch);
        AggregateIterable<Document> res;
        if (page != 1) {
            Bson sk1 = skip((page - 1) * objectPerPageSearch);
            res = collection.aggregate(Arrays.asList(m1, g1, s1, sk1, l1));
        } else {
            res = collection.aggregate(Arrays.asList(m1, g1, s1, l1));
        }

        List<String> destinations = new ArrayList<>();
        MongoCursor<Document> it = res.iterator();
        while (it.hasNext()) {
            Document doc = it.next();
            String s = doc.getString("_id");
            destinations.add(s);
        }
        return destinations;
    }

    @Override
    public List<Trip> cheapestDestinationsByAvg(int page, int objectPerPageSearch) {

        Bson s1 = sort(ascending("price"));
        Bson g1 = group("$destination",avg("agg","$price"));
        Bson l1 = limit(objectPerPageSearch);
        AggregateIterable<Document> res;
        if (page != 1) {
            Bson sk1 = skip((page - 1) * objectPerPageSearch);
            res = collection.aggregate(Arrays.asList(s1, g1, sk1, l1));
        } else {
            res = collection.aggregate(Arrays.asList(s1, g1, l1));
        }
        List<Trip> trips = new ArrayList<>();
        MongoCursor<Document> it = res.iterator();
        while (it.hasNext()) {
            Document doc = it.next();
            Trip t = TripUtils.destinationFromDocument(doc);
            trips.add(t);
        }
        return trips;
    }

    @Override
    public List<Trip> cheapestTripForDestinationInPeriod(Date start, Date end,int page, int objectPerPageSearch) {

// db.trips.aggregate([{$match : {$and : {"departureDate" : {$gte : new Date()}},{"returnDate" : {$lte : new Date('2024-12-12')}}} }},{ $sort: { price : 1 } }, { $group: { _id: "$destination", doc_with_max_ver: { $first: "$$ROOT" } } },{ $replaceWith: "$doc_with_max_ver" }
        Bson m1;
        if(end != null)
            m1  = match(and(gte("departureDate",start),lte("returnDate",end)));
        else
            m1 = match(gte("departureDate",start));
        Bson s1 = sort(ascending("price"));
        Bson g1 = group("$destination",first("doc_with_max_ver","$$ROOT"));
        Bson r1 = replaceWith("$doc_with_max_ver");
        Bson l1 = limit(objectPerPageSearch);
        AggregateIterable<Document> res;
        if (page != 1) {
            Bson sk1 = skip((page - 1) * objectPerPageSearch);
            res = collection.aggregate(Arrays.asList(m1, s1, g1,r1, sk1, l1));
        } else {
            res = collection.aggregate(Arrays.asList(m1, s1, g1,r1, l1));
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
    public String addTrip(Trip t){

        try {
            Document doc = TripUtils.documentFromTrip(t);
            return collection.insertOne(doc).getInsertedId().asObjectId().getValue().toString();
        }catch(IncompleteTripException iexc){
            //stampare un log di errore
            return null;
        }catch(MongoException me){
            return null;
        }
    }

    @Override
    public boolean deleteTrip(Trip t){
        try {
            Bson q1 = eq("_id",new ObjectId(t.getId()));
            collection.deleteOne(q1);
        }catch(MongoException me){
            return false;
        }
        return true;
    }

    @Override
    public boolean updateTrip(Trip newTrip, Trip oldTrip) {
        try{
            Document q1 = new Document().append("_id", new ObjectId(newTrip.getId()));
            Bson q2 = attributeToUpdate(newTrip, oldTrip);
            collection.updateOne(q1, q2);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    private Bson attributeToUpdate(Trip newTrip, Trip oldTrip) {
        List<Bson> query = new ArrayList<>();
        if(!newTrip.getItinerary().equals(oldTrip.getItinerary()))
           query.add(Updates.set("itinerary",newTrip.getItinerary()));
        if(!newTrip.getTags().equals(oldTrip.getTags()))
            query.add(Updates.set("tags",newTrip.getTags()));
        if(!newTrip.getWhatsNotIncluded().equals(oldTrip.getWhatsNotIncluded()))
            query.add(Updates.set("whatsNotIncluded",newTrip.getWhatsNotIncluded()));
        if(!newTrip.getWhatsIncluded().equals(oldTrip.getWhatsIncluded()))
            query.add(Updates.set("whatsIncluded",newTrip.getWhatsIncluded()));
        if(newTrip.getDestination()!= oldTrip.getDestination())
            query.add(Updates.set("destination",newTrip.getDestination()));
        if(newTrip.getTitle()!= oldTrip.getTitle())
            query.add(Updates.set("title",newTrip.getDestination()));
        if(newTrip.getDescription()!= oldTrip.getDescription())
            query.add(Updates.set("description",newTrip.getDescription()));
        if(newTrip.getInfo()!= oldTrip.getInfo())
            query.add(Updates.set("info",newTrip.getInfo()));
        if(newTrip.getPrice()!= oldTrip.getPrice())
            query.add(Updates.set("price",newTrip.getPrice()));
        if(newTrip.getDepartureDate()!= oldTrip.getDepartureDate())
            query.add(Updates.set("departureDate",newTrip.getDepartureDate()));
        if(newTrip.getReturnDate()!= oldTrip.getReturnDate())
            query.add(Updates.set("returnDate",newTrip.getReturnDate()));
        query.add(Updates.set("imgUrl",newTrip.getImg()));
        return Updates.combine(query);
    }

    @Override
    public List<Trip> mostPopularTrips(int tripNumberIndex) {
        Bson m1 = match(gt("departureDate", LocalDate.now()));
        Bson s1 = sort(descending("likes"));
        Bson l1 = limit(tripNumberIndex);
        AggregateIterable<Document> res;
        res = collection.aggregate(Arrays.asList(m1, s1, l1));
        List<Trip> trips = new ArrayList<>();
        MongoCursor<Document> it = res.iterator();
        while (it.hasNext()) {
            Document doc = it.next();
            Trip t = TripUtils.tripFromDocument(doc);
            trips.add(t);
        }
        return trips;
    }
}
