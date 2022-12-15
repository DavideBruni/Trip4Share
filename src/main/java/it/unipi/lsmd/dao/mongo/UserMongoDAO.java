package it.unipi.lsmd.dao.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import it.unipi.lsmd.dao.UserDAO;
import it.unipi.lsmd.dao.base.BaseDAOMongo;
import it.unipi.lsmd.model.Admin;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.User;
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
            if (result.getString("type").equals("admin")){
                user = new Admin(result.getString("username"));

            }else{
                RegisteredUser registeredUser = new RegisteredUser(result.getString("username"));
                registeredUser.setNationality(result.getString("nationality"));
                //registeredUser.setReviews();
                //registeredUser.setSponken_languages();
                user = registeredUser;
            }
        }catch (NullPointerException e){
            System.out.println("Error 404: user not found!");   // TODO - return UserNotFound page?
            return null;
        }
        user.setName(result.getString("name"));
        user.setSurname(result.getString("surname"));
        user.setEmail(result.getString("email"));

        return user;

    }
}
