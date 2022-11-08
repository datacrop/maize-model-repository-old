package eu.datacrop.maize.model_repository.persistence.mongo_implementation;

import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.SystemRequestDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.error.messages.SystemErrorMessages;
import eu.datacrop.maize.model_repository.commons.wrappers.collection.SystemResponsesWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.entities.SystemResponseWrapper;
import eu.datacrop.maize.model_repository.mongodb.services.SystemServices;
import eu.datacrop.maize.model_repository.persistence.daos.SystemPersistenceLayerDaos;
import eu.datacrop.maize.model_repository.persistence.validators.SystemValidator;
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

    @Autowired
    SystemValidator validator;


    /******************************************************************************************************************
     * Method to retrieve an existing System using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing System in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved System or failure messages.
     *
     * @throws IllegalArgumentException - if requestDto is null.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper retrieveSystemByDatabaseID(String databaseID) throws IllegalArgumentException {

        // Checking input parameters.
        if (databaseID.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method SystemMongoDaos.updateSystem().");
        }

        log.info("Persistence layer (MongoDB) received request for retrieval of System with ID: '{}'.", databaseID);
        return services.retrieveSystemByDatabaseID(databaseID);
    }

    /******************************************************************************************************************
     * Method to retrieve an existing System using its name as unique identifier.
     *
     * @param name A string that uniquely identifies an existing System in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved System or failure messages.
     *
     * @throws IllegalArgumentException - if name is null.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper retrieveSystemByName(String name) throws IllegalArgumentException {

        // Checking input parameters.
        if (name.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method SystemMongoDaos.updateSystem().");
        }

        log.info("Persistence layer (MongoDB) received request for retrieval of System with Name: '{}'.", name);
        return services.retrieveSystemByName(name);
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
        log.info("Persistence layer (MongoDB) received request for retrieval of all Systems.");
        return services.retrieveAllSystems(page, size);
    }

    /******************************************************************************************************************
     * Method to persist a new System.
     *
     * @param requestDto A data transfer object with values for the attributes of the System, not null.
     * @return A wrapped data transfer object with either information on the created System or failure messages.
     *
     * @throws IllegalArgumentException - if requestDto is null.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper createSystem(SystemRequestDto requestDto) throws IllegalArgumentException {

        // Checking input parameters.
        if (requestDto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method SystemMongoDaos.createSystem().");
        }

        log.info("Persistence layer (MongoDB) received request for creation of new System.");

        // Making the validator specific for Systems.
        requestDto.setValidator(this.validator);

        // Performing validations of data transfer object.
        SystemResponseWrapper wrapper = (SystemResponseWrapper) requestDto.performValidation();
        if (!wrapper.getCode().equals(ResponseCode.SUCCESS)) {
            // Aborting if issues have been discovered.
            log.debug("Issues discovered during attribute validation.");
            return synthesizeResponseWrapperForError(wrapper.getCode(), wrapper.getMessage(), wrapper.getErrorCode());
        }

        // Continuing if issues have not been discovered.
        return services.createSystem(requestDto);
    }

    /******************************************************************************************************************
     * Method to update an existing System using its databaseID as unique identifier.
     *
     * @param requestDto A data transfer object with values for the attributes of the System, not null.
     * @param databaseID A UUID that uniquely identifies an existing System in the database, not null.
     * @return A wrapped data transfer object with either information on the updated System or failure messages.
     *
     * @throws IllegalArgumentException - if requestDto is null.
     * @throws IllegalArgumentException - if databaseID is null or empty string.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper updateSystem(SystemRequestDto requestDto, String databaseID) throws IllegalArgumentException {

        // Checking input parameters.
        if (requestDto == null || databaseID.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method SystemMongoDaos.updateSystem().");
        }

        log.info("Persistence layer (MongoDB) received request for update of System with ID: '{}'.", databaseID);

        // Making the validator specific for Systems.
        requestDto.setValidator(this.validator);

        // Performing validations of data transfer object.
        SystemResponseWrapper wrapper = requestDto.performValidation();
        if (!wrapper.getCode().equals(ResponseCode.SUCCESS)) {
            // Aborting if issues have been discovered.
            log.debug("Issues discovered during attribute validation.");
            return synthesizeResponseWrapperForError(wrapper.getCode(), wrapper.getMessage(), wrapper.getErrorCode());
        }

        // Continuing if issues have not been discovered.
        return services.updateSystem(requestDto, databaseID);
    }

    /******************************************************************************************************************
     * Method to delete an existing System using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing System in the database, not null.
     * @return A wrapped data transfer object with either information on the deleted System or failure messages.
     *
     * @throws IllegalArgumentException - if databaseID is null or empty string.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper deleteSystem(String databaseID) throws IllegalArgumentException {

        // Checking input parameters.
        if (databaseID.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method SystemMongoDaos.updateSystem().");
        }

        log.info("Persistence layer (MongoDB) received request for deletion of System with ID: '{}'.", databaseID);
        return services.deleteSystem(databaseID);
    }

    /******************************************************************************************************************
     * Method to delete all existing Systems.
     *
     * @return A wrapped data transfer object with either a success message or failure messages.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper deleteAllSystems() {
        log.info("Persistence layer (MongoDB) received request for deletion of all Systems.");
        return services.deleteAllSystems();
    }

    /******************************************************************************************************************
     * Method that creates an error report.
     *
     * @param code A response code to be translated to an appropriate Http code.
     * @param message The message describing the actual error.
     * @param errorCode A shorthand key for the error.
     * @return A wrapped data transfer object with either a success message or failure messages.
     *****************************************************************************************************************/
    private SystemResponseWrapper synthesizeResponseWrapperForError(ResponseCode code, String message, SystemErrorMessages errorCode) throws IllegalArgumentException {

        if (code == null || code.equals(ResponseCode.SUCCESS) || code.equals(ResponseCode.UNDEFINED) || message.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method synthesizeResponseWrapperForError().");
        }

        SystemResponseWrapper wrapper = new SystemResponseWrapper();
        wrapper.setCode(code);
        wrapper.setMessage(message);
        wrapper.setResponse(null);
        wrapper.setErrorCode(errorCode);

        log.debug("Successfully produced ResponseWrapper for unsuccessful database transaction.");

        return wrapper;
    }
}
