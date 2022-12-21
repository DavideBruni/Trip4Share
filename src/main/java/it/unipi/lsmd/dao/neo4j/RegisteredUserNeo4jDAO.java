package it.unipi.lsmd.dao.neo4j;

import it.unipi.lsmd.dao.RegisteredUserDAO;
import it.unipi.lsmd.dao.base.BaseDAONeo4J;
import it.unipi.lsmd.dao.neo4j.exceptions.Neo4jException;
import it.unipi.lsmd.model.RegisteredUser;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import java.util.ArrayList;
import java.util.List;

import static org.neo4j.driver.Values.parameters;

public class RegisteredUserNeo4jDAO extends BaseDAONeo4J implements RegisteredUserDAO {

    public List<RegisteredUser> getSuggestedUser(String username, int nUser){
        List<RegisteredUser> suggested;
        try (Session session = getConnection().session()) {
            suggested = session.readTransaction(tx -> {
                Result result = tx.run("MATCH (u1:RegisteredUser {username:$username})-[:FOLLOW]->(u2:RegisteredUser)," +
                                "(u2)-[:FOLLOW]->(u3:RegisteredUser) WHERE u1.username <> u3.username AND " +
                                "(NOT (u1)-[:FOLLOW]->(u3)) RETURN u3.username LIMIT $limit",
                        parameters("username", username, "limit",nUser));
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
    public List<RegisteredUser> getFollowing(String username){
        List<RegisteredUser> followers;
        try (Session session = getConnection().session()) {
            followers = session.readTransaction(tx -> {
                Result result = tx.run("MATCH (u1:RegisteredUser {username:$username})-[:FOLLOW]->(u2:RegisteredUser)"+
                                "RETURN u2.username",
                        parameters("username", username));
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
    public List<RegisteredUser> getFollower(String username){
        List<RegisteredUser> followers;
        try (Session session = getConnection().session()) {
            followers = session.readTransaction(tx -> {
                Result result = tx.run("MATCH (u1:RegisteredUser {username:$username})<-[:FOLLOW]-(u2:RegisteredUser)"+
                                "RETURN u2.username",
                        parameters("username", username));
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
    public int getNumberOfFollower(String username){
        int followers = 0;
        try (Session session = getConnection().session()) {
            followers = session.readTransaction(tx -> {
                Result result = tx.run(" MATCH (user:RegisteredUser {username:$username})"+
                                " WITH size((user)<-[:FOLLOW]-()) as in RETURN in",
                        parameters("username", username));
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
    public int getNumberOfFollowing(String username){
        int followers = 0;
        try (Session session = getConnection().session()) {
            followers = session.readTransaction(tx -> {
                Result result = tx.run(" MATCH (user:RegisteredUser {username:$username})"+
                                " WITH size((user)-[:FOLLOW]->()) as in RETURN out",
                        parameters("username", username));
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
    public void saveRegistereduser(RegisteredUser user) throws Neo4jException {
        try (Session session = getConnection().session()) {
            session.writeTransaction(tx -> {
                tx.run("CREATE (x:RegisteredUser { username: $username})",
                        parameters("username",user.getUsername())).consume();
                    return null;
            });
        }catch (Exception e){
            throw new Neo4jException(e.getMessage());
        }
    }

    @Override
    public void deleteAllFollowingRelationshipRegisteredUser(RegisteredUser user) throws Neo4jException {
        try (Session session = getConnection().session()) {
            session.writeTransaction(tx -> {
                tx.run("MATCH (x:RegisteredUser {username: $username}) -[f1:FOLLOW]->(), (x)<-[f2:FOLLOW]-()" +
                                "DELETE f1,f2",
                        parameters("username",user.getUsername())).consume();
                return null;
            });
        }catch (Exception e){
            throw new Neo4jException(e.getMessage());
        }
    }

    @Override
    public void deleteAllFutureOrganizedTrip(RegisteredUser user) throws Neo4jException {
        try (Session session = getConnection().session()) {
            session.writeTransaction(tx -> {
                tx.run("MATCH (x:RegisteredUser {username: $username})<-[:ORGANIZED_BY]-(t:Trip)" +
                                "WHERE t.departureDate > date() SET t.deleted = TRUE return t",
                        parameters("username",user.getUsername())).consume();
                return null;
            });
        }catch (Exception e){
            throw new Neo4jException(e.getMessage());
        }
    }
}
