package it.unipi.lsmd.dao.neo4j;

import it.unipi.lsmd.dao.WishlistDAO;
import it.unipi.lsmd.dao.base.BaseDAONeo4J;
import it.unipi.lsmd.dao.neo4j.exceptions.Neo4jException;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Trip;
import it.unipi.lsmd.model.Wishlist;
import it.unipi.lsmd.utils.TripUtils;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import java.util.ArrayList;
import java.util.List;

import static org.neo4j.driver.Values.parameters;

public class WishlistNeo4jDAO extends BaseDAONeo4J implements WishlistDAO {
    @Override
    public boolean addToWishlist(RegisteredUser user, Trip trip) throws Neo4jException {
        if(user == null || trip == null)
           return false;

        try (Session session = getConnection().session()) {
            Boolean b = session.readTransaction(tx->{
                Result res = tx.run("MATCH (t:Trip{_id:$id})<-[j:LIKES]-(r:RegisteredUser{username:$username}) " +
                        "RETURN j", parameters("id", trip.getId(), "username", user.getUsername()));
                return res.hasNext();
            });
            if(!b) {    //if b --> relation already exist, we don't want duplicate join
                return session.writeTransaction(tx -> {
                    tx.run("MATCH (t:Trip{_id:$id}),(r:RegisteredUser{username:$username}) " +
                            "CREATE (t)<-[j:LIKES]-(r) " +
                            "RETURN j", parameters("id", trip.getId(), "username", user.getUsername())).consume();
                    return true;
                });
            }
        }catch (Exception e){
            throw new Neo4jException(e.getMessage());
        }

        return false;
    }

    @Override
    public boolean removeFromWishlist(RegisteredUser user, Trip trip) throws Neo4jException {
        if(user==null || trip == null)
            return false;

        try (Session session = getConnection().session()) {
            return session.writeTransaction(tx -> {
                tx.run("MATCH (t:Trip{_id:$id})<-[j:LIKES]-(r:RegisteredUser{username:$username}) " +
                        "DELETE j", parameters("id", trip.getId(), "username", user.getUsername())).consume();
                return true;
            });
        }catch (Exception e){
            throw new Neo4jException(e.getMessage());
        }
    }

    @Override
    public Wishlist getUserWishlist(RegisteredUser user, int size, int page) {
        if(user==null)
            return null;

        Wishlist wishlist = new Wishlist();
        try (Session session = getConnection().session()) {
            List<Trip> listTrips = session.readTransaction(tx -> {
                Result result = tx.run("MATCH (r:RegisteredUser{username: $username}) -[:LIKES]->(t:Trip) " +
                                "WHERE t.deleted = FALSE AND t.departureDate > date() " +
                                "RETURN t._id, t.destination, t.departureDate, t.returnDate, t.title, t.deleted " +
                                "ORDER BY t.departureDate " +
                                "SKIP $skip LIMIT $limit",
                        parameters("username", user.getUsername(), "skip", ((page-1)*size),"limit",size+1));
                List<Trip> trips = new ArrayList<>();

                while (result.hasNext()) {
                    Record r = result.next();
                    Trip t = TripUtils.tripFromRecord(r);
                    trips.add(t);
                }
                return trips;
            });
            wishlist.setWishlist(listTrips);
        }catch (Exception e){
            return new Wishlist();
        }
        return wishlist;


    }

    @Override
    public boolean flushWishlist(RegisteredUser user) throws Neo4jException {
        if(user==null)
            return false;
        try (Session session = getConnection().session()) {
            return session.writeTransaction(tx -> {
                tx.run("MATCH (r:RegisteredUser{username:$username})-[l:LIKES]->() " +
                        "DELETE l", parameters( "username", user.getUsername())).consume();
                return true;
            });
        }catch (Exception e){
            throw new Neo4jException(e.getMessage());
        }
    }

    @Override
    public boolean isInWishlist(RegisteredUser user, Trip trip) throws Neo4jException{
        if(user == null || trip == null)
            return false;

        try (Session session = getConnection().session()) {
            return session.readTransaction(tx->{
                Result res = tx.run("MATCH (t:Trip{_id:$id})<-[j:LIKES]-(r:RegisteredUser{username:$username}) " +
                        "RETURN j", parameters("id", trip.getId(), "username", user.getUsername()));
                return res.hasNext();
            });
        }catch (Exception e){
            throw new Neo4jException(e.getMessage());
        }
    }
}
