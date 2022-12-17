package it.unipi.lsmd.utils;

import it.unipi.lsmd.dto.ReviewDTO;
import it.unipi.lsmd.model.Admin;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Review;
import it.unipi.lsmd.model.User;
import org.bson.Document;

import java.util.ArrayList;

public class ReviewUtils {

    public static Review reviewFromDocument(Document result){

        Review review = new Review();

        review.setTitle(result.getString("title"));
        review.setText(result.getString("text"));
        review.setRating(result.getInteger("value"));
        review.setDate(result.getString("date"));

        return review;
    }

    public static ReviewDTO reviewModelToDTO(Review review_model){

        ReviewDTO reviewDTO = new ReviewDTO();

        reviewDTO.setTitle(review_model.getTitle());
        reviewDTO.setText(review_model.getText());
        reviewDTO.setRating(review_model.getRating());
        reviewDTO.setDate(reviewDTO.getDate());

        return reviewDTO;
    }


}
