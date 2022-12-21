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
                                "t.returnDate,t.title, t.deleted,t.imgUrl"+
                        " ORDER BY t.departureDate SKIP $skip",
                        parameters("username", username, "skip", ((page-1)*size)));
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
    public List<Trip> getSuggestedTrip(String username){
        List<Trip> tripsList;
        try (Session session = getConnection().session()) {
            tripsList = session.readTransaction(tx -> {
                Result result = tx.run("MATCH (r1:RegisteredUser{username : $username})-[:FOLLOW]->(r2:RegisteredUser) -" +
                                "[:JOIN]->(t:Trip) WHERE t.departureDate > date() AND (NOT (r1)-[:JOIN]->(t)) AND (NOT (t)-[:ORGANIZED_BY] -> (r1)"+
                                " AND t.deleted = false RETURN t.destination, t.departureDate, t.returnDate,t.title, t.deleted,t.imgUrl",
                        parameters("username", username));
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
        String depDate = t.getDepartureDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String retDate = t.getReturnDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        try (Session session = getConnection().session()) {
            session.writeTransaction(tx -> {
                tx.run("CREATE (t:Trip {id: $id ,destination : $destination, title : $title, imgUrl : $imgUrl," +
                                "departureDate : date($departureDate), returnDate: date($returnDate), deleted : FALSE})",
                        parameters("id", t.getId(),"destination",t.getDestination(), "title", t.getTitle(),
                                "imgUrl",t.getImg(),"departureDate",depDate,"returnDate",retDate)).consume();
                tx.run("MATCH (t:Trip), (r:RegisteredUser)" +
                        "WHERE t.id = $id AND r.username = $username" +
                        "CREATE (t)<-[o:ORGANIZED_BY]->(r)" +
                        "RETURN type(o)", parameters("id", t.getId(),"username",organizer.getUsername())).consume();
                return null;
            });
        }catch (Exception e){
            throw new Neo4jException(e.getMessage());
        }
    }

    @Override
    public void deleteTrip(Trip t) throws Neo4jException {

    }


}
