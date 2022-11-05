package eu.datacrop.maize.model_repository.mongodb.listeners;

import eu.datacrop.maize.model_repository.mongodb.model.System;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;

/**********************************************************************************************************************
 * This class automatically reports database transactions pertaining to IoT Systems (for MongoDB).
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
@Slf4j
@Component
@Profile("devmongo")
public class SystemListener extends AbstractMongoEventListener<System> {

    /******************************************************************************************************************
     * Listener that reports Save Events pertaining to IoT Systems (for MongoDB).
     *
     * @param event An event produced when an entity is persisted on MongoDB.
     *
     * @throws IllegalArgumentException if event is not correlated to a source.
     *****************************************************************************************************************/
    @Override
    public void onAfterSave(AfterSaveEvent event) throws IllegalArgumentException {

        if (event.getSource() == null) {
            throw new IllegalArgumentException("AfterSaveEvent not correlated to a source detected.");
        }

        System system = (System) event.getSource();
        log.info("System with DatabaseID: '{}' has been persisted to MongoDB.",
                system.getId());
    }

    /******************************************************************************************************************
     * Listener that reports Delete Events pertaining to IoT Systems (for MongoDB).
     *
     * @param event An event produced when an entity is deleted from MongoDB.
     *
     * @throws IllegalArgumentException if event is not correlated to a document.
     *****************************************************************************************************************/
    @Override
    public void onAfterDelete(AfterDeleteEvent event) throws IllegalArgumentException {

        if (event.getDocument() == null) {
            throw new IllegalArgumentException("AfterDeleteEvent not correlated to a document detected.");
        }

        try {
            log.info("System with DatabaseID: '{}' has been deleted from MongoDB.",
                    (event.getDocument()).get("_id").toString());
        } catch (NullPointerException e) {
            log.error("Event reported as null during deletion.");
        }
    }

}
