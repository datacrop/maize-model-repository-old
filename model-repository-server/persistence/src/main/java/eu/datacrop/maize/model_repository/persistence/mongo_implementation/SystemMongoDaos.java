package eu.datacrop.maize.model_repository.persistence.mongo_implementation;

import eu.datacrop.maize.model_repository.commons.wrappers.single.SystemResponseWrapper;
import eu.datacrop.maize.model_repository.mongodb.services.SystemServices;
import eu.datacrop.maize.model_repository.persistence.daos.SystemPersistenceLayerDaos;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**********************************************************************************************************************
 * This class redirects enquires to the persistence layer pertaining to IoT Systems. Implementation for MongoDB.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
@Slf4j
@Service
@Profile("devmongo")
public class SystemMongoDaos implements SystemPersistenceLayerDaos {

    @Autowired
    SystemServices services;

    /******************************************************************************************************************
     * Method to retrieve an existing System using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing System in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved System or failure messages.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper retrieveSystemByDatabaseID(String databaseID) {
        log.info("Persistence layer (MongoDB) received request to retrieve System with ID: {}", databaseID);
        return services.retrieveSystemByDatabaseID(databaseID);
    }
}
