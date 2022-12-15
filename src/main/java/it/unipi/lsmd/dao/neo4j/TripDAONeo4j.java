package it.unipi.lsmd.dao.neo4j;

import it.unipi.lsmd.dao.base.BaseDAONeo4J;
import it.unipi.lsmd.model.Trip;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Values;
import org.neo4j.driver.exceptions.value.Uncoercible;

import java.util.ArrayList;
import java.util.List;

import static org.neo4j.driver.Values.parameters;

public class TripDAONeo4j extends BaseDAONeo4J {

    public List<Trip> getTripsOrganizedByFollower(String follower) {
        List<Trip> tripsList;
        try (Session session = getConnection().session()) {
            tripsList = session.readTransaction(tx -> {
                Result result = tx.run("MATCH (r1:RegisteredUser)-[:FOLLOW]->(r2:RegisteredUser) <-" +
                        "[:ORGANIZED_BY]-(t:Trip) WHERE r1.username =$username RETURN t.destination, t.departureDate," +
                                "t.returnDate,t.title, t.deleted,t.imgUrl",
                        parameters("username", follower));
                List trips = new ArrayList<>();
                while (result.hasNext()) {
                    Record r = result.next();
                    Trip t = new Trip();
                    t.setDestination(r.get("t.destination").asString());
                    t.setTitle(r.get("t.title").asString());
                    t.setImg(r.get("it.mgUrl").asString());
                    try {
                        t.setDeleted(r.get("t.deleted").asBoolean());
                        t.setDepartureDate(r.get("t.departureDate").asLocalDate());
                        t.setReturnDate(r.get("t.returnDate").asLocalDate());
                    }catch (Uncoercible uncoercible){
                        t.setDeleted(Boolean.FALSE);
                        t.setDepartureDate(null);
                        t.setReturnDate(null);
                    }finally {
                        trips.add(t);
                    }
                }
                return trips;
            });
        }catch (Exception e){
            return new ArrayList<>();
        }
        return tripsList;
    }

}
