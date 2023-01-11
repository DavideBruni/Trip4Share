package it.unipi.lsmd.dao.neo4j;

import it.unipi.lsmd.dao.DAOLocator;
import it.unipi.lsmd.dao.RegisteredUserDAO;
import it.unipi.lsmd.dao.base.BaseDAONeo4J;
import it.unipi.lsmd.dao.neo4j.exceptions.Neo4jException;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Trip;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import java.util.ArrayList;
import java.util.List;

import static org.neo4j.driver.Values.parameters;

public class RegisteredUserNeo4jDAO extends BaseDAONeo4J implements RegisteredUserDAO {

    public List<RegisteredUser> getSuggestedUser(RegisteredUser user, int nUser){

        if(user == null)
            return null;

        List<RegisteredUser> suggested;
        try (Session session = getConnection().session()) {
            suggested = session.readTransaction(tx -> {
                Result result = tx.run("MATCH (u1:RegisteredUser {username:$username})-[:FOLLOW]->(u2:RegisteredUser)," +
                                "(u2)-[:FOLLOW]->(u3:RegisteredUser) WHERE u1.username <> u3.username AND " +
                                "(NOT (u1)-[:FOLLOW]->(u3)) RETURN DISTINCT u3.username, rand() as r " +
                                "ORDER BY r LIMIT $limit",
                        parameters("username", user.getUsername(), "limit",nUser));
                List<RegisteredUser> users = new ArrayList<>();
                while (result.hasNext()) {
                    Record r = result.next();
                    RegisteredUser ru = new RegisteredUser();
                    ru.setUsername(r.get("u3.username").asString());
                    users.add(ru);
                }
                return users;
            });
        }catch (Exception e){
            return new ArrayList<>();
        }
        return suggested;
    }

    @Override
    public List<RegisteredUser> getFollowing(RegisteredUser user, int size, int page){

        if(user == null)
            return null;

        List<RegisteredUser> followers;
        try (Session session = getConnection().session()) {
            followers = session.readTransaction(tx -> {
                Result result = tx.run("MATCH (u1:RegisteredUser {username:$username})-[:FOLLOW]->(u2:RegisteredUser)"+
                                "RETURN u2.username " +
                                "SKIP $skip " +
                                "LIMIT $limit",
                        parameters("username", user.getUsername(), "skip", ((page-1)*size), "limit", size ));
                List<RegisteredUser> users = new ArrayList<>();
                while (result.hasNext()) {
                    Record r = result.next();
                    RegisteredUser ru = new RegisteredUser();
                    ru.setUsername(r.get("u2.username").asString());
                    users.add(ru);
                }
                return users;
            });
        }catch (Exception e){
            return new ArrayList<>();
        }
        return followers;
    }

    @Override
    public List<RegisteredUser> getFollower(RegisteredUser user, int size, int page){

        if(user == null)
            return null;

        List<RegisteredUser> followers;
        try (Session session = getConnection().session()) {
            followers = session.readTransaction(tx -> {
                Result result = tx.run("MATCH (u1:RegisteredUser {username:$username})<-[:FOLLOW]-(u2:RegisteredUser)"+
                                "RETURN u2.username " +
                                "SKIP $skip " +
                                "LIMIT $limit",
                        parameters("username", user.getUsername(), "skip", ((page-1)*size), "limit", size));
                List<RegisteredUser> users = new ArrayList<>();
                while (result.hasNext()) {
                    Record r = result.next();
                    RegisteredUser ru = new RegisteredUser();
                    ru.setUsername(r.get("u2.username").asString());
                    users.add(ru);
                }
                return users;
            });
        }catch (Exception e){
            return new ArrayList<>();
        }
        return followers;
    }

    @Override
    public int getNumberOfFollower(RegisteredUser user){

        if(user == null)
            return 0;

        int followers;
        try (Session session = getConnection().session()) {
            followers = session.readTransaction(tx -> {
                Result result = tx.run(" MATCH (user:RegisteredUser {username:$username})<-[:FOLLOW]-()"+
                                " RETURN count(*) as in",
                        parameters("username", user.getUsername()));
                if(result.hasNext())
                    return result.next().get("in").asInt();
                else
                    return 0;
            });
        }catch(Exception e){
                return 0;
            }
        return followers;
    }

    @Override
    public int getNumberOfFollowing(RegisteredUser user){

        if(user == null)
            return 0;

        int followers = 0;
        try (Session session = getConnection().session()) {
            followers = session.readTransaction(tx -> {
                Result result = tx.run("MATCH (user:RegisteredUser {username:$username})-[:FOLLOW]->()"+
                                " RETURN count(*) as out",
                        parameters("username", user.getUsername()));
                if(result.hasNext())
                    return result.next().get("out").asInt();
                else
                    return 0;
            });
        }catch(Exception e){
            return 0;
        }
        return followers;
    }

    @Override
    public void follow(RegisteredUser user_1, RegisteredUser user_2) throws Neo4jException {

        if(user_1 == null || user_2 == null)
            throw new Neo4jException();

        try (Session session = getConnection().session()) {
            Boolean exists = session.readTransaction(tx->{
                Result res = tx.run("MATCH (u1:RegisteredUser {username: $u1})-[f:FOLLOW]->(u2:RegisteredUser{username: $u2}) " +
                                    "RETURN f",
                        parameters("u1", user_1.getUsername(), "u2", user_2.getUsername()));
                return res.hasNext();

            });
            if(!exists){
                session.writeTransaction(tx -> {
                    tx.run("MATCH (u1:RegisteredUser{username: $u1}), (u2:RegisteredUser{username: $u2}) " +
                                    "CREATE (u1)-[:FOLLOW]->(u2)",
                            parameters("u1", user_1.getUsername(), "u2", user_2.getUsername()));
                    return null;
                });
            }else{
                throw new Exception();
            }
        }catch (Exception e){
            System.out.println(e);
            throw new Neo4jException(e.getMessage());
        }
    }

