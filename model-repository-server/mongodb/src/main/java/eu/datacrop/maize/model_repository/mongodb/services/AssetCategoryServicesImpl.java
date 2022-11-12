package eu.datacrop.maize.model_repository.mongodb.services;

import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.AssetCategoryRequestDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.error.exceptions.NonUuidArgumentException;
import eu.datacrop.maize.model_repository.commons.error.messages.AssetCategoryErrorMessages;
import eu.datacrop.maize.model_repository.commons.util.ValidatorUUID;
import eu.datacrop.maize.model_repository.commons.wrappers.PaginationInfo;
import eu.datacrop.maize.model_repository.commons.wrappers.collection.AssetCategoryResponsesWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.entities.AssetCategoryResponseWrapper;
import eu.datacrop.maize.model_repository.mongodb.converters.entities.AssetCategoryConverters;
import eu.datacrop.maize.model_repository.mongodb.model.entities.AssetCategory;
import eu.datacrop.maize.model_repository.mongodb.repositories.AssetCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**********************************************************************************************************************
 * This class implements the services offered by Mongo databases pertaining to the persistence of Asset Categories.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
@Slf4j
@Service
@Profile("devmongo")
public class AssetCategoryServicesImpl implements AssetCategoryServices {

    @Autowired
    AssetCategoryRepository repository;

    @Autowired
    AssetCategoryConverters converters;


    /******************************************************************************************************************
     * Method to retrieve an existing Asset Category using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing Asset Category in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved Asset Category or failure messages.
     *
     * @throws IllegalArgumentException if databaseID parameter is null or empty string.
     * @throws NonUuidArgumentException if databaseID parameter does not adhere to UUID format.
     *****************************************************************************************************************/
    @Override
    public AssetCategoryResponseWrapper retrieveAssetCategoryByDatabaseID(String databaseID) throws IllegalArgumentException, NonUuidArgumentException {

        // Validating input parameter.
        if (databaseID == null || databaseID.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method retrieveAssetCategoryByDatabaseID().");
        } else if (ValidatorUUID.isValidUUIDFormat(databaseID).equals(Boolean.FALSE)) {
            throw new NonUuidArgumentException("Non-UUID parameter detected for method retrieveAssetCategoryByDatabaseID().");
        }

        // Attempting to retrieve the entity corresponding to the databaseID.
        AssetCategory entity;
        String message;
        try {
            entity = repository.findById(databaseID).orElse(null);
        } catch (Exception e) {
            message = AssetCategoryErrorMessages.ERROR_ON_RETRIEVAL_ID.toString().concat("'" + databaseID + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.ERROR_ON_RETRIEVAL_ID);
        }

        // If nothing has been found, but not due to error, report accordingly.
        if (entity == null) {
            message = AssetCategoryErrorMessages.ASSET_CATEGORY_NOT_FOUND_ID.toString().concat("'" + databaseID + "'.");
            log.info(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.NOT_FOUND, message, AssetCategoryErrorMessages.ASSET_CATEGORY_NOT_FOUND_ID);
        }

        // Since the retrieval has been successful, enclosing the AssetCategory into a message.
        AssetCategoryResponseWrapper wrapper;
        try {
            wrapper = converters.convertEntityToResponseWrapper(entity);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            message = AssetCategoryErrorMessages.ERROR_ON_RETRIEVAL_ID.toString().concat("'" + databaseID + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.ERROR_ON_RETRIEVAL_ID);
        }

        // Logging success and returning the result.
        log.info("Successfully retrieved Asset Category from persistence layer with ID: '{}'.", databaseID);
        return wrapper;
    }

    /******************************************************************************************************************
     * Method to retrieve an existing Asset Category using its name as unique identifier.
     *
     * @param name A string that uniquely identifies an existing Asset Category in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved Asset Category or failure messages.
     *
     * @throws IllegalArgumentException if name parameter is null or an empty string.
     *****************************************************************************************************************/
    @Override
    public AssetCategoryResponseWrapper retrieveAssetCategoryByName(String name) throws IllegalArgumentException {

        // Validating input parameter.
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method retrieveAssetCategoryByName().");
        }

        // Attempting to retrieve the entity corresponding to the name.
        AssetCategory entity;
        String message;
        try {
            entity = repository.findFirstByName(name);
        } catch (Exception e) {
            message = AssetCategoryErrorMessages.ERROR_ON_RETRIEVAL_NAME.toString().concat("'" + name + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.ERROR_ON_RETRIEVAL_NAME);
        }

        // If nothing has been found, but not due to error, report accordingly.
        if (entity == null) {
            message = AssetCategoryErrorMessages.ASSET_CATEGORY_NOT_FOUND_NAME.toString().concat("'" + name + "'.");
            log.info(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.NOT_FOUND, message, AssetCategoryErrorMessages.ASSET_CATEGORY_NOT_FOUND_NAME);
        }

