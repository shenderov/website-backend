package me.shenderov.website.repositories;

import me.shenderov.website.security.dao.Authority;
import me.shenderov.website.security.dao.AuthorityName;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthorityRepository extends MongoRepository<Authority, Long> {

    Authority findAuthorityByName(AuthorityName authorityName);

}
