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
import org.springframework.context.annotation.Profile;
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
@Profile("devmongo")
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
            message = SystemErrorMessages.ERROR_ON_RETRIEVAL_ID.toString().concat("'" + databaseID + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.ERROR_ON_RETRIEVAL_ID);
        }

        // If nothing has been found, but not due to error, report accordingly.
        if (entity == null) {
            message = SystemErrorMessages.SYSTEM_NOT_FOUND_ID.toString().concat("'" + databaseID + "'.");
            log.info(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.NOT_FOUND, message, SystemErrorMessages.SYSTEM_NOT_FOUND_ID);
        }

        // Since the retrieval has been successful, enclosing the System into a message.
        SystemResponseWrapper wrapper;
        try {
            wrapper = converters.convertEntityToResponseWrapper(entity);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            message = SystemErrorMessages.ERROR_ON_RETRIEVAL_ID.toString().concat("'" + databaseID + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.ERROR_ON_RETRIEVAL_ID);
        }

        // Logging success and returning the result.
        log.info("Successfully retrieved System from persistence layer with ID: '{}'.", databaseID);
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
            throw new IllegalArgumentException("Invalid parameter detected for method retrieveSystemByName().");
        }

        // Attempting to retrieve the entity corresponding to the name.
        System entity;
        String message;
        try {
            entity = repository.findFirstByName(name);
        } catch (Exception e) {
            message = SystemErrorMessages.ERROR_ON_RETRIEVAL_NAME.toString().concat("'" + name + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.ERROR_ON_RETRIEVAL_NAME);
        }

        // If nothing has been found, but not due to error, report accordingly.
        if (entity == null) {
            message = SystemErrorMessages.SYSTEM_NOT_FOUND_NAME.toString().concat("'" + name + "'.");
            log.info(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.NOT_FOUND, message, SystemErrorMessages.SYSTEM_NOT_FOUND_NAME);
        }

        // Since the retrieval has been successful, enclosing the System into a message.
        SystemResponseWrapper wrapper;
        try {
            wrapper = converters.convertEntityToResponseWrapper(entity);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            message = SystemErrorMessages.ERROR_ON_RETRIEVAL_NAME.toString().concat("'" + name + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.ERROR_ON_RETRIEVAL_NAME);
        }

        // Logging success and returning the result.
        log.info("Successfully retrieved System from persistence layer with Name: '{}'.", name);
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
            return converters.synthesizeResponsesWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.ERROR_ON_RETRIEVAL_MANY);
        }

        // If nothing has been found, but not due to error, report accordingly.
        if (entities == null || entities.size() == 0) {

            // Systems are available but the request was out of pagination limits.
            if (paginationInfo.getTotalItems() > 0) {
                message = SystemErrorMessages.EXCEEDED_PAGE_LIMIT.toString().concat(" Total Pages: " + paginationInfo.getTotalPages());
                log.info(message);
                return converters.synthesizeResponsesWrapperForError(ResponseCode.NOT_FOUND, message, SystemErrorMessages.EXCEEDED_PAGE_LIMIT);
            }

            // Systems are not available at all.
            message = SystemErrorMessages.NO_SYSTEMS_FOUND.toString();
            log.info(message);
            return converters.synthesizeResponsesWrapperForError(ResponseCode.NOT_FOUND, message, SystemErrorMessages.NO_SYSTEMS_FOUND);
        }

        // Since the retrieval has been successful, enclosing the collection of Systems into a message.
        SystemResponsesWrapper wrapper;
        try {
            wrapper = converters.convertEntitiesToResponseWrapper(entities, paginationInfo);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponsesWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            message = SystemErrorMessages.ERROR_ON_RETRIEVAL_MANY.toString();
            log.error(message);
            return converters.synthesizeResponsesWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.ERROR_ON_RETRIEVAL_MANY);
        }

        // Logging success and returning the result.
        log.info("Successfully retrieved all System entities from persistence layer (Page '{}' of '{}').", systemsPage.getNumber(), systemsPage.getTotalPages());
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

        // Validating input parameter.
        if (requestDto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method createSystem().");
        }
        String message;

        // Checking whether the name is already taken by another entity.
        System conflictingEntity;
        try {
            conflictingEntity = repository.findFirstByName(requestDto.getName());
        } catch (Exception e) {
            message = SystemErrorMessages.ERROR_ON_RETRIEVAL_NAME.toString().concat("'" + requestDto.getName() + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.INTERNAL_SERVER_ERROR);
        }

        if (conflictingEntity != null) {
            message = SystemErrorMessages.DUPLICATE_SYSTEM.toString().concat("'" + conflictingEntity.getId() + "'.");
            log.info(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.CONFLICT, message, SystemErrorMessages.DUPLICATE_SYSTEM);
        }

        // Converting the request data transfer object to a database entity.
        System entityToPersist;
        try {
            entityToPersist = converters.convertRequestDtoToEntity(requestDto, "");
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.INTERNAL_SERVER_ERROR);
        }

        // Attempting to create new System entity.
        System createdEntity;
        try {
            createdEntity = repository.save(entityToPersist);
        } catch (Exception e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.INTERNAL_SERVER_ERROR);
        }

        if (createdEntity == null) {
            message = SystemErrorMessages.ERROR_ON_CREATION.toString().concat("'" + requestDto.getName() + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.ERROR_ON_CREATION);
        }

        // Since the creation has been successful, enclosing the System into a message.
        SystemResponseWrapper wrapper;
        try {
            wrapper = converters.convertEntityToResponseWrapper(createdEntity);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            message = SystemErrorMessages.ERROR_ON_CREATION.toString().concat("'" + requestDto.getName() + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.ERROR_ON_CREATION);
        }

        // Logging success and returning the result.
        log.info("Successfully created new System in persistence layer with ID '{}' and Name '{}'.", createdEntity.getId(), createdEntity.getName());
        return wrapper;
    }

    /******************************************************************************************************************
     * Method to update an existing System using its databaseID as unique identifier.
     *
     * @param requestDto A data transfer object with values for the attributes of the System, not null.
     * @param databaseID A UUID that uniquely identifies an existing System in the database, not null.
     * @return A wrapped data transfer object with either information on the updated System or failure messages.
     *
     * @throws IllegalArgumentException if requestDto parameter is null.
     * @throws IllegalArgumentException if databaseID parameter is null or empty string.
     * @throws NonUuidArgumentException if databaseID parameter does not adhere to UUID format.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper updateSystem(SystemRequestDto requestDto, String databaseID) throws IllegalArgumentException {

        // Validating input parameters.
        if (requestDto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method updateSystem().");
        } else if (databaseID == null || databaseID.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method updateSystem().");
        } else if (ValidatorUUID.isValidUUIDFormat(databaseID).equals(Boolean.FALSE)) {
            throw new NonUuidArgumentException("Non-UUID parameter detected for method updateSystem().");
        }

        // Attempting to retrieve the entity corresponding to the databaseID.
        System retrievedEntity;
        String message;
        try {
            retrievedEntity = repository.findById(databaseID).orElse(null);
        } catch (Exception e) {
            message = SystemErrorMessages.ERROR_ON_RETRIEVAL_ID.toString().concat("'" + databaseID + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.ERROR_ON_RETRIEVAL_ID);
        }

        // If nothing has been found, but not due to error, report accordingly.
        if (retrievedEntity == null) {
            message = SystemErrorMessages.SYSTEM_NOT_FOUND_ID.toString().concat("'" + databaseID + "'.");
            log.info(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.NOT_FOUND, message, SystemErrorMessages.SYSTEM_NOT_FOUND_ID);
        }

        // If a change to the name is being attempted, search for conflicts.
        if (!requestDto.getName().equals(retrievedEntity.getName())) {
            System conflictingEntity;
            try {
                conflictingEntity = repository.findFirstByName(requestDto.getName());
            } catch (Exception e) {
                message = SystemErrorMessages.ERROR_ON_RETRIEVAL_NAME.toString().concat("'" + requestDto.getName() + "'.");
                log.error(message);
                return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.ERROR_ON_RETRIEVAL_NAME);
            }

            if (conflictingEntity != null) {
                message = SystemErrorMessages.DUPLICATE_SYSTEM.toString().concat("'" + conflictingEntity.getId() + "'.");
                log.error(message);
                return converters.synthesizeResponseWrapperForError(ResponseCode.CONFLICT, message, SystemErrorMessages.DUPLICATE_SYSTEM);
            }
        }

        // Converting the request data transfer object to a database entity.
        System entityToPersist;
        try {
            entityToPersist = converters.convertRequestDtoToEntity(requestDto, databaseID);
            entityToPersist.setCreationDate(retrievedEntity.getCreationDate());
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.INTERNAL_SERVER_ERROR);
        }

        // Attempting to update System entity.
        System updatedEntity;
        try {
            updatedEntity = repository.save(entityToPersist);
        } catch (Exception e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.INTERNAL_SERVER_ERROR);
        }

        if (updatedEntity == null) {
            message = SystemErrorMessages.ERROR_ON_UPDATE.toString().concat("'" + databaseID + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.ERROR_ON_UPDATE);
        }

        // Since the creation has been successful, enclosing the System into a message.
        SystemResponseWrapper wrapper;
        try {
            wrapper = converters.convertEntityToResponseWrapper(updatedEntity);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            message = SystemErrorMessages.ERROR_ON_UPDATE.toString().concat("'" + databaseID + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.INTERNAL_SERVER_ERROR);
        }

        // Logging success and returning the result.
        log.info("Successfully updated System in persistence layer with ID '{}' and Name '{}'.", updatedEntity.getId(), updatedEntity.getName());
        return wrapper;
    }

    /******************************************************************************************************************
     * Method to delete an existing System using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing System in the database, not null.
     * @return A wrapped data transfer object with either information on the deleted System or failure messages.
     *
     * @throws IllegalArgumentException if databaseID parameter is null or empty string.
     * @throws NonUuidArgumentException if databaseID parameter does not adhere to UUID format.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper deleteSystem(String databaseID) throws IllegalArgumentException {

        // Validating input parameter.
        if (databaseID == null || databaseID.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method deleteSystem().");
        } else if (ValidatorUUID.isValidUUIDFormat(databaseID).equals(Boolean.FALSE)) {
            throw new NonUuidArgumentException("Non-UUID parameter detected for method deleteSystem().");
        }

        // Attempting to retrieve the entity corresponding to the databaseID.
        System entity;
        String message;
        try {
            entity = repository.findById(databaseID).orElse(null);
        } catch (Exception e) {
            message = SystemErrorMessages.ERROR_ON_DELETION_ID.toString().concat("'" + databaseID + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.ERROR_ON_DELETION_ID);
        }

        // If nothing has been found, but not due to error, report accordingly.
        if (entity == null) {
            message = SystemErrorMessages.SYSTEM_NOT_FOUND_ID.toString().concat("'" + databaseID + "'.");
            log.info(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.NOT_FOUND, message, SystemErrorMessages.SYSTEM_NOT_FOUND_ID);
        }

        // Attempting to delete the entity corresponding to the databaseID.
        try {
            repository.deleteById(databaseID);
        } catch (Exception e) {
            message = SystemErrorMessages.ERROR_ON_DELETION_ID.toString().concat("'" + databaseID + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.ERROR_ON_DELETION_ID);
        }

        // Since the retrieval has been successful, enclosing the System into a message.
        SystemResponseWrapper wrapper;
        try {
            wrapper = converters.convertEntityToResponseWrapper(entity);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            message = SystemErrorMessages.ERROR_ON_DELETION_ID.toString().concat("'" + databaseID + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.ERROR_ON_DELETION_ID);
        }

        // Logging success and returning the result.
        log.info("Successfully deleted System from persistence layer with ID: '{}'.", databaseID);
        return wrapper;
    }

    /******************************************************************************************************************
     * Method to delete all existing Systems.
     *
     * @return A wrapped data transfer object with either a success message or failure messages.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper deleteAllSystems() {

        // Checking whether there is anything to delete.
        String message;
        try {
            long count = repository.count();
            if (count == 0L) {
                message = SystemErrorMessages.NO_SYSTEMS_FOUND.toString();
                log.info(message);
                return converters.synthesizeResponseWrapperForError(ResponseCode.NOT_FOUND, message, SystemErrorMessages.NO_SYSTEMS_FOUND);
            }
        } catch (Exception e) {
            message = SystemErrorMessages.ERROR_ON_DELETION_MANY.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.ERROR_ON_DELETION_MANY);
        }

        // Attempting to delete all System entities from the database.
        try {
            repository.deleteAll();
        } catch (Exception e) {
            message = SystemErrorMessages.ERROR_ON_DELETION_MANY.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, SystemErrorMessages.ERROR_ON_DELETION_MANY);
        }

        // Logging success and returning the result.
        SystemResponseWrapper wrapper = new SystemResponseWrapper();
        wrapper.setCode(ResponseCode.SUCCESS);
        wrapper.setMessage("Database transaction successfully concluded.");

        log.info("Successfully deleted all Systems from the persistence layer.");
        return wrapper;
    }
}
