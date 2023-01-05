package it.unipi.lsmd.dao;

import it.unipi.lsmd.dao.neo4j.exceptions.Neo4jException;
import it.unipi.lsmd.model.RegisteredUser;

import java.util.List;

public interface RegisteredUserDAO {
    List<RegisteredUser> getSuggestedUser(RegisteredUser user, int nUser);
    List<RegisteredUser> getFollowing(RegisteredUser user, int size, int page);

    List<RegisteredUser> getFollower(RegisteredUser user, int size, int page);

    int getNumberOfFollower(RegisteredUser user);

    int getNumberOfFollowing(RegisteredUser user);

    void follow(RegisteredUser user_1, RegisteredUser user_2) throws Neo4jException;

    void unfollow(RegisteredUser user_1, RegisteredUser user_2) throws Neo4jException;

    boolean isFriend(RegisteredUser user_1, RegisteredUser user_2) throws Neo4jException;

    void createRegistereduser(RegisteredUser user) throws Neo4jException;

    void deleteUser(RegisteredUser u) throws Neo4jException;
}
