package eu.datacrop.maize.model_repository.mongodb.services;

import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.VendorRequestDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.error.exceptions.NonUuidArgumentException;
import eu.datacrop.maize.model_repository.commons.error.messages.VendorErrorMessages;
import eu.datacrop.maize.model_repository.commons.util.ValidatorUUID;
import eu.datacrop.maize.model_repository.commons.wrappers.PaginationInfo;
import eu.datacrop.maize.model_repository.commons.wrappers.collection.VendorResponsesWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.entities.VendorResponseWrapper;
import eu.datacrop.maize.model_repository.mongodb.converters.entities.VendorConverters;
import eu.datacrop.maize.model_repository.mongodb.model.entities.Vendor;
import eu.datacrop.maize.model_repository.mongodb.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**********************************************************************************************************************
 * This class implements the services offered by Mongo databases pertaining to the persistence of Vendors.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
@Slf4j
@Service
@Profile("devmongo")
public class VendorServicesImpl implements VendorServices {

    @Autowired
    VendorRepository repository;

    @Autowired
    VendorConverters converters;


    /******************************************************************************************************************
     * Method to retrieve an existing Vendor using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing Vendor in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved Vendor or failure messages.
     *
     * @throws IllegalArgumentException if databaseID parameter is null or empty string.
     * @throws NonUuidArgumentException if databaseID parameter does not adhere to UUID format.
     *****************************************************************************************************************/
    @Override
    public VendorResponseWrapper retrieveVendorByDatabaseID(String databaseID) throws IllegalArgumentException, NonUuidArgumentException {

        // Validating input parameter.
        if (databaseID == null || databaseID.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method retrieveVendorByDatabaseID().");
        } else if (ValidatorUUID.isValidUUIDFormat(databaseID).equals(Boolean.FALSE)) {
            throw new NonUuidArgumentException("Non-UUID parameter detected for method retrieveVendorByDatabaseID().");
        }

        // Attempting to retrieve the entity corresponding to the databaseID.
        Vendor entity;
        String message;
        try {
            entity = repository.findById(databaseID).orElse(null);
        } catch (Exception e) {
            message = VendorErrorMessages.ERROR_ON_RETRIEVAL_ID.toString().concat("'" + databaseID + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.ERROR_ON_RETRIEVAL_ID);
        }

        // If nothing has been found, but not due to error, report accordingly.
        if (entity == null) {
            message = VendorErrorMessages.VENDOR_NOT_FOUND_ID.toString().concat("'" + databaseID + "'.");
            log.info(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.NOT_FOUND, message, VendorErrorMessages.VENDOR_NOT_FOUND_ID);
        }

        // Since the retrieval has been successful, enclosing the Vendor into a message.
        VendorResponseWrapper wrapper;
        try {
            wrapper = converters.convertEntityToResponseWrapper(entity);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            message = VendorErrorMessages.ERROR_ON_RETRIEVAL_ID.toString().concat("'" + databaseID + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.ERROR_ON_RETRIEVAL_ID);
        }

        // Logging success and returning the result.
        log.info("Successfully retrieved Vendor from persistence layer with ID: '{}'.", databaseID);
        return wrapper;
    }

    /******************************************************************************************************************
     * Method to retrieve an existing Vendor using its name as unique identifier.
     *
     * @param name A string that uniquely identifies an existing Vendor in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved Vendor or failure messages.
     *
     * @throws IllegalArgumentException if name parameter is null or an empty string.
     *****************************************************************************************************************/
    @Override
    public VendorResponseWrapper retrieveVendorByName(String name) throws IllegalArgumentException {

        // Validating input parameter.
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method retrieveVendorByName().");
        }

        // Attempting to retrieve the entity corresponding to the name.
        Vendor entity;
        String message;
        try {
            entity = repository.findFirstByName(name);
        } catch (Exception e) {
            message = VendorErrorMessages.ERROR_ON_RETRIEVAL_NAME.toString().concat("'" + name + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.ERROR_ON_RETRIEVAL_NAME);
        }

        // If nothing has been found, but not due to error, report accordingly.
        if (entity == null) {
            message = VendorErrorMessages.VENDOR_NOT_FOUND_NAME.toString().concat("'" + name + "'.");
            log.info(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.NOT_FOUND, message, VendorErrorMessages.VENDOR_NOT_FOUND_NAME);
        }

