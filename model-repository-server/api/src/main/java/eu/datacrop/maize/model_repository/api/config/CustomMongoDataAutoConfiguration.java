package eu.datacrop.maize.model_repository.api.config;

import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("devmongo")
@Configuration
@EnableConfigurationProperties(MongoProperties.class)
public class CustomMongoDataAutoConfiguration extends MongoDataAutoConfiguration {

    public CustomMongoDataAutoConfiguration(
            ApplicationContext applicationContext,
            MongoProperties properties) {
        super();
    }

}
