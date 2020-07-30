package me.shenderov.website.repositories;

import me.shenderov.website.security.dao.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {

    User findUserByUsername(String username);

    void deleteUserByUsername(String username);

    boolean existsByUsername(String username);

}
