package eu.datacrop.maize.model_repository.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**********************************************************************************************************************
 * This is the entrance point to the execution of the Spring Boot Project.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
@SpringBootApplication
@ComponentScan(basePackages = {
        "eu.datacrop.maize.model_repository.commons.dtos",
        "eu.datacrop.maize.model_repository.commons.dtos.requests",
        "eu.datacrop.maize.model_repository.commons.dtos.requests.templates",
        "eu.datacrop.maize.model_repository.commons.dtos.responses",
        "eu.datacrop.maize.model_repository.commons.enums",
        "eu.datacrop.maize.model_repository.commons.error",
        "eu.datacrop.maize.model_repository.commons.error.exceptions",
        "eu.datacrop.maize.model_repository.commons.error.messages",
        "eu.datacrop.maize.model_repository.commons.util",
        "eu.datacrop.maize.model_repository.commons.validators",
        "eu.datacrop.maize.model_repository.commons.wrappers",
        "eu.datacrop.maize.model_repository.commons.wrappers.collection",
        "eu.datacrop.maize.model_repository.commons.wrappers.single",
        "eu.datacrop.maize.model_repository.mongodb.converters",
        "eu.datacrop.maize.model_repository.mongodb.converters.auxiliary",
        "eu.datacrop.maize.model_repository.mongodb.daos",
        "eu.datacrop.maize.model_repository.mongodb.listeners",
        "eu.datacrop.maize.model_repository.mongodb.model",
        "eu.datacrop.maize.model_repository.mongodb.model.auxiliary",
        "eu.datacrop.maize.model_repository.mongodb.repositories",
        "eu.datacrop.maize.model_repository.mongodb.services",
        "eu.datacrop.maize.model_repository.persistence.daos",
        "eu.datacrop.maize.model_repository.persistence.mongo_implementation",
        "eu.datacrop.maize.model_repository.persistence.mysql_implementation",
        "eu.datacrop.maize.model_repository.persistence.validators",
        "eu.datacrop.maize.model_repository.services.persistence",
        "eu.datacrop.maize.model_repository.api",
        "eu.datacrop.maize.model_repository.api.config",
        "eu.datacrop.maize.model_repository.api.controllers",
        "eu.datacrop.maize.model_repository.api.error",
        "eu.datacrop.maize.model_repository.api.services",
        "org.springframework.data.annotation"
})
@EntityScan(basePackages = {
        "eu.datacrop.maize.model_repository.mongodb.model",
        "eu.datacrop.maize.model_repository.mongodb.model.auxiliary"
})
public class ModelRepositoryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModelRepositoryServerApplication.class, args);
    }

}
