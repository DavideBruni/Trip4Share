package it.unipi.dii.lsmd.repository;

import it.unipi.dii.lsmd.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    /*@Query("{ 'username' : ?0, 'password' : ?1 }")
    User login(String username, String password);*/

}
