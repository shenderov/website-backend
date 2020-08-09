package me.shenderov.website.repositories;


import me.shenderov.website.dao.settings.AbstractApplicationSettings;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SettingsRepository extends MongoRepository<AbstractApplicationSettings, String> {
}
