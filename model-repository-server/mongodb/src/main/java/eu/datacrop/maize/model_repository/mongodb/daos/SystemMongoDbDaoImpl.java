package eu.datacrop.maize.model_repository.mongodb.daos;

import eu.datacrop.maize.model_repository.commons.dtos.requests.SystemRequestDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.error.exceptions.NonUuidArgumentException;
import eu.datacrop.maize.model_repository.commons.error.messages.SystemErrorMessages;
import eu.datacrop.maize.model_repository.commons.wrappers.collection.SystemResponsesWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.SystemResponseWrapper;
import eu.datacrop.maize.model_repository.mongodb.converters.SystemConverters;
import eu.datacrop.maize.model_repository.mongodb.services.SystemServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**********************************************************************************************************************
 * This class implements the entry points (Data Access Objects) to the services offered by Mongo databases
 * pertaining to the persistence of IoT Systems. It also handles exceptions related to invalid method arguments.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
@Slf4j
@Service
@Profile("devmongo")
public class SystemMongoDbDaoImpl implements SystemMongoDbDao {

    @Autowired
    SystemServices services;

    @Autowired
    SystemConverters converters;

    /******************************************************************************************************************
     * Method to retrieve an existing System using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing System in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved System or failure messages.
     *
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper retrieveSystemByDatabaseID(String databaseID) {

        log.info("MongoDB received request for retrieval of System with ID: '{}'.", databaseID);
        SystemResponseWrapper wrapper;
        String message;
        try {
            wrapper = services.retrieveSystemByDatabaseID(databaseID);
        } catch (NonUuidArgumentException e) {
            message = SystemErrorMessages.INVALID_PARAMETER_FORMAT.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.INVALID_PARAMETER_FORMAT);
        } catch (IllegalArgumentException e) {
            message = SystemErrorMessages.INVALID_PARAMETERS.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.INVALID_PARAMETERS);
        }
        return wrapper;
    }

    /******************************************************************************************************************
     * Method to retrieve an existing System using its name as unique identifier.
     *
     * @param name A string that uniquely identifies an existing System in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved System or failure messages.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper retrieveSystemByName(String name) {

        log.info("MongoDB received request for retrieval of System with Name: '{}'.", name);
        SystemResponseWrapper wrapper;
        String message;
        try {
            wrapper = services.retrieveSystemByName(name);
        } catch (IllegalArgumentException e) {
            message = SystemErrorMessages.INVALID_PARAMETERS.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.INVALID_PARAMETERS);
        }
        return wrapper;
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
        log.info("MongoDB received request for retrieval of all Systems.");
        return services.retrieveAllSystems(page, size);
    }

    /******************************************************************************************************************
     * Method to persist a new System.
     *
     * @param requestDto A data transfer object with values for the attributes of the System, not null.
     * @return A wrapped data transfer object with either information on the created System or failure messages.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper createSystem(SystemRequestDto requestDto) {

        log.info("MongoDB received request for creation of new System.");
        SystemResponseWrapper wrapper;
        String message;
        try {
            wrapper = services.createSystem(requestDto);
        } catch (IllegalArgumentException e) {
            message = SystemErrorMessages.INVALID_PARAMETERS.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.INVALID_PARAMETERS);
        }
        return wrapper;
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

        log.info("MongoDB received request for update of System with ID: '{}'.", databaseID);
        SystemResponseWrapper wrapper;
        String message;
        try {
            wrapper = services.updateSystem(requestDto, databaseID);
        } catch (NonUuidArgumentException e) {
            message = SystemErrorMessages.INVALID_PARAMETER_FORMAT.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.INVALID_PARAMETER_FORMAT);
        } catch (IllegalArgumentException e) {
            message = SystemErrorMessages.INVALID_PARAMETERS.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.INVALID_PARAMETERS);
        }
        return wrapper;
    }

    /******************************************************************************************************************
     * Method to delete an existing System using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing System in the database, not null.
     * @return A wrapped data transfer object with either information on the deleted System or failure messages.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper deleteSystem(String databaseID) {

        log.info("MongoDB received request for deletion of System with ID: '{}'.", databaseID);
        SystemResponseWrapper wrapper;
        String message;
        try {
            wrapper = services.deleteSystem(databaseID);
        } catch (NonUuidArgumentException e) {
            message = SystemErrorMessages.INVALID_PARAMETER_FORMAT.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.INVALID_PARAMETER_FORMAT);
        } catch (IllegalArgumentException e) {
            message = SystemErrorMessages.INVALID_PARAMETERS.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.INVALID_PARAMETERS);
        }
        return wrapper;
    }

    /******************************************************************************************************************
     * Method to delete all existing Systems.
     *
     * @return A wrapped data transfer object with either a success message or failure messages.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper deleteAllSystems() {
        log.info("MongoDB received request for deletion of all Systems.");
        return services.deleteAllSystems();
    }
}
