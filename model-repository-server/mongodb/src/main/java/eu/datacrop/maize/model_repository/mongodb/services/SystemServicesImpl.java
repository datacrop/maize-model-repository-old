package eu.datacrop.maize.model_repository.mongodb.services;

import eu.datacrop.maize.model_repository.commons.dtos.requests.SystemRequestDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.error.exceptions.NonUuidArgumentException;
import eu.datacrop.maize.model_repository.commons.error.messages.SystemErrorMessages;
import eu.datacrop.maize.model_repository.commons.util.ValidatorUUID;
import eu.datacrop.maize.model_repository.commons.wrappers.PaginationInfo;
import eu.datacrop.maize.model_repository.commons.wrappers.collection.SystemResponsesWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.SystemResponseWrapper;
import eu.datacrop.maize.model_repository.mongodb.converters.SystemConverters;
import eu.datacrop.maize.model_repository.mongodb.model.System;
import eu.datacrop.maize.model_repository.mongodb.repositories.SystemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**********************************************************************************************************************
 * This class implements the services offered by Mongo databases pertaining to the persistence of IoT Systems.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
@Slf4j
@Service
public class SystemServicesImpl implements SystemServices {

    @Autowired
    SystemRepository repository;

    @Autowired
    SystemConverters converters;


    /******************************************************************************************************************
     * Method to retrieve an existing System using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing System in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved System or failure messages.
     *
     * @throws IllegalArgumentException if databaseID parameter is null or empty string.
     * @throws NonUuidArgumentException if databaseID parameter does not adhere to UUID format.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper retrieveSystemByDatabaseID(String databaseID) throws IllegalArgumentException, NonUuidArgumentException {

        // Validating input parameter.
        if (databaseID == null || databaseID.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method retrieveSystemByDatabaseID().");
        } else if (ValidatorUUID.isValidUUIDFormat(databaseID).equals(Boolean.FALSE)) {
            throw new NonUuidArgumentException("Non-UUID parameter detected for method retrieveSystemByDatabaseID().");
        }

        // Attempting to retrieve the entity corresponding to the databaseID.
        System entity;
        String message;
        try {
            entity = repository.findById(databaseID).orElse(null);
        } catch (Exception e) {
            message = SystemErrorMessages.ERROR_ON_RETRIEVAL_ID.toString().concat(databaseID);
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message);
        }

        // If nothing has been found, but not due to error, report accordingly.
        if (entity == null) {
            message = SystemErrorMessages.NOT_FOUND_ID.toString().concat(databaseID);
            log.info(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.NOT_FOUND, message);
        }

        // Since the retrieval has been successful, enclosing the System into a message.
        SystemResponseWrapper wrapper;
        try {
            wrapper = converters.convertEntityToResponseWrapper(entity);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message);
        } catch (Exception e) {
            message = SystemErrorMessages.ERROR_ON_RETRIEVAL_ID.toString().concat(databaseID);
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message);
        }

        // Logging success and returning the result.
        log.info("Successfully retrieved System from database with ID: '{}'.", databaseID);
        return wrapper;
    }

    /******************************************************************************************************************
     * Method to retrieve an existing System using its name as unique identifier.
     *
     * @param name A string that uniquely identifies an existing System in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved System or failure messages.
     *
     * @throws IllegalArgumentException if name parameter is null or an empty string.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper retrieveSystemByName(String name) throws IllegalArgumentException {

        // Validating input parameter.
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method retrieveSystemByDatabaseID().");
        }

        // Attempting to retrieve the entity corresponding to the name.
        System entity;
        String message;
        try {
            entity = repository.findFirstByName(name);
        } catch (Exception e) {
            message = SystemErrorMessages.ERROR_ON_RETRIEVAL_NAME.toString().concat(name);
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message);
        }

        // If nothing has been found, but not due to error, report accordingly.
        if (entity == null) {
            message = SystemErrorMessages.NOT_FOUND_NAME.toString().concat(name);
            log.info(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.NOT_FOUND, message);
        }

        // Since the retrieval has been successful, enclosing the System into a message.
        SystemResponseWrapper wrapper;
        try {
            wrapper = converters.convertEntityToResponseWrapper(entity);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message);
        } catch (Exception e) {
            message = SystemErrorMessages.ERROR_ON_RETRIEVAL_NAME.toString().concat(name);
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message);
        }

        // Logging success and returning the result.
        log.info("Successfully retrieved System from database with Name: '{}'.", name);
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

        // Attempting to retrieve the all entities indicated by the pagination instructions.
        Pageable paging = PageRequest.of(page, size);
        Page<System> systemsPage;
        List<System> entities;
        PaginationInfo paginationInfo;

        String message;
        try {
            systemsPage = repository.findAll(paging);
            entities = systemsPage.getContent();
            paginationInfo = new PaginationInfo(systemsPage.getTotalElements(), systemsPage.getTotalPages(), systemsPage.getNumber());
        } catch (Exception e) {
            message = SystemErrorMessages.ERROR_ON_RETRIEVAL_MANY.toString();
            log.error(message);
            return converters.synthesizeResponsesWrapperForError(ResponseCode.ERROR, message);
        }

        // If nothing has been found, but not due to error, report accordingly.
        if (entities == null || entities.size() == 0) {
            message = SystemErrorMessages.NOT_FOUND_ALL.toString();
            log.info(message);
            return converters.synthesizeResponsesWrapperForError(ResponseCode.NOT_FOUND, message);
        }

        // Since the retrieval has been successful, enclosing the collection of Systems into a message.
        SystemResponsesWrapper wrapper;
        try {
            wrapper = converters.convertEntitiesToResponseWrapper(entities, paginationInfo);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponsesWrapperForError(ResponseCode.ERROR, message);
        } catch (Exception e) {
            message = SystemErrorMessages.ERROR_ON_RETRIEVAL_MANY.toString();
            log.error(message);
            return converters.synthesizeResponsesWrapperForError(ResponseCode.ERROR, message);
        }

        // Logging success and returning the result.
        log.info("Successfully retrieved all System entities from database (Page '{}' of '{}').", systemsPage.getNumber(), systemsPage.getTotalPages());
        return wrapper;
    }

    /******************************************************************************************************************
     * Method to persist a new System.
     *
     * @param requestDto A data transfer object with values for the attributes of the System, not null.
     * @return A wrapped data transfer object with either information on the created System or failure messages.
     *
     * @throws IllegalArgumentException if requestDto parameter is null.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper createSystem(SystemRequestDto requestDto) throws IllegalArgumentException {
        return null;
    }

    /******************************************************************************************************************
     * Method to update an existing System using its databaseID as unique identifier.
     *
     * @param requestDto A data transfer object with values for the attributes of the System, not null.
     * @param databaseID A UUID that uniquely identifies an existing System in the database, not null.
     * @return A wrapped data transfer object with either information on the updated System or failure messages.
     *
     * @throws IllegalArgumentException if requestDto parameter is null.
     * @throws IllegalArgumentException if databaseID parameter is null, empty or does not adhere to UUID format.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper updateSystem(SystemRequestDto requestDto, String databaseID) throws IllegalArgumentException {
        return null;
    }

    /******************************************************************************************************************
     * Method to delete an existing System using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing System in the database, not null.
     * @return A wrapped data transfer object with either information on the deleted System or failure messages.
     *
     * @throws IllegalArgumentException if databaseID parameter is null, empty or does not adhere to UUID format.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper deleteSystem(String databaseID) throws IllegalArgumentException {
        return null;
    }

    /******************************************************************************************************************
     * Method to delete all existing Systems.
     *
     * @return A wrapped data transfer object with either a success message or failure messages.
     *****************************************************************************************************************/
    @Override
    public SystemResponsesWrapper deleteAllSystems() {
        return null;
    }
}
