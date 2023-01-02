package it.unipi.lsmd.dao.neo4j;

import it.unipi.lsmd.dao.TripDAO;
import it.unipi.lsmd.dao.base.BaseDAONeo4J;
import it.unipi.lsmd.dao.neo4j.exceptions.Neo4jException;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Trip;
import it.unipi.lsmd.model.enums.Status;
import it.unipi.lsmd.utils.TripUtils;
import org.javatuples.Pair;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.neo4j.driver.Values.parameters;

public class TripNeo4jDAO extends BaseDAONeo4J implements TripDAO {

    @Override
    public List<Trip> getTripsOrganizedByFollower(String username, int size, int page) {
        List<Trip> tripsList;
        try (Session session = getConnection().session()) {
            tripsList = session.readTransaction(tx -> {
                Result result = tx.run("MATCH (r1:RegisteredUser{username : $username})-[:FOLLOW]->(r2:RegisteredUser) <-" +

                        "[:ORGANIZED_BY]-(t:Trip) WHERE t.deleted = FALSE " +
                        " RETURN t.destination, t.departureDate, t.returnDate, t.title, t.deleted, t.imgUrl, r2.username as organizer" +
                        " ORDER BY t.departureDate SKIP $skip LIMIT $limit",
                        parameters("username", username, "skip", ((page-1)*(size-1)),"limit",size));
                List trips = new ArrayList<>();

                while (result.hasNext()) {
                    Record r = result.next();
                    Trip t = TripUtils.tripFromRecord(r);
                    trips.add(t);
                }
                return trips;
            });
        }catch (Exception e){
            return new ArrayList<>();
        }
        return tripsList;
    }

    @Override
    public List<Trip> getSuggestedTrip(String username, int numTrips){
        List<Trip> tripsList;
        try (Session session = getConnection().session()) {
            tripsList = session.readTransaction(tx -> {
                Result result = tx.run("MATCH (r1:RegisteredUser{username : $username})-[:FOLLOW]->(r2:RegisteredUser) -[:JOIN]->(t:Trip) " +
                                "WHERE t.departureDate > date() AND (NOT (r1)-[:JOIN]->(t)) AND (NOT (t)-[:ORGANIZED_BY] -> (r1) AND t.deleted = FALSE " +
                                "RETURN t.destination, t.departureDate, t.returnDate,t.title, t.deleted, t.imgUrl, r2.username as organizer, rand() as ord " +

                                "ORDER BY ord LIMIT $limit",
                        parameters("username", username, "limit",numTrips));
                List trips = new ArrayList<>();
                while (result.hasNext()) {
                    Record r = result.next();
                    Trip t = TripUtils.tripFromRecord(r);
                    trips.add(t);
                }
                return trips;
            });
        }catch (Exception e){
            return new ArrayList<>();
        }
        return tripsList;
    }

    @Override
    public void addTrip(Trip t, RegisteredUser organizer) throws Neo4jException {
        String depDate = t.getDepartureDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String retDate = t.getReturnDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        try (Session session = getConnection().session()) {
            session.writeTransaction(tx -> {
                tx.run("CREATE (t:Trip {_id: $id ,destination : $destination, title : $title, imgUrl : $imgUrl," +
                                "departureDate : date($departureDate), returnDate: date($returnDate), deleted : FALSE})",
                        parameters("id", t.getId(),"destination",t.getDestination(), "title", t.getTitle(),
                                "imgUrl",t.getImg(),"departureDate",depDate,"returnDate",retDate)).consume();
                tx.run("MATCH (t:Trip), (r:RegisteredUser) " +
                        "WHERE t._id = $id AND r.username = $username " +
                        "CREATE (t)-[o:ORGANIZED_BY]->(r) " +
                        "RETURN type(o)", parameters("id", t.getId(),"username",organizer.getUsername())).consume();
                return null;
            });
        }catch (Exception e){
            throw new Neo4jException(e.getMessage());
        }
    }

    @Override
    public void deleteTrip(Trip t) throws Neo4jException {
        try (Session session = getConnection().session()) {
            Boolean exists = session.readTransaction(tx->{
                Result res = tx.run("MATCH (t:Trip {_id: $id})<-[:JOIN]-(:RegisteredUser) RETURN t",
                        parameters("id",t.getId()));
                return res.hasNext();
            });
            if(!exists){
                //nessun utente ha fatto join, posso cancellarlo anche da Neo4j
                session.writeTransaction(tx -> {
                    tx.run("MATCH (t:Trip) " +
                            "WHERE t._id = $id " +
                            "DETACH DELETE t ",parameters("id", t.getId())).consume();
                    return null;
                });
            }else {
                session.writeTransaction(tx -> {
                    tx.run("MATCH (t:Trip) " +
                            "WHERE t._id = $id " +
                            "SET t.deleted = TRUE " +
                            "RETURN t", parameters("id", t.getId())).consume();
                    return null;
                });
            }
        }catch (Exception e){
            throw new Neo4jException(e.getMessage());
        }
    }

