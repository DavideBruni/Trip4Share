package it.unipi.dii.lsmd.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "users")
public class RegisteredUser extends User{

    private List<Review> reviews;
}
