package it.unipi.lsmd.dao;

import it.unipi.lsmd.model.Admin;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Review;
import it.unipi.lsmd.model.User;

import java.util.List;

public interface UserDAO {

    /**
     @param registeredUser  user
     @return an Admin or a RegisteredUser
     **/
    User authenticate(RegisteredUser registeredUser);

    RegisteredUser getUser(RegisteredUser registeredUser);

    List<RegisteredUser> searchUser(RegisteredUser registeredUser, int limit, int page);

    List<Review> getReviews(RegisteredUser registeredUser, int limit, int page);

    double avgRating(RegisteredUser registeredUser);

    String createUser(User u);

    boolean deleteUser(User u);

    boolean updateRegisteredUser(RegisteredUser new_user, RegisteredUser old_user);

    boolean updateAdmin(Admin new_user, Admin old_user);

    boolean putReview(Review review, RegisteredUser to);

    boolean deleteReview(Review review, RegisteredUser r);
}