        // Since the retrieval has been successful, enclosing the Vendor into a message.
        VendorResponseWrapper wrapper;
        try {
            wrapper = converters.convertEntityToResponseWrapper(entity);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            message = VendorErrorMessages.ERROR_ON_RETRIEVAL_NAME.toString().concat("'" + name + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.ERROR_ON_RETRIEVAL_NAME);
        }

        // Logging success and returning the result.
        log.info("Successfully retrieved Vendor from persistence layer with Name: '{}'.", name);
        return wrapper;
    }

    /******************************************************************************************************************
     * Method to retrieve all Vendors paginated.
     *
     * @param page The page to retrieve.
     * @param size The intended size of pages.
     * @return A wrapped data transfer object with either information on the retrieved Vendors or failure messages.
     *****************************************************************************************************************/
    @Override
    public VendorResponsesWrapper retrieveAllVendors(int page, int size) {

        // Attempting to retrieve the all entities indicated by the pagination instructions.
        Pageable paging = PageRequest.of(page, size);
        Page<Vendor> vendorsPage;
        List<Vendor> entities;
        PaginationInfo paginationInfo;
        String message;

        try {
            vendorsPage = repository.findAll(paging);
            entities = vendorsPage.getContent();
            paginationInfo = new PaginationInfo(vendorsPage.getTotalElements(), vendorsPage.getTotalPages(), vendorsPage.getNumber());
        } catch (Exception e) {
            message = VendorErrorMessages.ERROR_ON_RETRIEVAL_MANY.toString();
            log.error(message);
            return converters.synthesizeResponsesWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.ERROR_ON_RETRIEVAL_MANY);
        }

        // If nothing has been found, but not due to error, report accordingly.
        if (entities == null || entities.size() == 0) {

            // Vendors are available but the request was out of pagination limits.
            if (paginationInfo.getTotalItems() > 0) {
                message = VendorErrorMessages.EXCEEDED_PAGE_LIMIT.toString().concat(" Total Pages: " + paginationInfo.getTotalPages());
                log.info(message);
                return converters.synthesizeResponsesWrapperForError(ResponseCode.NOT_FOUND, message, VendorErrorMessages.EXCEEDED_PAGE_LIMIT);
            }

            // Vendors are not available at all.
            message = VendorErrorMessages.NO_VENDORS_FOUND.toString();
            log.info(message);
            return converters.synthesizeResponsesWrapperForError(ResponseCode.NOT_FOUND, message, VendorErrorMessages.NO_VENDORS_FOUND);
        }

        // Since the retrieval has been successful, enclosing the collection of Vendors into a message.
        VendorResponsesWrapper wrapper;
        try {
            wrapper = converters.convertEntitiesToResponseWrapper(entities, paginationInfo);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponsesWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            message = VendorErrorMessages.ERROR_ON_RETRIEVAL_MANY.toString();
            log.error(message);
            return converters.synthesizeResponsesWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.ERROR_ON_RETRIEVAL_MANY);
        }

        // Logging success and returning the result.
        log.info("Successfully retrieved all Vendor entities from persistence layer (Page '{}' of '{}').", vendorsPage.getNumber(), vendorsPage.getTotalPages());
        return wrapper;
    }

    /******************************************************************************************************************
     * Method to persist a new Vendor.
     *
     * @param requestDto A data transfer object with values for the attributes of the Vendor, not null.
     * @return A wrapped data transfer object with either information on the created Vendor or failure messages.
     *
     * @throws IllegalArgumentException if requestDto parameter is null.
     *****************************************************************************************************************/
    @Override
    public VendorResponseWrapper createVendor(VendorRequestDto requestDto) throws IllegalArgumentException {

        // Validating input parameter.
        if (requestDto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method createVendor().");
        }
        String message;

        // Checking whether the name is already taken by another entity.
        Vendor conflictingEntity;
        try {
            conflictingEntity = repository.findFirstByName(requestDto.getName());
        } catch (Exception e) {
            message = VendorErrorMessages.ERROR_ON_RETRIEVAL_NAME.toString().concat("'" + requestDto.getName() + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.INTERNAL_SERVER_ERROR);
        }

        if (conflictingEntity != null) {
            message = VendorErrorMessages.DUPLICATE_VENDOR.toString().concat("'" + conflictingEntity.getId() + "'.");
            log.info(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.CONFLICT, message, VendorErrorMessages.DUPLICATE_VENDOR);
        }

        // Converting the request data transfer object to a database entity.
        Vendor entityToPersist;
        try {
            entityToPersist = converters.convertRequestDtoToEntity(requestDto, "");
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.INTERNAL_SERVER_ERROR);
        }

        // Attempting to create new Vendor entity.
        Vendor createdEntity;
        try {
            createdEntity = repository.save(entityToPersist);
        } catch (Exception e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.INTERNAL_SERVER_ERROR);
        }

        if (createdEntity == null) {
            message = VendorErrorMessages.ERROR_ON_CREATION.toString().concat("'" + requestDto.getName() + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.ERROR_ON_CREATION);
        }

        // Since the creation has been successful, enclosing the Vendor into a message.
        VendorResponseWrapper wrapper;
        try {
            wrapper = converters.convertEntityToResponseWrapper(createdEntity);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            message = VendorErrorMessages.ERROR_ON_CREATION.toString().concat("'" + requestDto.getName() + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.ERROR_ON_CREATION);
        }

        // Logging success and returning the result.
        log.info("Successfully created new Vendor in persistence layer with ID '{}' and Name '{}'.", createdEntity.getId(), createdEntity.getName());
        return wrapper;
    }

    /******************************************************************************************************************
     * Method to update an existing Vendor using its databaseID as unique identifier.
     *
     * @param requestDto A data transfer object with values for the attributes of the Vendor, not null.
     * @param databaseID A UUID that uniquely identifies an existing Vendor in the database, not null.
     * @return A wrapped data transfer object with either information on the updated Vendor or failure messages.
     *
     * @throws IllegalArgumentException if requestDto parameter is null.
     * @throws IllegalArgumentException if databaseID parameter is null or empty string.
     * @throws NonUuidArgumentException if databaseID parameter does not adhere to UUID format.
     *****************************************************************************************************************/
    @Override
    public VendorResponseWrapper updateVendor(VendorRequestDto requestDto, String databaseID) throws IllegalArgumentException {

        // Validating input parameters.
        if (requestDto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method updateVendor().");
        } else if (databaseID == null || databaseID.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method updateVendor().");
        } else if (ValidatorUUID.isValidUUIDFormat(databaseID).equals(Boolean.FALSE)) {
            throw new NonUuidArgumentException("Non-UUID parameter detected for method updateVendor().");
        }

        // Attempting to retrieve the entity corresponding to the databaseID.
        Vendor retrievedEntity;
        String message;
        try {
            retrievedEntity = repository.findById(databaseID).orElse(null);
        } catch (Exception e) {
            message = VendorErrorMessages.ERROR_ON_RETRIEVAL_ID.toString().concat("'" + databaseID + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.ERROR_ON_RETRIEVAL_ID);
        }

        // If nothing has been found, but not due to error, report accordingly.
        if (retrievedEntity == null) {
            message = VendorErrorMessages.VENDOR_NOT_FOUND_ID.toString().concat("'" + databaseID + "'.");
            log.info(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.NOT_FOUND, message, VendorErrorMessages.VENDOR_NOT_FOUND_ID);
        }

        // If a change to the name is being attempted, search for conflicts.
        if (!requestDto.getName().equals(retrievedEntity.getName())) {
            Vendor conflictingEntity;
            try {
                conflictingEntity = repository.findFirstByName(requestDto.getName());
            } catch (Exception e) {
                message = VendorErrorMessages.ERROR_ON_RETRIEVAL_NAME.toString().concat("'" + requestDto.getName() + "'.");
                log.error(message);
                return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.ERROR_ON_RETRIEVAL_NAME);
            }

            if (conflictingEntity != null) {
                message = VendorErrorMessages.DUPLICATE_VENDOR.toString().concat("'" + conflictingEntity.getId() + "'.");
                log.error(message);
                return converters.synthesizeResponseWrapperForError(ResponseCode.CONFLICT, message, VendorErrorMessages.DUPLICATE_VENDOR);
            }
        }

        // Converting the request data transfer object to a database entity.
        Vendor entityToPersist;
        try {
            entityToPersist = converters.convertRequestDtoToEntity(requestDto, databaseID);
            entityToPersist.setCreationDate(retrievedEntity.getCreationDate());
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.INTERNAL_SERVER_ERROR);
        }

        // Attempting to update Vendor entity.
        Vendor updatedEntity;
        try {
            updatedEntity = repository.save(entityToPersist);
        } catch (Exception e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.INTERNAL_SERVER_ERROR);
        }

        if (updatedEntity == null) {
            message = VendorErrorMessages.ERROR_ON_UPDATE.toString().concat("'" + databaseID + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.ERROR_ON_UPDATE);
        }

        // Since the creation has been successful, enclosing the Vendor into a message.
        VendorResponseWrapper wrapper;
        try {
            wrapper = converters.convertEntityToResponseWrapper(updatedEntity);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            message = VendorErrorMessages.ERROR_ON_UPDATE.toString().concat("'" + databaseID + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.INTERNAL_SERVER_ERROR);
        }

        // Logging success and returning the result.
        log.info("Successfully updated Vendor in persistence layer with ID '{}' and Name '{}'.", updatedEntity.getId(), updatedEntity.getName());
        return wrapper;
    }

    /******************************************************************************************************************
     * Method to delete an existing Vendor using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing Vendor in the database, not null.
     * @return A wrapped data transfer object with either information on the deleted Vendor or failure messages.
     *
     * @throws IllegalArgumentException if databaseID parameter is null or empty string.
     * @throws NonUuidArgumentException if databaseID parameter does not adhere to UUID format.
     *****************************************************************************************************************/
    @Override
    public VendorResponseWrapper deleteVendor(String databaseID) throws IllegalArgumentException {

        // Validating input parameter.
        if (databaseID == null || databaseID.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method deleteVendor().");
        } else if (ValidatorUUID.isValidUUIDFormat(databaseID).equals(Boolean.FALSE)) {
            throw new NonUuidArgumentException("Non-UUID parameter detected for method deleteVendor().");
        }

        // Attempting to retrieve the entity corresponding to the databaseID.
        Vendor entity;
        String message;
        try {
            entity = repository.findById(databaseID).orElse(null);
        } catch (Exception e) {
            message = VendorErrorMessages.ERROR_ON_DELETION_ID.toString().concat("'" + databaseID + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.ERROR_ON_DELETION_ID);
        }

        // If nothing has been found, but not due to error, report accordingly.
        if (entity == null) {
            message = VendorErrorMessages.VENDOR_NOT_FOUND_ID.toString().concat("'" + databaseID + "'.");
            log.info(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.NOT_FOUND, message, VendorErrorMessages.VENDOR_NOT_FOUND_ID);
        }

        // Attempting to delete the entity corresponding to the databaseID.
        try {
            repository.deleteById(databaseID);
        } catch (Exception e) {
            message = VendorErrorMessages.ERROR_ON_DELETION_ID.toString().concat("'" + databaseID + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.ERROR_ON_DELETION_ID);
        }

        // Since the retrieval has been successful, enclosing the Vendor into a message.
        VendorResponseWrapper wrapper;
        try {
            wrapper = converters.convertEntityToResponseWrapper(entity);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            message = VendorErrorMessages.ERROR_ON_DELETION_ID.toString().concat("'" + databaseID + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.ERROR_ON_DELETION_ID);
        }

        // Logging success and returning the result.
        log.info("Successfully deleted Vendor from persistence layer with ID: '{}'.", databaseID);
        return wrapper;
    }

    /******************************************************************************************************************
     * Method to delete all existing Vendors.
     *
     * @return A wrapped data transfer object with either a success message or failure messages.
     *****************************************************************************************************************/
    @Override
    public VendorResponseWrapper deleteAllVendors() {

        // Checking whether there is anything to delete.
        String message;
        try {
            long count = repository.count();
            if (count == 0L) {
                message = VendorErrorMessages.NO_VENDORS_FOUND.toString();
                log.info(message);
                return converters.synthesizeResponseWrapperForError(ResponseCode.NOT_FOUND, message, VendorErrorMessages.NO_VENDORS_FOUND);
            }
        } catch (Exception e) {
            message = VendorErrorMessages.ERROR_ON_DELETION_MANY.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.ERROR_ON_DELETION_MANY);
        }

        // Attempting to delete all Vendor entities from the database.
        try {
            repository.deleteAll();
        } catch (Exception e) {
            message = VendorErrorMessages.ERROR_ON_DELETION_MANY.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.ERROR_ON_DELETION_MANY);
        }

        // Logging success and returning the result.
        VendorResponseWrapper wrapper = new VendorResponseWrapper();
        wrapper.setCode(ResponseCode.SUCCESS);
        wrapper.setMessage("Database transaction successfully concluded.");

        log.info("Successfully deleted all Vendors from the persistence layer.");
        return wrapper;
    }
}
