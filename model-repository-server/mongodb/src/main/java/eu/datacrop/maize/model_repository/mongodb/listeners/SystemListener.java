package eu.datacrop.maize.model_repository.mongodb.listeners;

import eu.datacrop.maize.model_repository.mongodb.model.System;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SystemListener extends AbstractMongoEventListener<System> {

    @Override
    public void onAfterSave(AfterSaveEvent event) {

        System system = (System) event.getSource();
        log.info("System with DatabaseID: '{}' has been persisted to MongoDB.",
                system.getId());
    }

    @Override
    public void onAfterDelete(AfterDeleteEvent event) {

        if (event.getDocument() == null) return;

        try {
            log.info("System with DatabaseID: '{}' has been deleted from MongoDB.",
                    (event.getDocument()).get("_id").toString());
        } catch (NullPointerException e) {
            log.error("Event reported as null during deletion.");
        }
    }

}
