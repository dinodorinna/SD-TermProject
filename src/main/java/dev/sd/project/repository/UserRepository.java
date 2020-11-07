package dev.sd.project.repository;

import dev.sd.project.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {  //for manage data in database
    User findByUserId(String userId);
    User findByUsername(String username);
    User findByEmail(String email);

}
