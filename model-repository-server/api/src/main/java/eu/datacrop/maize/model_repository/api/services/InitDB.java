package eu.datacrop.maize.model_repository.api.services;

import eu.datacrop.maize.model_repository.mongodb.model.System;
import eu.datacrop.maize.model_repository.mongodb.repositories.SystemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InitDB implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    SystemRepository systemRepository;

    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        System system = new System();
        system.setName("MyTestSystem");
        system.setOrganization("MyOrg");
        // systemRepository.save(system);

    }

}
