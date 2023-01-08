package it.unipi.lsmd.utils;

import it.unipi.lsmd.dto.ReviewDTO;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Review;
import org.bson.Document;


public class ReviewUtils {

    public static Review reviewFromDocument(Document result){

        if(result == null)
            return  null;

        Review review = new Review();

        String username = result.getString("author");
        if(username == null || username.equals("")){
            review.setAuthor(null);
        }else{
            review.setAuthor(new RegisteredUser(username));
        }

        review.setTitle(result.getString("title"));
        review.setText(result.getString("text"));
        review.setRating(result.getInteger("value"));
        review.setDate(LocalDateAdapter.convertToLocalDateViaInstant(result.getDate("date")));

        return review;
    }

    public static ReviewDTO reviewModelToDTO(Review review_model){

        if(review_model == null)
            return null;

        ReviewDTO reviewDTO = new ReviewDTO();

        try{
            reviewDTO.setAuthor(review_model.getAuthor().getUsername());
        }catch (NullPointerException e){
            reviewDTO.setAuthor(null);
        }

        reviewDTO.setTitle(review_model.getTitle());
        reviewDTO.setText(review_model.getText());
        reviewDTO.setRating(review_model.getRating());
        reviewDTO.setDate(review_model.getDate());

        return reviewDTO;
    }


}
