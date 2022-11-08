package eu.datacrop.maize.model_repository.services.persistence;

import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.SystemRequestDto;
import eu.datacrop.maize.model_repository.commons.wrappers.collection.SystemResponsesWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.entities.SystemResponseWrapper;
import eu.datacrop.maize.model_repository.persistence.daos.SystemPersistenceLayerDaos;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**********************************************************************************************************************
 * This class redirects enquires to the persistence layer pertaining to IoT Systems.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
@Slf4j
@Service
public class SystemPersistenceServicesDaoImpl implements SystemPersistenceServicesDao {

    @Autowired
    SystemPersistenceLayerDaos persistenceLayer;

    /******************************************************************************************************************
     * Method to retrieve an existing System using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing System in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved System or failure messages.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper retrieveSystemByDatabaseID(String databaseID) {
        log.info("Services layer received request for retrieval of System with ID: '{}'.", databaseID);
        return persistenceLayer.retrieveSystemByDatabaseID(databaseID);
    }

    /******************************************************************************************************************
     * Method to retrieve an existing System using its name as unique identifier.
     *
     * @param name A string that uniquely identifies an existing System in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved System or failure messages.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper retrieveSystemByName(String name) {
        log.info("Services layer received request for retrieval of System with Name: '{}'.", name);
        return persistenceLayer.retrieveSystemByName(name);
    }

    /******************************************************************************************************************
     * Method to retrieve all Systems paginated.
     *
     * @param page The page to retrieve.
     * @param size The intended size of pages.
     * @return A wrapped data transfer object with either information on the retrieved Systems or failure messages.
     *****************************************************************************************************************/
    @Override
    public SystemResponsesWrapper retrieveAllSystems(int page, int size) {
        log.info("Services layer received request for retrieval of all Systems.");
        return persistenceLayer.retrieveAllSystems(page, size);
    }

    /******************************************************************************************************************
     * Method to persist a new System.
     *
     * @param requestDto A data transfer object with values for the attributes of the System, not null.
     * @return A wrapped data transfer object with either information on the created System or failure messages.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper createSystem(SystemRequestDto requestDto) {
        log.info("Services layer received request for creation of new System.");
        return persistenceLayer.createSystem(requestDto);
    }

    /******************************************************************************************************************
     * Method to update an existing System using its databaseID as unique identifier.
     *
     * @param requestDto A data transfer object with values for the attributes of the System, not null.
     * @param databaseID A UUID that uniquely identifies an existing System in the database, not null.
     * @return A wrapped data transfer object with either information on the updated System or failure messages.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper updateSystem(SystemRequestDto requestDto, String databaseID) {
        log.info("Services layer received request for update of System with ID: '{}'.", databaseID);
        return persistenceLayer.updateSystem(requestDto, databaseID);
    }

    /******************************************************************************************************************
     * Method to delete an existing System using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing System in the database, not null.
     * @return A wrapped data transfer object with either information on the deleted System or failure messages.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper deleteSystem(String databaseID) {
        log.info("Services layer received request for deletion of System with ID: '{}'.", databaseID);
        return persistenceLayer.deleteSystem(databaseID);
    }

    /******************************************************************************************************************
     * Method to delete all existing Systems.
     *
     * @return A wrapped data transfer object with either a success message or failure messages.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper deleteAllSystems() {
        log.info("Services layer received request for deletion of all Systems.");
        return persistenceLayer.deleteAllSystems();
    }
}
