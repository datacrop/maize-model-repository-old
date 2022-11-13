package eu.datacrop.maize.model_repository.persistence.mongo_implementation;

import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.AssetCategoryRequestDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.error.messages.AssetCategoryErrorMessages;
import eu.datacrop.maize.model_repository.commons.wrappers.collection.AssetCategoryResponsesWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.entities.AssetCategoryResponseWrapper;
import eu.datacrop.maize.model_repository.mongodb.services.AssetCategoryServices;
import eu.datacrop.maize.model_repository.persistence.daos.AssetCategoryPersistenceLayerDaos;
import eu.datacrop.maize.model_repository.persistence.validators.AssetCategoryValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**********************************************************************************************************************
 * This class redirects enquires to the persistence layer pertaining to AssetCategories. Implementation for MongoDB.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
@Slf4j
@Service
@Profile("devmongo")
public class AssetCategoryMongoDaos implements AssetCategoryPersistenceLayerDaos {

    @Autowired
    AssetCategoryServices services;

    @Autowired
    AssetCategoryValidator validator;


    /******************************************************************************************************************
     * Method to retrieve an existing Asset Category using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing Asset Category in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved Asset Category or failure messages.
     *
     * @throws IllegalArgumentException - if requestDto is null.
     *****************************************************************************************************************/
    @Override
    public AssetCategoryResponseWrapper retrieveAssetCategoryByDatabaseID(String databaseID) throws IllegalArgumentException {

        // Checking input parameters.
        if (databaseID.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method AssetCategoryMongoDaos.retrieveAssetCategoryByDatabaseID().");
        }

        log.info("Persistence layer (MongoDB) received request for retrieval of Asset Category with ID: '{}'.", databaseID);
        return services.retrieveAssetCategoryByDatabaseID(databaseID);
    }

    /******************************************************************************************************************
     * Method to retrieve an existing Asset Category using its name as unique identifier.
     *
     * @param name A string that uniquely identifies an existing Asset Category in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved Asset Category or failure messages.
     *
     * @throws IllegalArgumentException - if name is null.
     *****************************************************************************************************************/
    @Override
    public AssetCategoryResponseWrapper retrieveAssetCategoryByName(String name) throws IllegalArgumentException {

        // Checking input parameters.
        if (name.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method AssetCategoryMongoDaos.retrieveAssetCategoryByName().");
        }

        log.info("Persistence layer (MongoDB) received request for retrieval of Asset Category with Name: '{}'.", name);
        return services.retrieveAssetCategoryByName(name);
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
        log.info("Persistence layer (MongoDB) received request for retrieval of all Asset Categories.");
        return services.retrieveAllAssetCategories(page, size);
    }

    /******************************************************************************************************************
     * Method to persist a new Asset Category.
     *
     * @param requestDto A data transfer object with values for the attributes of the Asset Category, not null.
     * @return A wrapped data transfer object with either information on the created Asset Category or failure messages.
     *
     * @throws IllegalArgumentException - if requestDto is null.
     *****************************************************************************************************************/
    @Override
    public AssetCategoryResponseWrapper createAssetCategory(AssetCategoryRequestDto requestDto) throws IllegalArgumentException {

        // Checking input parameters.
        if (requestDto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method AssetCategoryMongoDaos.createAssetCategory().");
        }

        log.info("Persistence layer (MongoDB) received request for creation of new Asset Category.");

        // Making the validator specific for AssetCategories.
        requestDto.setValidator(this.validator);

        // Performing validations of data transfer object.
        AssetCategoryResponseWrapper wrapper = (AssetCategoryResponseWrapper) requestDto.performValidation();
        if (!wrapper.getCode().equals(ResponseCode.SUCCESS)) {
            // Aborting if issues have been discovered.
            log.debug("Issues discovered during attribute validation.");
            return synthesizeResponseWrapperForError(wrapper.getCode(), wrapper.getMessage(), wrapper.getErrorCode());
        }

        // Continuing if issues have not been discovered.
        return services.createAssetCategory(requestDto);
    }

