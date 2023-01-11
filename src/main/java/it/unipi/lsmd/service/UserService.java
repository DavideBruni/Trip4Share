package it.unipi.lsmd.service;

import it.unipi.lsmd.dto.*;

import java.util.List;

public interface UserService {

    AuthenticatedUserDTO authenticate(String username, String password);

    RegisteredUserDTO getUser(String username, String me);

    List<OtherUserDTO> getSuggestedUsers(String username, int nUsers);

    List<OtherUserDTO> getFollowers(String username, int size, int page);

    List<OtherUserDTO> getFollowing(String username, int size, int page);

    String follow(String user_1, String user_2);

    String unfollow(String user_1, String user_2);

    boolean isFriend(String user_1, String user_2);

    List<OtherUserDTO> searchUsers(String username, int limit, int page);

    List<ReviewDTO> getReviews(String username, int limit, int page);

    boolean updateUser(AuthenticatedUserDTO newUser, AuthenticatedUserDTO oldUser);

    String signup(AuthenticatedUserDTO user);

    boolean deleteUser(String username);

    boolean setReview(ReviewDTO reviewDTO,OtherUserDTO to);

    boolean deleteReview(ReviewDTO review, OtherUserDTO toDTO);
}
