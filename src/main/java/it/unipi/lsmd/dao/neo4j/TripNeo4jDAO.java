package it.unipi.lsmd.dao.neo4j;

import it.unipi.lsmd.dao.TripDAO;
import it.unipi.lsmd.dao.base.BaseDAONeo4J;
import it.unipi.lsmd.model.Trip;
import it.unipi.lsmd.utils.TripUtils;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

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
                        "[:ORGANIZED_BY]-(t:Trip) RETURN t.destination, t.departureDate," +
                                "t.returnDate,t.title, t.deleted,t.imgUrl"+
                        " ORDER BY t.departureDate SKIP "+((page-1)*size),
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
    public List<Trip> getSuggestedTrip(String username){
        List<Trip> tripsList;
        try (Session session = getConnection().session()) {
            tripsList = session.readTransaction(tx -> {
                Result result = tx.run("MATCH (r1:RegisteredUser{username : $username})-[:FOLLOW]->(r2:RegisteredUser) -" +
                                "[:JOIN]->(t:Trip) WHERE t.departureDate > date() AND (NOT (r1)-[:JOIN]->(t)) AND (NOT (t)-[:ORGANIZED_BY] -> (r1)"+
                                " RETURN t.destination, t.departureDate, t.returnDate,t.title, t.deleted,t.imgUrl",
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


}
