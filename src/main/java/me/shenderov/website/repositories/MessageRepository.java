package me.shenderov.website.repositories;

import me.shenderov.website.dao.MessageWrapper;
import me.shenderov.website.entities.MessageDeliveryStatus;
import me.shenderov.website.entities.MessageStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends MongoRepository<MessageWrapper, UUID> {

    List<MessageWrapper> findByDeliveryStatusIsIn(List<MessageDeliveryStatus> statuses);

    Integer countAllByStatus(MessageStatus status);

    @Transactional
    void deleteAll();

}
