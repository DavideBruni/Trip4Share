package it.unipi.lsmd.dao.mongo;

import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Updates;
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
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Updates.pushEach;

public class UserMongoDAO extends BaseDAOMongo implements UserDAO {

    private final MongoCollection<Document> collection = getConnection().getCollection("users");

    @Override
    public User authenticate(RegisteredUser registeredUser) {

        if(registeredUser == null)
            return null;

        User user;
        Bson query = and(eq("username", registeredUser.getUsername()), eq("password", registeredUser.getPassword()));
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
    public RegisteredUser getUser(RegisteredUser registeredUser) {

        if(registeredUser == null)
            return null;

        Bson query = eq("username", registeredUser.getUsername());
        Bson projection = fields(exclude("password"), slice("reviews", 0, PagesUtilis.REVIEWS_IN_USER_PROFILE));
        Document result = collection.find(query).projection(projection).first();
        User u = UserUtils.userFromDocument(result);

        if(u instanceof Admin){
            return null;
        }else{
            return (RegisteredUser) u;
        }
    }

    public List<RegisteredUser> searchUser(RegisteredUser registeredUser, int limit, int page){

        if(registeredUser == null)
            return null;

        Bson m1 = match(and(regex("username",".*"+registeredUser.getUsername()+".*","i"),eq("type","user")));
        Bson l1 = limit(limit);
        AggregateIterable<Document> res;

        if(page!=1) {
            Bson s1 = skip((page - 1) * limit);
            res =collection.aggregate(Arrays.asList(m1, s1, l1));
        }else{
            res = collection.aggregate(Arrays.asList(m1,l1));
        }
        List<RegisteredUser> searchRes = new ArrayList<>();

        try(MongoCursor<Document> result = res.iterator()) {
            while (result.hasNext()) {
                Document doc = result.next();
                RegisteredUser u = (RegisteredUser) UserUtils.userFromDocument(doc);
                searchRes.add(u);
            }
        }catch (Exception e){
            return null;
        }
        return searchRes;
    }

    @Override
    public List<Review> getReviews(RegisteredUser registeredUser, int limit, int page) {

        if(registeredUser == null)
            return null;


        List<Review> reviews = new ArrayList<>();
        Bson match = match(eq("username", registeredUser.getUsername()));
        Bson unwind = unwind("$reviews");
        Bson project = project(fields(excludeId(), include("reviews")));
        Bson sort = sort(descending("reviews.date"));
        Bson skip = skip((page - 1) * limit);
        Bson lim = limit(limit);
        AggregateIterable<Document> results = collection.aggregate(Arrays.asList(match, unwind, project, sort, skip, lim));

        try(MongoCursor<Document> documents = results.iterator()){
            while (documents.hasNext()){
                Document result = (Document) documents.next().get("reviews");
                reviews.add(ReviewUtils.reviewFromDocument(result));
            }
        }catch (Exception e){
            return null;
        }

        return reviews;
    }

    @Override
    public double avgRating(RegisteredUser registeredUser){

        if(registeredUser == null)
            return 0.0;

        Bson m1 = match(eq("username", registeredUser.getUsername()));
        Bson u1 = unwind("$reviews");
        Bson g1 = group("$username",avg("rating","$reviews.value"));
        AggregateIterable<Document> res = collection.aggregate(Arrays.asList(m1,u1,g1));
        try(MongoCursor<Document> result = res.iterator()){
            if(result.hasNext()){
                return Math.round(result.next().getDouble("rating") * 100) / 100.0;
            }
        }catch (Exception e){
            return 0.0;
        }
        return 0.0;
    }

    @Override
    public String createUser(User u){

        Document doc = UserUtils.documentFromUser(u);
        if(doc != null){
            try{
                return collection.withWriteConcern(WriteConcern.W3).insertOne(doc).getInsertedId().asObjectId().getValue().toString();
            }catch(DuplicateKeyException de){
                return "Duplicate key";
            }catch(MongoException | NullPointerException me){
                return "Something gone wrong";
            }
        }
        return "Something gone wrong";
    }

    @Override
    public boolean deleteUser(User u){
        try {
            Bson q1 = eq("username", u.getUsername());
            collection.deleteOne(q1);
        }catch(MongoException | NullPointerException me){
            return false;
        }
        return true;
    }

    @Override
    public boolean updateRegisteredUser(RegisteredUser new_user, RegisteredUser old_user) {

        try {
            Bson q1 = eq("username",new_user.getUsername());
            Bson q2 = attributeToUpdate(new_user, old_user);
            collection.updateOne(q1, q2);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean updateAdmin(Admin new_user, Admin old_user) {

        try {
            Bson q1 = eq("username",new_user.getUsername());
            Bson q2 = attributeToUpdate(new_user, old_user);
            collection.updateOne(q1, q2);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    private Bson attributeToUpdate(User new_user, User old_user){

        List<Bson> query = new ArrayList<>();
        if(!new_user.getName().equals("") && !new_user.getName().equals(old_user.getName()))
            query.add(Updates.set("name", new_user.getName()));
        if(!new_user.getSurname().equals("") && !new_user.getSurname().equals(old_user.getSurname()))
            query.add(Updates.set("surname", new_user.getSurname()));
        if(!new_user.getEmail().equals("") && !new_user.getEmail().equals(old_user.getEmail()))
            query.add(Updates.set("email", new_user.getEmail()));
        if(!new_user.getPassword().equals("") && !new_user.getPassword().equals(old_user.getPassword()))
            query.add(Updates.set("password", new_user.getPassword()));

        if(new_user instanceof RegisteredUser && old_user instanceof RegisteredUser){
            if(((RegisteredUser) new_user).getBirthdate() != null && ((RegisteredUser) new_user).getBirthdate() != ((RegisteredUser) old_user).getBirthdate())
                query.add(Updates.set("birthdate", ((RegisteredUser) new_user).getBirthdate()));
            if(((RegisteredUser) new_user).getNationality() != null && !((RegisteredUser) new_user).getNationality().equals(((RegisteredUser) old_user).getNationality()))
                query.add(Updates.set("nationality", ((RegisteredUser) new_user).getNationality()));
            if(((RegisteredUser) new_user).getSpoken_languages() != null && !((RegisteredUser) new_user).getSpoken_languages().equals(((RegisteredUser) old_user).getSpoken_languages()))
                query.add(Updates.set("spoken_languages", ((RegisteredUser) new_user).getSpoken_languages()));
        }

        return Updates.combine(query);
    }

    @Override
    public boolean putReview(Review review, RegisteredUser to) {

        if(review == null || to == null)
            return false;

        Document doc = docFromReview(review,true);
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

        if(review == null || r == null)
            return false;

        Document doc = docFromReview(review,false);
        Bson bson = eq("username",r.getUsername());
        try{
            collection.updateOne(bson,Updates.pull("reviews",doc));
            return true;
        }catch (MongoException me){
            return false;
        }
    }

    private Document docFromReview(Review review, boolean extended){

        Document doc = new Document();
        doc.append("author", review.getAuthor().getUsername());
        if(extended) {
            doc.append("title",review.getTitle());
            doc.append("text", review.getText());
            doc.append("value", review.getRating());
        }
        doc.append("date",review.getDate());
        return doc;
    }
}
