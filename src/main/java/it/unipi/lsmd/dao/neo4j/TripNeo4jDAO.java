package it.unipi.lsmd.dao.neo4j;

import it.unipi.lsmd.dao.TripDAO;
import it.unipi.lsmd.dao.base.BaseDAONeo4J;
import it.unipi.lsmd.dao.neo4j.exceptions.Neo4jException;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Trip;
import it.unipi.lsmd.utils.TripUtils;
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
                        "[:ORGANIZED_BY]-(t:Trip) WHERE t.deleted = FALSE RETURN t.destination, t.departureDate," +
                                "t.returnDate,t.title, t.deleted,t.imgUrl,t._id, r2.username"+
                        " ORDER BY t.departureDate SKIP $skip LIMIT $limit",
                        parameters("username", username, "skip", ((page-1)*size),"limit",size));
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
                Result result = tx.run("MATCH (r1:RegisteredUser{username : $username})-[:FOLLOW]->(r2:RegisteredUser)-" +
                                "[:JOIN]->(t:Trip) WHERE t.departureDate > date() AND (NOT (r1)-[:JOIN]->(t)) AND (NOT (t)-[:ORGANIZED_BY] -> (r1))"+
                                " AND t.deleted = FALSE RETURN t.destination, t.departureDate, t.returnDate,t.title, t.deleted,t.imgUrl, rand() as ord " +
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
                tx.run("CREATE (t:Trip {id: $id ,destination : $destination, title : $title, imgUrl : $imgUrl," +
                                "departureDate : date($departureDate), returnDate: date($returnDate), deleted : FALSE})",
                        parameters("id", t.getId(),"destination",t.getDestination(), "title", t.getTitle(),
                                "imgUrl",t.getImg(),"departureDate",depDate,"returnDate",retDate)).consume();
                tx.run("MATCH (t:Trip), (r:RegisteredUser) " +
                        "WHERE t.id = $id AND r.username = $username " +
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
            Result res = session.readTransaction(tx->{
                return tx.run("RETURN EXISTS( (:Trip {id: $id})<-[:JOIN]-(:RegisteredUser)) as ex",
                        parameters("id",t.getId()));
            });
            if(!res.next().get("ex").asBoolean()){
                //nessun utente ha fatto join, posso cancellarlo anche da Neo4j
                session.writeTransaction(tx -> {
                    tx.run("MATCH (t:Trip) " +
                            "WHERE t.id = $id " +
                            "DETACH DELETE t ",parameters("id", t.getId())).consume();
                    return null;
                });
            }else {
                session.writeTransaction(tx -> {
                    tx.run("MATCH (t:Trip) " +
                            "WHERE t.id = $id " +
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
                        "WHERE t.id = $id " +
                        "SET t.deleted = FALSE " +
                        "RETURN t", parameters("id", t.getId())).consume();
                return null;
            });
        }catch (Exception e){
            throw new Neo4jException(e.getMessage());
        }
    }

    @Override
    public void updateTrip(Trip newTrip) throws Neo4jException {

    }
}
