package eu.datacrop.maize.model_repository.api.services;

import eu.datacrop.maize.model_repository.mongodb.model.System;
import eu.datacrop.maize.model_repository.mongodb.repositories.SystemRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InitDB implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    SystemRepository systemRepository;

    @Autowired
    SystemApiServices services;

    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        systemRepository.deleteAll();

        for (int i = 0; i < 20; i++) {
            System system = new System();
            system.setName(RandomStringUtils.randomAlphabetic(10));
            system.setOrganization(RandomStringUtils.randomAlphabetic(10));
            systemRepository.save(system);
        }

        // services.retrieveAllSystems(0, 5);

        //  services.retrieveAllSystems(0, 25);

        //  services.retrieveAllSystems(2, 5);

        //  services.retrieveAllSystems(9, 5);

    }

}
