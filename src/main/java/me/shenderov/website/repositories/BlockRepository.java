package me.shenderov.website.repositories;

import me.shenderov.website.dao.Block;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;

public interface BlockRepository extends MongoRepository<Block, String> {

}
