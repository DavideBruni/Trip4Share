package it.unipi.lsmd.dao.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import it.unipi.lsmd.dao.UserDAO;
import it.unipi.lsmd.dao.base.BaseDAOMongo;
import it.unipi.lsmd.model.Admin;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Review;
import it.unipi.lsmd.model.User;
import it.unipi.lsmd.utils.UserUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
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
            return null;
        }

        return user;
    }

    @Override
    public RegisteredUser getUser(String user_id) {

        MongoDatabase database = getConnection();
        MongoCollection<Document> users = database.getCollection("users");

        RegisteredUser user = null;

        Bson query = eq("_id", new ObjectId(user_id));
        Document result = users.find(query).first();

        User u = UserUtils.userFromDocument(result);
        if(u instanceof Admin){
            return null;
        }else{
            return (RegisteredUser) u;
        }
    }
}
