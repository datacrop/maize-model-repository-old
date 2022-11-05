package eu.datacrop.maize.model_repository.api.config;


import com.mongodb.MongoClientOptions;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Profile("devmongo")
@Configuration
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = {"eu.datacrop.maize.model_repository.mongodb.repositories"})
public class CustomMongoAutoConfiguration extends MongoAutoConfiguration {

    public CustomMongoAutoConfiguration(
            MongoProperties properties,
            ObjectProvider<MongoClientOptions> options,
            Environment environment) {
        super();
    }
}