    @Override
    public void setNotDeleted(Trip t) throws Neo4jException {
        try (Session session = getConnection().session()) {
            session.writeTransaction(tx -> {
                 tx.run("MATCH (t:Trip), " +
                        "WHERE t._id = $id " +
                        "SET t.deleted = FALSE " +
                        "RETURN t", parameters("id", t.getId())).consume();
                return null;
            });
        }catch (Exception e){
            throw new Neo4jException(e.getMessage());
        }
    }

    @Override
    public void updateTrip(Trip newTrip, Trip oldTrip) throws Neo4jException {
        String query = createQuery(newTrip,oldTrip);
        if(query==null)
            return;
        try (Session session = getConnection().session()) {
            session.writeTransaction(tx -> {
                tx.run(query).consume();
                return null;
            });
        }catch (Exception e){
            throw new Neo4jException(e.getMessage());
        }
    }

    private String createQuery(Trip newTrip, Trip oldTrip) {
        List<String> sets = new ArrayList<>();
        if(!newTrip.getTitle().equals(oldTrip.getTitle())){
            String s = "t.title = \""+newTrip.getTitle()+"\"";
            sets.add(s);
        }
        if(!newTrip.getDestination().equals(oldTrip.getDestination())){
            String s = "t.destination = \""+newTrip.getDestination()+"\"";
            sets.add(s);
        }
        if(!newTrip.getDepartureDate().equals(oldTrip.getDepartureDate())){
            String s = "t.departureDate = \""+newTrip.getDepartureDate()+"\"";
            sets.add(s);
        }
        if(!newTrip.getTitle().equals(oldTrip.getTitle())){
            String s = "t.returnDate = \""+newTrip.getReturnDate()+"\"";
            sets.add(s);
        }
        if(sets==null  || sets.isEmpty())
            return null;
        StringBuilder query = new StringBuilder("MATCH (t:Trip {_id:\""+oldTrip.getId()+"\"}) SET ");
        query.append(sets.get(0));
        sets.remove(0);
        for(String x : sets){
            query.append(", "+x);
        }
        query.append(" RETURN t");
        return String.valueOf(query);
    }

    @Override
    public Trip getJoinersAndOrganizer(Trip t){
        Trip trip;
        try (Session session = getConnection().session()) {
            trip = session.readTransaction(tx -> {
                Result result = tx.run("MATCH (r1:RegisteredUser) -[j:JOIN]->(:Trip {_id : $id}) -[:ORGANIZED_BY]->(r2:RegisteredUser) " +
                                "RETURN r1.username, r1.profile_pic, r2.username, j.status ORDER BY j.status",
                        parameters("id", t.getId()));
                Trip res = new Trip();
                res.setId(t.getId());
                List<Pair<RegisteredUser, Status>> joiners = new ArrayList<>();
                boolean first = true;
                while (result.hasNext()) {
                    Record r = result.next();
                    if (first) {
                        RegisteredUser org = new RegisteredUser(r.get("r2.username").asString());
                        res.setOrganizer(org);
                        first = false;
                    }
                    Pair<RegisteredUser,Status> j = new Pair<>(new RegisteredUser(r.get("r1.username").asString(),
                            r.get("r1.profile_pic").asString()),
                            Status.valueOf(r.get("j.status").asString()));
                    joiners.add(j);
                }
                res.setJoiners(joiners);
                return res;
            });
        }catch (Exception e){
            return null;
        }
        return trip;
    }

    @Override
    public void removeJoin(Trip t,RegisteredUser r) throws Neo4jException {
        try (Session session = getConnection().session()) {
           session.writeTransaction(tx -> {
                tx.run("MATCH (t:Trip)<-[j:JOIN]-(r:RegisteredUser) " +
                        "WHERE t._id = $id AND r.username = $username " +
                        "DELETE (j) ", parameters("id", t.getId(), "username", r.getUsername())).consume();
                return null;
           });
        }catch (Exception e){
            throw new Neo4jException(e.getMessage());
        }
    }

    // TODO: set pending
    @Override
    public void setStatusJoin(Trip t, RegisteredUser r, Status status) throws Neo4jException {
        try (Session session = getConnection().session()) {
            session.writeTransaction(tx -> {
                            tx.run("MATCH (t:Trip)<-[j:JOIN]-(r:RegisteredUser) " +
                                    "WHERE t._id = $id AND r.username = $username " +
                                    "SET j.status = $status ", parameters("id", t.getId(), "username", r.getUsername(),
                                    "status",status.name())).consume();
                            return null;
                        }
                );
        }catch (Exception e){
            throw new Neo4jException(e.getMessage());
        }
    }