    /******************************************************************************************************************
     * Method to update an existing Asset Category using its databaseID as unique identifier.
     *
     * @param requestDto A data transfer object with values for the attributes of the Asset Category, not null.
     * @param databaseID A UUID that uniquely identifies an existing Asset Category in the database, not null.
     * @return A wrapped data transfer object with either information on the updated Asset Category or failure messages.
     *
     * @throws IllegalArgumentException - if requestDto is null.
     * @throws IllegalArgumentException - if databaseID is null or empty string.
     *****************************************************************************************************************/
    @Override
    public AssetCategoryResponseWrapper updateAssetCategory(AssetCategoryRequestDto requestDto, String databaseID) throws IllegalArgumentException {

        // Checking input parameters.
        if (requestDto == null || databaseID.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method AssetCategoryMongoDaos.updateAssetCategory().");
        }

        log.info("Persistence layer (MongoDB) received request for update of Asset Category with ID: '{}'.", databaseID);

        // Making the validator specific for AssetCategories.
        requestDto.setValidator(this.validator);

        // Performing validations of data transfer object.
        AssetCategoryResponseWrapper wrapper = requestDto.performValidation();
        if (!wrapper.getCode().equals(ResponseCode.SUCCESS)) {
            // Aborting if issues have been discovered.
            log.debug("Issues discovered during attribute validation.");
            return synthesizeResponseWrapperForError(wrapper.getCode(), wrapper.getMessage(), wrapper.getErrorCode());
        }

        // Continuing if issues have not been discovered.
        return services.updateAssetCategory(requestDto, databaseID);
    }

    /******************************************************************************************************************
     * Method to delete an existing Asset Category using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing Asset Category in the database, not null.
     * @return A wrapped data transfer object with either information on the deleted Asset Category or failure messages.
     *
     * @throws IllegalArgumentException - if databaseID is null or empty string.
     *****************************************************************************************************************/
    @Override
    public AssetCategoryResponseWrapper deleteAssetCategory(String databaseID) throws IllegalArgumentException {

        // Checking input parameters.
        if (databaseID.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method AssetCategoryMongoDaos.deleteAssetCategory().");
        }

        log.info("Persistence layer (MongoDB) received request for deletion of Asset Category with ID: '{}'.", databaseID);
        return services.deleteAssetCategory(databaseID);
    }

    /******************************************************************************************************************
     * Method to delete all existing Asset Categories.
     *
     * @return A wrapped data transfer object with either a success message or failure messages.
     *****************************************************************************************************************/
    @Override
    public AssetCategoryResponseWrapper deleteAllAssetCategories() {
        log.info("Persistence layer (MongoDB) received request for deletion of all Asset Categories.");
        return services.deleteAllAssetCategories();
    }

    /******************************************************************************************************************
     * Method that creates an error report.
     *
     * @param code A response code to be translated to an appropriate Http code.
     * @param message The message describing the actual error.
     * @param errorCode A shorthand key for the error.
     * @return A wrapped data transfer object with either a success message or failure messages.
     *
     * @throws IllegalArgumentException - if code is null or empty.
     * @throws IllegalArgumentException - if code is ResponseCode.SUCCESS or ResponseCode.UNDEFINED.
     *****************************************************************************************************************/
    private AssetCategoryResponseWrapper synthesizeResponseWrapperForError(ResponseCode code, String message, AssetCategoryErrorMessages errorCode) throws IllegalArgumentException {

        if (code == null || code.equals(ResponseCode.SUCCESS) || code.equals(ResponseCode.UNDEFINED) || message.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method AssetCategoryMongoDaos.synthesizeResponseWrapperForError().");
        }

        AssetCategoryResponseWrapper wrapper = new AssetCategoryResponseWrapper();
        wrapper.setCode(code);
        wrapper.setMessage(message);
        wrapper.setResponse(null);
        wrapper.setErrorCode(errorCode);

        log.debug("Successfully produced ResponseWrapper for unsuccessful database transaction.");

        return wrapper;
    }
}
