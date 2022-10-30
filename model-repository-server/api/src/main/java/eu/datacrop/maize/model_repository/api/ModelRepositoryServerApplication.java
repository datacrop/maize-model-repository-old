package eu.datacrop.maize.model_repository.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "eu.datacrop.maize.model_repository.commons.dtos",
        "eu.datacrop.maize.model_repository.commons.enums",
        "eu.datacrop.maize.model_repository.commons.util",
        "eu.datacrop.maize.model_repository.commons.wrappers",
        "eu.datacrop.maize.model_repository.mongodb.converters",
        "eu.datacrop.maize.model_repository.mongodb.daos",
        "eu.datacrop.maize.model_repository.mongodb.listeners",
        "eu.datacrop.maize.model_repository.mongodb.model",
        "eu.datacrop.maize.model_repository.mongodb.repositories",
        "eu.datacrop.maize.model_repository.mongodb.services",
})
public class ModelRepositoryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModelRepositoryServerApplication.class, args);
    }

}
