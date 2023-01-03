package it.unipi.lsmd.dao.mongo;

import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoException;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import it.unipi.lsmd.dao.UserDAO;
import it.unipi.lsmd.dao.base.BaseDAOMongo;
import it.unipi.lsmd.model.Admin;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Review;
import it.unipi.lsmd.model.User;
import it.unipi.lsmd.utils.PagesUtilis;
import it.unipi.lsmd.utils.ReviewUtils;
import it.unipi.lsmd.utils.UserUtils;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Accumulators.avg;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Updates.pull;
import static com.mongodb.client.model.Updates.pushEach;

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
        Bson projection = fields(slice("reviews", 0, PagesUtilis.REVIEWS_IN_USER_PROFILE));
        Document result = collection.find(query).projection(projection).first();
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
        Bson projection = fields(exclude("email", "password"), slice("reviews", 0, PagesUtilis.REVIEWS_IN_USER_PROFILE));
        Document result = collection.find(query).projection(projection).first();
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
            res =collection.aggregate(Arrays.asList(m1, s1, l1));
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
    public List<Review> getReviews(String username, int limit, int page) {
        Bson query = and(eq("username", username));
        Bson projection = fields(include("reviews"), excludeId(), slice("reviews", (page - 1)*(limit - 1), limit)); // TODO - non funziona

        List<Review> reviews = new ArrayList<Review>();
        Document document = collection.find(query).projection(projection).first();

        for(Object review : document.get("reviews", ArrayList.class)){
            Document d = (Document) review;
            reviews.add(ReviewUtils.reviewFromDocument(d));
        }

        return reviews;
    }

    @Override
    public double avgRating(String username){
        Bson m1 = match(eq("username", username));
        Bson u1 = unwind("$reviews");
        Bson g1 = group("$username",avg("rating","$reviews.value"));
        AggregateIterable<Document> res = collection.aggregate(Arrays.asList(m1,u1,g1));
        MongoCursor<Document> result = res.iterator();
        if(result.hasNext()){
            return result.next().getDouble("rating");
        }
        return 0.0;
    }

    @Override
    public String createUser(User u){
        Document doc =UserUtils.documentFromUser(u);
        if(doc!=null){
            try{
                return collection.insertOne(doc).getInsertedId().asObjectId().getValue().toString();
            }catch(DuplicateKeyException de){
                return "Duplicate key";
            }catch(MongoException me){
                return "Something gone wrong";
            }
        }
        return "Something gone wrong";
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

    @Override
    public boolean updateRegisteredUser(RegisteredUser new_user, RegisteredUser old_user) {
        try {

            // TODO - nel fare la merge ho commentato questa, mi sembra meglio l'altra in quanto il campo _id non ha nulla a che fare con new_user.getUsername()
            // Document q1 = new Document().append("_id", new ObjectId(new_user.getUsername()));

            Bson q1 = eq("username",new_user.getUsername());

            Bson q2 = attributeToUpdate(new_user, old_user);
            collection.updateOne(q1, q2);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    private Bson attributeToUpdate(RegisteredUser new_user, RegisteredUser old_user){
        List<Bson> query = new ArrayList<>();
        if(new_user.getBio()!=old_user.getBio()){
            query.add(Updates.set("bio",new_user.getBio()));
        }
        if(new_user.getName()!=old_user.getName()){
            query.add(Updates.set("name",new_user.getName()));
        }
        if(new_user.getSurname()!=old_user.getSurname()){
            query.add(Updates.set("surname",new_user.getSurname()));
        }
        if(new_user.getBirthdate()!=old_user.getBirthdate()){
            query.add(Updates.set("birthdate",new_user.getBirthdate()));
        }
        if(new_user.getPhone()!=old_user.getPhone()){
            query.add(Updates.set("phone",new_user.getPhone()));
        }
        if(new_user.getNationality()!=old_user.getNationality()){
            query.add(Updates.set("nationality",new_user.getNationality()));
        }
        if(new_user.getEmail()!=old_user.getEmail()){
            query.add(Updates.set("email",new_user.getEmail()));
        }
        if(!new_user.getSpoken_languages().equals(old_user.getSpoken_languages())){
            query.add(Updates.set("spoken_languages",new_user.getSpoken_languages()));
        }
        query.add(Updates.set("password",new_user.getPassword()));
        return Updates.combine(query);
    }

    @Override
    public boolean putReview(Review review, RegisteredUser to) {
        Document doc = docFromReview(review);
        List<Document> rev = new ArrayList<>();
        rev.add(doc);
        try {
            collection.findOneAndUpdate(eq("username", to.getUsername()), pushEach("reviews", rev));
        }catch (MongoException me){
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteReview(Review review, RegisteredUser r) {
        Document doc = docFromReview(review);
        Bson bson = eq("reviews",doc);
        try{
            collection.updateOne(bson,Updates.pull("reviews",doc));
            return true;
        }catch (MongoException me){
            return false;
        }
    }

    private Document docFromReview(Review review){
        Document doc = new Document();
        doc.append("title",review.getTitle());
        doc.append("text",review.getText());
        doc.append("value",review.getRating());
        doc.append("author",review.getAuthor());
        doc.append("date",review.getDate());
        return doc;
    }
}