    @Override
    public RegisteredUser getOrganizer(Trip trip) throws Neo4jException {

        RegisteredUser user = new RegisteredUser();
        try (Session session = getConnection().session()) {
            String username = session.readTransaction(tx -> {
                Result result = tx.run("MATCH (t:Trip{_id: $id})-[:ORGANIZED_BY]->(r:RegisteredUser) " +
                                "RETURN r.username as organizer",
                        parameters("id", trip.getId()));
                return result.next().get("organizer").asString();
            });
            user.setUsername(username);
        }catch (Exception e){
            //System.out.println(e);
            throw new Neo4jException();
        }
        return user;
    }

    public List<Trip> getTripOrganizedByUser(String organizer, int size, int page){

        List<Trip> trip_list;

        try (Session session = getConnection().session()) {
            trip_list = session.readTransaction(tx -> {
                Result result = tx.run("MATCH (t:Trip)-[:ORGANIZED_BY]->(r:RegisteredUser{username: $username}) " +
                                "RETURN t._id, t.destination, t.departureDate, t.returnDate, t.title, t.deleted, t.imgUrl, r.username as organizer " +
                                "ORDER BY t.departureDate DESC " +
                                "SKIP $skip " +
                                "LIMIT $limit",
                        parameters("username", organizer, "skip", ((page-1)*(size-1)), "limit", size));
                List<Trip> trips = new ArrayList<Trip>();
                while(result.hasNext()){
                    Record r = result.next();
                    Trip trip = TripUtils.tripFromRecord(r);
                    trips.add(trip);
                }
                return trips;
            });
        }catch (Exception e){
            System.out.println(e);
            return new ArrayList<>();

        }
        return trip_list;
    }

    public List<Trip> getPastTrips(String username, int size, int page){
        List<Trip> trip_list;

        try (Session session = getConnection().session()) {
            trip_list = session.readTransaction(tx -> {
                Result result = tx.run("MATCH (r:RegisteredUser{username: $username})-[:JOIN]->(t:Trip)-[:ORGANIZED_BY]->(r1:RegisteredUser) " +
                                "RETURN t._id, t.destination, t.departureDate, t.returnDate, t.title, t.deleted, t.imgUrl, r1.username as organizer " +
                                "ORDER BY t.departureDate DESC " +
                                "SKIP $skip " +
                                "LIMIT $limit",
                        parameters("username", username, "skip", ((page-1)*(size-1)), "limit", size));
                List<Trip> trips = new ArrayList<Trip>();
                while(result.hasNext()){
                    Record r = result.next();
                    Trip trip = TripUtils.tripFromRecord(r);
                    trips.add(trip);
                }
                return trips;
            });
        }catch (Exception e){
            System.out.println(e);
            return new ArrayList<>();

        }
        return trip_list;
    }

    @Override
    public void createJoin(Trip t, RegisteredUser r) throws Neo4jException {
        try (Session session = getConnection().session()) {
            Boolean b = session.readTransaction(tx->{
                Result res = tx.run("MATCH (t:Trip{_id:$id})<-[j:JOIN]-(r:RegisteredUser{username:$username}) " +
                        "RETURN j", parameters("id", t.getId(), "username", r.getUsername()));
                return res.hasNext();
            });
            if(!b) {    //if b --> relation already exist, we don't want duplicate join
                session.writeTransaction(tx -> {
                    tx.run("MATCH (t:Trip{_id:$id}),(r:RegisteredUser{username:$username}) " +
                            "CREATE (t)<-[j:JOIN {status:\"pending\"}]-(r) " +
                            "RETURN j", parameters("id", t.getId(), "username", r.getUsername())).consume();
                    return null;
                });
            }
        }catch (Exception e){
            throw new Neo4jException(e.getMessage());
        }
    }

    @Override
    public void cancelJoin(Trip t, RegisteredUser r) throws Neo4jException {
        try (Session session = getConnection().session()) {
            session.writeTransaction(tx -> {
                tx.run("MATCH (t:Trip{_id:$id})<-[j:JOIN]-(r:RegisteredUser{username:$username}) " +
                        "DELETE j", parameters("id", t.getId(), "username", r.getUsername())).consume();
                return null;
            });
        }catch (Exception e){
            throw new Neo4jException(e.getMessage());
        }
    }

    @Override
    public Status getJoinStatus(Trip t, RegisteredUser r) throws Neo4jException {
        String status;
        try (Session session = getConnection().session()) {
             status = session.readTransaction(tx -> {
                Result res = tx.run("MATCH (t:Trip{_id:$id})<-[j:JOIN]-(r:RegisteredUser{username:$username}) " +
                        "RETURN j.status", parameters("id", t.getId(), "username", r.getUsername()));
                if(res.hasNext()){
                    return res.next().get("j.status").asString();
                }
                return null;
            });
        }catch (Exception e){
            throw new Neo4jException(e.getMessage());
        }
        if(status!=null){
            return Status.valueOf(status);
        }else{
            return null;
        }
    }
}
