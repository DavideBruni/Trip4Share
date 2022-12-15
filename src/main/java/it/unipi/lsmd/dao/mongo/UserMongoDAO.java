package it.unipi.lsmd.dao.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import it.unipi.lsmd.dao.UserDAO;
import it.unipi.lsmd.dao.base.BaseDAOMongo;
import it.unipi.lsmd.model.Admin;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.User;
import it.unipi.lsmd.utils.UserUtils;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;

import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;

public class UserMongoDAO extends BaseDAOMongo implements UserDAO {

    @Override
    public RegisteredUser register(RegisteredUser user) {
        return null;
    }

    /**
    @return an Admin or a RegisteredUser
     **/
    @Override
    public User authenticate(String username, String password) {

        MongoDatabase database = getConnection();
        MongoCollection<Document> users = database.getCollection("users");

        User user = null;

        Bson query = and(eq("username", username), eq("password", password));
        Document result = users.find(query).first();

        try{
            user = UserUtils.userFromDocument(result);
        }catch (NullPointerException e){
            System.out.println("Error 404: user not found!");   // TODO - return UserNotFound page?
            return null;
        }

        return user;
    }

    @Override
    public RegisteredUser getUser(String username) {

        MongoDatabase database = getConnection();
        MongoCollection<Document> users = database.getCollection("users");

        RegisteredUser user = null;

        Bson query = and(eq("username", username));
        Document result = users.find(query).first();

        try{
            user = new RegisteredUser(result.getString("username"));
            user.setName(result.getString("name"));
            user.setSurname(result.getString("surname"));
            user.setEmail(result.getString("email"));
            //TODO add other fields

        }catch (NullPointerException e){
            System.out.println("Error 404: user not found!");   // TODO - return UserNotFound page?
            return null;
        }


        return user;
    }
}
