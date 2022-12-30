package it.unipi.lsmd.dao;

import it.unipi.lsmd.dao.neo4j.exceptions.Neo4jException;
import it.unipi.lsmd.model.RegisteredUser;

import java.util.List;

public interface RegisteredUserDAO {
    List<RegisteredUser> getSuggestedUser(String username, int nUser);
    List<RegisteredUser> getFollowing(String username, int size, int page);

    List<RegisteredUser> getFollower(String username, int size, int page);

    int getNumberOfFollower(String username);

    int getNumberOfFollowing(String username);


    void createRegistereduser(RegisteredUser user) throws Neo4jException;

    void deleteAllFollowingRelationshipRegisteredUser(RegisteredUser user) throws Neo4jException;

    void deleteAllFutureOrganizedTrip(RegisteredUser user) throws Neo4jException;

    void updateRegisteredUser(RegisteredUser r) throws Neo4jException;
}
