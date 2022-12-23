package it.unipi.lsmd.dao.mongo;

import com.mongodb.MongoException;
import com.mongodb.client.AggregateIterable;
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
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Accumulators.avg;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.include;

public class UserMongoDAO extends BaseDAOMongo implements UserDAO {

    private final MongoCollection<Document> collection = getConnection().getCollection("users");
    @Override
    public RegisteredUser register(RegisteredUser user) {
        return null;
    }

    /**
    @return an Admin or a RegisteredUser
     **/
    @Override
    public User authenticate(String username, String password) {

        User user;
        Bson query = and(eq("username", username), eq("password", password));
        Document result = collection.find(query).first();

        try{
            user = UserUtils.userFromDocument(result);
        }catch (NullPointerException e){
            return null;
        }

        return user;
    }

    @Override

    public RegisteredUser getUser(String username) {

        Bson query = eq("username", username);

        Document result = collection.find(query).first();
        User u = UserUtils.userFromDocument(result);
        if(u instanceof Admin){
            return null;
        }else{
            return (RegisteredUser) u;
        }
    }

    public List<RegisteredUser> searchUser(String username, int limit, int page){
        Bson m1 = match(and(regex("username",".*"+username+".*","i"),eq("type","user")));
        Bson l1 = limit(limit);
        AggregateIterable<Document> res;
        if(page!=1) {
            Bson s1 = skip((page - 1) * limit);
            res =collection.aggregate(Arrays.asList(m1,l1,s1));
        }else{
            res = collection.aggregate(Arrays.asList(m1,l1));
        }
        List<RegisteredUser> searchRes = new ArrayList<>();
        MongoCursor<Document> result = res.iterator();
        while(result.hasNext()){
            Document doc = result.next();
            RegisteredUser u = (RegisteredUser) UserUtils.userFromDocument(doc);
            searchRes.add(u);
        }
        return searchRes;
    }

    @Override
    public double avgRating(String username){
        Bson m1 = match(eq("username", username));
        Bson u1 = unwind("reviews");
        Bson g1 = group("$username",avg("rating","$reviews.value"));
        AggregateIterable<Document> res = collection.aggregate(Arrays.asList(m1,u1,g1));
        MongoCursor<Document> result = res.iterator();
        if(result.hasNext()){
            return result.next().getDouble("rating");
        }
        return 0.0;
    }

    @Override
    public boolean createUser(User u){
        Document doc =UserUtils.documentFromUser(u);
        if(doc!=null){
            try{
                collection.insertOne(doc);
                return true;
            }catch(MongoException me){
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean deleteUser(User u){
        try {
            Bson q1 = eq("username",u.getUsername());
            collection.deleteOne(q1);
        }catch(MongoException me){
            return false;
        }
        return true;
    }
}
