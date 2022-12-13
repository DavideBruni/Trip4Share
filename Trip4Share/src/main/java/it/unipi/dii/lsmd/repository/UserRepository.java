package it.unipi.dii.lsmd.repository;

import it.unipi.dii.lsmd.model.RegisteredUser;
import it.unipi.dii.lsmd.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<RegisteredUser,String> {

    //TODO it must return a user
    //RegisteredUser findFirstByUsernameAndPassword(String username, String password);

    RegisteredUser findFirstByUsername(String username);


}
