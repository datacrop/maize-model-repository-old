package eu.datacrop.maize.model_repository.mongodb.daos;

import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.AssetCategoryRequestDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.error.exceptions.NonUuidArgumentException;
import eu.datacrop.maize.model_repository.commons.error.messages.AssetCategoryErrorMessages;
import eu.datacrop.maize.model_repository.commons.wrappers.collection.AssetCategoryResponsesWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.entities.AssetCategoryResponseWrapper;
import eu.datacrop.maize.model_repository.mongodb.converters.entities.AssetCategoryConverters;
import eu.datacrop.maize.model_repository.mongodb.services.AssetCategoryServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**********************************************************************************************************************
 * This class implements the entry points (Data Access Objects) to the services offered by Mongo databases
 * pertaining to the persistence of Asset Categories. It also handles exceptions related to invalid method arguments.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
@Slf4j
@Service
@Profile("devmongo")
public class AssetCategoryMongoDbDaoImpl implements AssetCategoryMongoDbDao {

    @Autowired
    AssetCategoryServices services;

    @Autowired
    AssetCategoryConverters converters;

    /******************************************************************************************************************
     * Method to retrieve an existing Asset Category using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing Asset Category in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved Asset Category or failure messages.
     *
     *****************************************************************************************************************/
    @Override
    public AssetCategoryResponseWrapper retrieveAssetCategoryByDatabaseID(String databaseID) {

        log.info("MongoDB received request for retrieval of Asset Category with ID: '{}'.", databaseID);
        AssetCategoryResponseWrapper wrapper;
        String message;
        try {
            wrapper = services.retrieveAssetCategoryByDatabaseID(databaseID);
        } catch (NonUuidArgumentException e) {
            message = AssetCategoryErrorMessages.INVALID_PARAMETER_FORMAT.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.INVALID_PARAMETER_FORMAT);
        } catch (IllegalArgumentException e) {
            message = AssetCategoryErrorMessages.INVALID_PARAMETERS.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.INVALID_PARAMETERS);
        }
        return wrapper;
    }

    /******************************************************************************************************************
     * Method to retrieve an existing Asset Category using its name as unique identifier.
     *
     * @param name A string that uniquely identifies an existing Asset Category in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved Asset Category or failure messages.
     *****************************************************************************************************************/
    @Override
    public AssetCategoryResponseWrapper retrieveAssetCategoryByName(String name) {

        log.info("MongoDB received request for retrieval of Asset Category with Name: '{}'.", name);
        AssetCategoryResponseWrapper wrapper;
        String message;
        try {
            wrapper = services.retrieveAssetCategoryByName(name);
        } catch (IllegalArgumentException e) {
            message = AssetCategoryErrorMessages.INVALID_PARAMETERS.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.INVALID_PARAMETERS);
        }
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
        log.info("MongoDB received request for retrieval of all Asset Categories.");
        return services.retrieveAllAssetCategories(page, size);
    }

    /******************************************************************************************************************
     * Method to persist a new Asset Category.
     *
     * @param requestDto A data transfer object with values for the attributes of the Asset Category, not null.
     * @return A wrapped data transfer object with either information on the created Asset Category or failure messages.
     *****************************************************************************************************************/
    @Override
    public AssetCategoryResponseWrapper createAssetCategory(AssetCategoryRequestDto requestDto) {

        log.info("MongoDB received request for creation of new Asset Category.");
        AssetCategoryResponseWrapper wrapper;
        String message;
        try {
            wrapper = services.createAssetCategory(requestDto);
        } catch (IllegalArgumentException e) {
            message = AssetCategoryErrorMessages.INVALID_PARAMETERS.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.INVALID_PARAMETERS);
        }
        return wrapper;
    }

    /******************************************************************************************************************
     * Method to update an existing Asset Category using its databaseID as unique identifier.
     *
     * @param requestDto A data transfer object with values for the attributes of the Asset Category, not null.
     * @param databaseID A UUID that uniquely identifies an existing Asset Category in the database, not null.
     * @return A wrapped data transfer object with either information on the updated Asset Category or failure messages.
     *****************************************************************************************************************/
    @Override
    public AssetCategoryResponseWrapper updateAssetCategory(AssetCategoryRequestDto requestDto, String databaseID) {

        log.info("MongoDB received request for update of Asset Category with ID: '{}'.", databaseID);
        AssetCategoryResponseWrapper wrapper;
        String message;
        try {
            wrapper = services.updateAssetCategory(requestDto, databaseID);
        } catch (NonUuidArgumentException e) {
            message = AssetCategoryErrorMessages.INVALID_PARAMETER_FORMAT.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.INVALID_PARAMETER_FORMAT);
        } catch (IllegalArgumentException e) {
            message = AssetCategoryErrorMessages.INVALID_PARAMETERS.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.INVALID_PARAMETERS);
        }
        return wrapper;
    }

    /******************************************************************************************************************
     * Method to delete an existing Asset Category using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing Asset Category in the database, not null.
     * @return A wrapped data transfer object with either information on the deleted Asset Category or failure messages.
     *****************************************************************************************************************/
    @Override
    public AssetCategoryResponseWrapper deleteAssetCategory(String databaseID) {

        log.info("MongoDB received request for deletion of Asset Category with ID: '{}'.", databaseID);
        AssetCategoryResponseWrapper wrapper;
        String message;
        try {
            wrapper = services.deleteAssetCategory(databaseID);
        } catch (NonUuidArgumentException e) {
            message = AssetCategoryErrorMessages.INVALID_PARAMETER_FORMAT.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.INVALID_PARAMETER_FORMAT);
        } catch (IllegalArgumentException e) {
            message = AssetCategoryErrorMessages.INVALID_PARAMETERS.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, AssetCategoryErrorMessages.INVALID_PARAMETERS);
        }
        return wrapper;
    }

    /******************************************************************************************************************
     * Method to delete all existing Asset Categories.
     *
     * @return A wrapped data transfer object with either a success message or failure messages.
     *****************************************************************************************************************/
    @Override
    public AssetCategoryResponseWrapper deleteAllAssetCategories() {
        log.info("MongoDB received request for deletion of all Asset Categories.");
        return services.deleteAllAssetCategories();
    }
}