    @Override
    public void unfollow(RegisteredUser user_1, RegisteredUser user_2) throws Neo4jException {

        if(user_1 == null || user_2 == null)
            throw new Neo4jException();

        try (Session session = getConnection().session()) {
            Boolean exists = session.readTransaction(tx->{
                Result res = tx.run("MATCH (u1:RegisteredUser {username: $u1})-[f:FOLLOW]->(u2:RegisteredUser{username: $u2}) " +
                                "RETURN f",
                        parameters("u1", user_1.getUsername(), "u2", user_2.getUsername()));
                return res.hasNext();

            });
            if(exists){  //username is new
                session.writeTransaction(tx -> {
                    tx.run("MATCH (u1:RegisteredUser{username: $u1})-[f:FOLLOW]->(u2:RegisteredUser{username: $u2}) " +
                                    "DELETE f",
                            parameters("u1", user_1.getUsername(), "u2", user_2.getUsername()));
                    return null;
                });
            }else{
                throw new Exception();
            }
        }catch (Exception e){
            throw new Neo4jException(e.getMessage());
        }

    }

    @Override
    public boolean isFriend(RegisteredUser user_1, RegisteredUser user_2) throws Neo4jException {

        if(user_1 == null || user_2 == null)
            throw new Neo4jException();

        try (Session session = getConnection().session()) {
            Boolean exists = session.readTransaction(tx->{
                Result res = tx.run("MATCH (u1:RegisteredUser {username: $u1})-[f:FOLLOW]->(u2:RegisteredUser{username: $u2}) " +
                                "RETURN f",
                        parameters("u1", user_1.getUsername(), "u2", user_2.getUsername()));
                return res.hasNext();

            });
            return exists;
        }catch (Exception e){
            throw new Neo4jException(e.getMessage());
        }
    }

    @Override
    public void createRegistereduser(RegisteredUser user) throws Neo4jException {

        if(user == null)
            throw new Neo4jException();


        try (Session session = getConnection().session()) {
             Boolean exists = session.readTransaction(tx->{
                 Result res = tx.run("MATCH (r:RegisteredUser {username: $username}) RETURN r",
                        parameters("username",user.getUsername()));
                 return res.hasNext();

             });
            if(!exists){  //username is new
                session.writeTransaction(tx -> {
                    tx.run("CREATE (x:RegisteredUser { username: $username})",
                            parameters("username",user.getUsername())).consume();
                        return null;
                });
            }else{
                throw new Exception();
            }
        }catch (Exception e){
            throw new Neo4jException(e.getMessage());
        }
    }


    private void deleteAllFollowingRelationshipRegisteredUser(RegisteredUser user) throws Neo4jException {

        if(user == null)
            throw new Neo4jException();

        try (Session session = getConnection().session()) {
            session.writeTransaction(tx -> {
                    tx.run("MATCH (x:RegisteredUser {username: $username}) -[f1:FOLLOW]->(), (x)<-[f2:FOLLOW]-() " +
                                    "DELETE f1,f2",
                            parameters("username", user.getUsername())).consume();
                    return null;
                });

        }catch (Exception e){
            throw new Neo4jException(e.getMessage());
        }
    }


    private void deleteAllFutureOrganizedTrip(RegisteredUser user) throws Neo4jException {

        if(user == null)
            throw new Neo4jException();

        try (Session session = getConnection().session()) {
            List<String> ids =session.readTransaction(tx -> {
                Result r = tx.run("MATCH (x:RegisteredUser {username: $username})<-[:ORGANIZED_BY]-(t:Trip) " +
                                "WHERE t.departureDate > date() return t._id",
                        parameters("username",user.getUsername()));
                List<String> results = new ArrayList<>();
                while(r.hasNext()){
                    results.add(String.valueOf(r.next().get("t._id")));
                }
                return results;
            });
            for(String id : ids){
                Trip t= new Trip();
                t.setId(id);
                DAOLocator.getTripDAO().deleteTrip(t);
            }
        }catch (Exception e){
            throw new Neo4jException(e.getMessage());
        }
    }

    @Override
    public void deleteUser(RegisteredUser u) throws Neo4jException {

        if(u == null)
            throw new Neo4jException();

        deleteAllFollowingRelationshipRegisteredUser(u);
        deleteAllFutureOrganizedTrip(u);
        if(!incidentEdges(u)){
            try (Session session = getConnection().session()) {
                session.writeTransaction(tx -> {
                    tx.run("MATCH (x:RegisteredUser {username: $username}) " +
                                    "DELETE x",
                            parameters("username", u.getUsername())).consume();
                    return null;
                });

            }catch (Exception e){
                throw new Neo4jException(e.getMessage());
            }
        }
    }

    private boolean incidentEdges(RegisteredUser u) throws Neo4jException {

        if(u == null)
            throw new Neo4jException();

        try (Session session = getConnection().session()) {
                Boolean flag = session.readTransaction(tx -> {
                    Result r = tx.run("MATCH (r:RegisteredUser {username: $username})-[]->(), (r)<-[]-() " +
                                    "RETURN r LIMIT 1",
                            parameters("username", u.getUsername()));
                    return r.hasNext();
                });
                return flag;
        }catch (Exception e){
            throw new Neo4jException(e.getMessage());
        }

    }
}
