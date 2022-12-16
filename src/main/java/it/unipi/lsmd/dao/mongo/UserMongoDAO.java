package it.unipi.lsmd.dao.mongo;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import it.unipi.lsmd.dao.UserDAO;
import it.unipi.lsmd.dao.base.BaseDAOMongo;
import it.unipi.lsmd.model.Admin;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Review;
import it.unipi.lsmd.model.User;
import it.unipi.lsmd.utils.UserUtils;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
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
    public RegisteredUser getUser(String username) {
        MongoDatabase database = getConnection();
        MongoCollection<Document> users = database.getCollection("users");
        RegisteredUser user = null;
        Bson query = eq("username", username);
        Document result = users.find(query).first();

        User u = UserUtils.userFromDocument(result);
        if(u instanceof Admin){
            return null;
        }else{
            return (RegisteredUser) u;
        }
    }

    public List<RegisteredUser> searchUser(String username, int limit, int page){
        MongoDatabase database = getConnection();
        MongoCollection<Document> collection = database.getCollection("users");
        RegisteredUser user = null;
        Bson m1 = match(eq("username", "/.*"+username+".*/i"));
        Bson l1 = limit(limit);
        AggregateIterable<Document> res;
        if(page!=1) {
            Bson s1 = skip((page - 1) * limit);
            res =collection.aggregate(Arrays.asList(m1,l1,s1));
        }else{
            res = collection.aggregate(Arrays.asList(m1,l1));
        }
        List<RegisteredUser> searchRes = new ArrayList<>();
        while(res.iterator().hasNext()){
            Document doc = res.iterator().next();
            RegisteredUser u = (RegisteredUser) UserUtils.userFromDocument(doc);
            searchRes.add(u);
        }
        return searchRes;
    }
}