        // Since the retrieval has been successful, enclosing the AssetCategory into a message.
        AssetCategoryResponseWrapper wrapper;
        try {
            wrapper = converters.convertEntityToResponseWrapper(entity);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            message = AssetCategoryErrorMessages.ERROR_ON_RETRIEVAL_NAME.toString().concat("'" + name + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.ERROR_ON_RETRIEVAL_NAME);
        }

        // Logging success and returning the result.
        log.info("Successfully retrieved Asset Category from persistence layer with Name: '{}'.", name);
        return wrapper;
    }

    /******************************************************************************************************************
     * Method to retrieve all Asset Categories paginated.
     *
     * @param page The page to retrieve.
     * @param size The intended size of pages.
     * @return A wrapped data transfer object with either information on the retrieved Asset Categories or failure messages.
     *****************************************************************************************************************/
    @Override
    public AssetCategoryResponsesWrapper retrieveAllAssetCategories(int page, int size) {

        // Attempting to retrieve the all entities indicated by the pagination instructions.
        Pageable paging = PageRequest.of(page, size);
        Page<AssetCategory> assetCategoriesPage;
        List<AssetCategory> entities;
        PaginationInfo paginationInfo;
        String message;

        try {
            assetCategoriesPage = repository.findAll(paging);
            entities = assetCategoriesPage.getContent();
            paginationInfo = new PaginationInfo(assetCategoriesPage.getTotalElements(), assetCategoriesPage.getTotalPages(), assetCategoriesPage.getNumber());
        } catch (Exception e) {
            message = AssetCategoryErrorMessages.ERROR_ON_RETRIEVAL_MANY.toString();
            log.error(message);
            return converters.synthesizeResponsesWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.ERROR_ON_RETRIEVAL_MANY);
        }

        // If nothing has been found, but not due to error, report accordingly.
        if (entities == null || entities.size() == 0) {

            // AssetCategories are available but the request was out of pagination limits.
            if (paginationInfo.getTotalItems() > 0) {
                message = AssetCategoryErrorMessages.EXCEEDED_PAGE_LIMIT.toString().concat(" Total Pages: " + paginationInfo.getTotalPages());
                log.info(message);
                return converters.synthesizeResponsesWrapperForError(ResponseCode.NOT_FOUND, message, AssetCategoryErrorMessages.EXCEEDED_PAGE_LIMIT);
            }

            // AssetCategories are not available at all.
            message = AssetCategoryErrorMessages.NO_ASSET_CATEGORIES_FOUND.toString();
            log.info(message);
            return converters.synthesizeResponsesWrapperForError(ResponseCode.NOT_FOUND, message, AssetCategoryErrorMessages.NO_ASSET_CATEGORIES_FOUND);
        }

        // Since the retrieval has been successful, enclosing the collection of AssetCategories into a message.
        AssetCategoryResponsesWrapper wrapper;
        try {
            wrapper = converters.convertEntitiesToResponseWrapper(entities, paginationInfo);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponsesWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            message = AssetCategoryErrorMessages.ERROR_ON_RETRIEVAL_MANY.toString();
            log.error(message);
            return converters.synthesizeResponsesWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.ERROR_ON_RETRIEVAL_MANY);
        }

        // Logging success and returning the result.
        log.info("Successfully retrieved all Asset Category entities from persistence layer (Page '{}' of '{}').", assetCategoriesPage.getNumber(), assetCategoriesPage.getTotalPages());
        return wrapper;
    }

    /******************************************************************************************************************
     * Method to persist a new Asset Category.
     *
     * @param requestDto A data transfer object with values for the attributes of the Asset Category, not null.
     * @return A wrapped data transfer object with either information on the created Asset Category or failure messages.
     *
     * @throws IllegalArgumentException if requestDto parameter is null.
     *****************************************************************************************************************/
    @Override
    public AssetCategoryResponseWrapper createAssetCategory(AssetCategoryRequestDto requestDto) throws IllegalArgumentException {

        // Validating input parameter.
        if (requestDto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method createAssetCategory().");
        }
        String message;

        // Checking whether the name is already taken by another entity.
        AssetCategory conflictingEntity;
        try {
            conflictingEntity = repository.findFirstByName(requestDto.getName());
        } catch (Exception e) {
            message = AssetCategoryErrorMessages.ERROR_ON_RETRIEVAL_NAME.toString().concat("'" + requestDto.getName() + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.INTERNAL_SERVER_ERROR);
        }

        if (conflictingEntity != null) {
            message = AssetCategoryErrorMessages.DUPLICATE_ASSET_CATEGORY.toString().concat("'" + conflictingEntity.getId() + "'.");
            log.info(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.CONFLICT, message, AssetCategoryErrorMessages.DUPLICATE_ASSET_CATEGORY);
        }

        // Converting the request data transfer object to a database entity.
        AssetCategory entityToPersist;
        try {
            entityToPersist = converters.convertRequestDtoToEntity(requestDto, "");
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.INTERNAL_SERVER_ERROR);
        }

        // Attempting to create new AssetCategory entity.
        AssetCategory createdEntity;
        try {
            createdEntity = repository.save(entityToPersist);
        } catch (Exception e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.INTERNAL_SERVER_ERROR);
        }

        if (createdEntity == null) {
            message = AssetCategoryErrorMessages.ERROR_ON_CREATION.toString().concat("'" + requestDto.getName() + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.ERROR_ON_CREATION);
        }

        // Since the creation has been successful, enclosing the AssetCategory into a message.
        AssetCategoryResponseWrapper wrapper;
        try {
            wrapper = converters.convertEntityToResponseWrapper(createdEntity);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            message = AssetCategoryErrorMessages.ERROR_ON_CREATION.toString().concat("'" + requestDto.getName() + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.ERROR_ON_CREATION);
        }

        // Logging success and returning the result.
        log.info("Successfully created new Asset Category in persistence layer with ID '{}' and Name '{}'.", createdEntity.getId(), createdEntity.getName());
        return wrapper;
    }

    /******************************************************************************************************************
     * Method to update an existing Asset Category using its databaseID as unique identifier.
     *
     * @param requestDto A data transfer object with values for the attributes of the Asset Category, not null.
     * @param databaseID A UUID that uniquely identifies an existing Asset Category in the database, not null.
     * @return A wrapped data transfer object with either information on the updated Asset Category or failure messages.
     *
     * @throws IllegalArgumentException if requestDto parameter is null.
     * @throws IllegalArgumentException if databaseID parameter is null or empty string.
     * @throws NonUuidArgumentException if databaseID parameter does not adhere to UUID format.
     *****************************************************************************************************************/
    @Override
    public AssetCategoryResponseWrapper updateAssetCategory(AssetCategoryRequestDto requestDto, String databaseID) throws IllegalArgumentException {

        // Validating input parameters.
        if (requestDto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method updateAssetCategory().");
        } else if (databaseID == null || databaseID.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method updateAssetCategory().");
        } else if (ValidatorUUID.isValidUUIDFormat(databaseID).equals(Boolean.FALSE)) {
            throw new NonUuidArgumentException("Non-UUID parameter detected for method updateAssetCategory().");
        }

        // Attempting to retrieve the entity corresponding to the databaseID.
        AssetCategory retrievedEntity;
        String message;
        try {
            retrievedEntity = repository.findById(databaseID).orElse(null);
        } catch (Exception e) {
            message = AssetCategoryErrorMessages.ERROR_ON_RETRIEVAL_ID.toString().concat("'" + databaseID + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.ERROR_ON_RETRIEVAL_ID);
        }

        // If nothing has been found, but not due to error, report accordingly.
        if (retrievedEntity == null) {
            message = AssetCategoryErrorMessages.ASSET_CATEGORY_NOT_FOUND_ID.toString().concat("'" + databaseID + "'.");
            log.info(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.NOT_FOUND, message, AssetCategoryErrorMessages.ASSET_CATEGORY_NOT_FOUND_ID);
        }

        // If a change to the name is being attempted, search for conflicts.
        if (!requestDto.getName().equals(retrievedEntity.getName())) {
            AssetCategory conflictingEntity;
            try {
                conflictingEntity = repository.findFirstByName(requestDto.getName());
            } catch (Exception e) {
                message = AssetCategoryErrorMessages.ERROR_ON_RETRIEVAL_NAME.toString().concat("'" + requestDto.getName() + "'.");
                log.error(message);
                return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.ERROR_ON_RETRIEVAL_NAME);
            }

            if (conflictingEntity != null) {
                message = AssetCategoryErrorMessages.DUPLICATE_ASSET_CATEGORY.toString().concat("'" + conflictingEntity.getId() + "'.");
                log.error(message);
                return converters.synthesizeResponseWrapperForError(ResponseCode.CONFLICT, message, AssetCategoryErrorMessages.DUPLICATE_ASSET_CATEGORY);
            }
        }

        // Converting the request data transfer object to a database entity.
        AssetCategory entityToPersist;
        try {
            entityToPersist = converters.convertRequestDtoToEntity(requestDto, databaseID);
            entityToPersist.setCreationDate(retrievedEntity.getCreationDate());
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.INTERNAL_SERVER_ERROR);
        }

        // Attempting to update AssetCategory entity.
        AssetCategory updatedEntity;
        try {
            updatedEntity = repository.save(entityToPersist);
        } catch (Exception e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.INTERNAL_SERVER_ERROR);
        }

        if (updatedEntity == null) {
            message = AssetCategoryErrorMessages.ERROR_ON_UPDATE.toString().concat("'" + databaseID + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.ERROR_ON_UPDATE);
        }

        // Since the creation has been successful, enclosing the AssetCategory into a message.
        AssetCategoryResponseWrapper wrapper;
        try {
            wrapper = converters.convertEntityToResponseWrapper(updatedEntity);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            message = AssetCategoryErrorMessages.ERROR_ON_UPDATE.toString().concat("'" + databaseID + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.INTERNAL_SERVER_ERROR);
        }

        // Logging success and returning the result.
        log.info("Successfully updated Asset Category in persistence layer with ID '{}' and Name '{}'.", updatedEntity.getId(), updatedEntity.getName());
        return wrapper;
    }

    /******************************************************************************************************************
     * Method to delete an existing Asset Category using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing Asset Category in the database, not null.
     * @return A wrapped data transfer object with either information on the deleted Asset Category or failure messages.
     *
     * @throws IllegalArgumentException if databaseID parameter is null or empty string.
     * @throws NonUuidArgumentException if databaseID parameter does not adhere to UUID format.
     *****************************************************************************************************************/
    @Override
    public AssetCategoryResponseWrapper deleteAssetCategory(String databaseID) throws IllegalArgumentException {

        // Validating input parameter.
        if (databaseID == null || databaseID.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method deleteAssetCategory().");
        } else if (ValidatorUUID.isValidUUIDFormat(databaseID).equals(Boolean.FALSE)) {
            throw new NonUuidArgumentException("Non-UUID parameter detected for method deleteAssetCategory().");
        }

        // Attempting to retrieve the entity corresponding to the databaseID.
        AssetCategory entity;
        String message;
        try {
            entity = repository.findById(databaseID).orElse(null);
        } catch (Exception e) {
            message = AssetCategoryErrorMessages.ERROR_ON_DELETION_ID.toString().concat("'" + databaseID + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.ERROR_ON_DELETION_ID);
        }

        // If nothing has been found, but not due to error, report accordingly.
        if (entity == null) {
            message = AssetCategoryErrorMessages.ASSET_CATEGORY_NOT_FOUND_ID.toString().concat("'" + databaseID + "'.");
            log.info(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.NOT_FOUND, message, AssetCategoryErrorMessages.ASSET_CATEGORY_NOT_FOUND_ID);
        }

        // Attempting to delete the entity corresponding to the databaseID.
        try {
            repository.deleteById(databaseID);
        } catch (Exception e) {
            message = AssetCategoryErrorMessages.ERROR_ON_DELETION_ID.toString().concat("'" + databaseID + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.ERROR_ON_DELETION_ID);
        }

        // Since the retrieval has been successful, enclosing the AssetCategory into a message.
        AssetCategoryResponseWrapper wrapper;
        try {
            wrapper = converters.convertEntityToResponseWrapper(entity);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            message = AssetCategoryErrorMessages.ERROR_ON_DELETION_ID.toString().concat("'" + databaseID + "'.");
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.ERROR_ON_DELETION_ID);
        }

        // Logging success and returning the result.
        log.info("Successfully deleted Asset Category from persistence layer with ID: '{}'.", databaseID);
        return wrapper;
    }

    /******************************************************************************************************************
     * Method to delete all existing Asset Categories.
     *
     * @return A wrapped data transfer object with either a success message or failure messages.
     *****************************************************************************************************************/
    @Override
    public AssetCategoryResponseWrapper deleteAllAssetCategories() {

        // Checking whether there is anything to delete.
        String message;
        try {
            long count = repository.count();
            if (count == 0L) {
                message = AssetCategoryErrorMessages.NO_ASSET_CATEGORIES_FOUND.toString();
                log.info(message);
                return converters.synthesizeResponseWrapperForError(ResponseCode.NOT_FOUND, message, AssetCategoryErrorMessages.NO_ASSET_CATEGORIES_FOUND);
            }
        } catch (Exception e) {
            message = AssetCategoryErrorMessages.ERROR_ON_DELETION_MANY.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.ERROR_ON_DELETION_MANY);
        }

        // Attempting to delete all AssetCategory entities from the database.
        try {
            repository.deleteAll();
        } catch (Exception e) {
            message = AssetCategoryErrorMessages.ERROR_ON_DELETION_MANY.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.ERROR_ON_DELETION_MANY);
        }

        // Logging success and returning the result.
        AssetCategoryResponseWrapper wrapper = new AssetCategoryResponseWrapper();
        wrapper.setCode(ResponseCode.SUCCESS);
        wrapper.setMessage("Database transaction successfully concluded.");

        log.info("Successfully deleted all Asset Categories from the persistence layer.");
        return wrapper;
    }
}
