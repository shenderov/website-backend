package me.shenderov.website.repositories;

import me.shenderov.website.dao.MessageWrapper;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<MessageWrapper, Long> {
}
