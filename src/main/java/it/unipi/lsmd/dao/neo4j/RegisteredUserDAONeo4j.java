package it.unipi.lsmd.dao.neo4j;

import it.unipi.lsmd.dao.RegisteredUserDAO;
import it.unipi.lsmd.dao.base.BaseDAONeo4J;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Trip;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.exceptions.value.Uncoercible;

import java.util.ArrayList;
import java.util.List;

import static org.neo4j.driver.Values.parameters;

public class RegisteredUserDAONeo4j extends BaseDAONeo4J implements RegisteredUserDAO {

    public List<RegisteredUser> getSuggestedUser(String username){
        List<RegisteredUser> suggested;
        try (Session session = getConnection().session()) {
            suggested = session.readTransaction(tx -> {
                Result result = tx.run("MATCH (u1:RegisteredUser {username:$username})-[:FOLLOW]->(u2:RegisteredUser)," +
                                "(u2)-[:FOLLOW]->(u3:RegisteredUser) WHERE u1.username <> u3.username AND " +
                                "(NOT (u1)-[:FOLLOW]->(u3)) RETURN u3.username",
                        parameters("username", username));
                List users = new ArrayList<>();
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
}