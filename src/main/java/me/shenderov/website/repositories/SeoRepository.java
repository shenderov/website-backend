package me.shenderov.website.repositories;

import me.shenderov.website.dao.SeoInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SeoRepository extends MongoRepository<SeoInfo, Integer> {
}
