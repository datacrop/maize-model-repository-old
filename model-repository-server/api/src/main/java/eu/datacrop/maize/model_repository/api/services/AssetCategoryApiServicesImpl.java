package eu.datacrop.maize.model_repository.api.services;

import eu.datacrop.maize.model_repository.api.error.ErrorMessage;
import eu.datacrop.maize.model_repository.api.error.ErrorMessages;
import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.AssetCategoryRequestDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.util.ValidatorUUID;
import eu.datacrop.maize.model_repository.commons.wrappers.collection.AssetCategoryResponsesWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.entities.AssetCategoryResponseWrapper;
import eu.datacrop.maize.model_repository.services.persistence.AssetCategoryPersistenceServicesDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


/**********************************************************************************************************************
 * This class implements the services offered by the API layer pertaining to Asset Categories.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
@Slf4j
@Service
public class AssetCategoryApiServicesImpl implements AssetCategoryApiServices {

    @Autowired
    AssetCategoryPersistenceServicesDao services;

    /******************************************************************************************************************
     * Method that connects to the persistence layer to retrieve an existing Asset Category using its databaseID
     * as unique identifier.
     *
     * @param assetCategoryID A UUID that uniquely identifies an existing Asset Category in the database, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @Override
    public ResponseEntity retrieveAssetCategoryByDatabaseID(String assetCategoryID) {

        // Checking that the mandatory assetCategory identifier has a value.
        if (assetCategoryID == null || assetCategoryID.isBlank()) {
            log.info("Attempt to retrieve AssetCategory without specifying a unique UUID detected. Operation aborted.");
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    ErrorMessages.IDENTIFIER_MISSING.getErrorMessage(),
                    ErrorMessages.IDENTIFIER_MISSING.name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // An identifier that can be parsed to a UUID is mandatory.
        Boolean testResult = ValidatorUUID.isValidUUIDFormat(assetCategoryID);
        if (testResult == Boolean.FALSE) {
            log.info("Attempt to retrieve Asset Category with non-UUID identifier detected. Operation aborted.");
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    ErrorMessages.IDENTIFIER_NOT_UUID.getErrorMessage(),
                    ErrorMessages.IDENTIFIER_NOT_UUID.name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // Querying the persistence layer.
        AssetCategoryResponseWrapper wrapper;
        try {
            wrapper = services.retrieveAssetCategoryByDatabaseID(assetCategoryID);
        } catch (Exception e) {
            log.error("Internal error occurred after attempt to retrieve Asset Category with ID: '{}'. Message: '{}'", assetCategoryID, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.toString(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Reporting server errors.
        if (wrapper == null
                || wrapper.getCode().equals(ResponseCode.ERROR)
                || wrapper.getCode().equals(ResponseCode.UNDEFINED)
                || wrapper.getCode().equals(ResponseCode.CONFLICT)) {
            log.error("Internal error occurred after attempt  to retrieve Asset Category with ID: '{}'. Message: '{}'",
                    assetCategoryID, wrapper != null ? wrapper.getMessage() : "Details unknown.");
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Reporting problematic client requests.
        if (wrapper.getCode().equals(ResponseCode.BAD_REQUEST)) {
            log.info("Bad request diagnosed after attempt  to retrieve Asset Category with ID: '{}'. Message: '{}'", assetCategoryID, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // Reporting failure to locate the requested Asset Category.
        if (wrapper.getCode().equals(ResponseCode.NOT_FOUND)) {
            log.info("Observed fruitless attempt to retrieve Asset Category with ID: '{}'. Message: '{}'", assetCategoryID, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(404, HttpStatus.NOT_FOUND.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

        // Last type of error: SUCCESS indicator but no content.
        if (wrapper.getResponse() == null) {
            log.error("Internal error occurred after attempt  to retrieve Asset Category with ID: '{}'. Message: '{}'", assetCategoryID, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Logging success and returning the retrieved Asset Category.
        log.info("Successfully retrieved Asset Category from persistence layer with ID: '{}'.", assetCategoryID);
        log.info("Successfully retrieved Asset Category from persistence layer with ID: '{}'.", assetCategoryID);
        return ResponseEntity.ok(wrapper.getResponse());
    }

    /******************************************************************************************************************
     * Method that connects to the persistence layer to retrieve an existing Asset Category using its name
     * as unique identifier.
     *
     * @param name A human-readable string that uniquely identifies an existing Asset Category in the database, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @Override
    public ResponseEntity retrieveAssetCategoryByName(String name) {

        // Checking that the mandatory assetCategory identifier has a value.
        if (name == null || name.isBlank()) {
            log.info("Attempt to retrieve Asset Category without specifying name identifier detected. Operation aborted.");
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    ErrorMessages.IDENTIFIER_MISSING.getErrorMessage(),
                    ErrorMessages.IDENTIFIER_MISSING.name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // Querying the persistence layer.
        AssetCategoryResponseWrapper wrapper;
        try {
            wrapper = services.retrieveAssetCategoryByName(name);
        } catch (Exception e) {
            log.error("Internal error occurred after attempt to retrieve Asset Category with Name: '{}'. Message: '{}'", name, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Reporting server errors.
        if (wrapper == null
                || wrapper.getCode().equals(ResponseCode.ERROR)
                || wrapper.getCode().equals(ResponseCode.UNDEFINED)
                || wrapper.getCode().equals(ResponseCode.CONFLICT)) {
            log.error("Internal error occurred after attempt  to retrieve Asset Category with Name: '{}'. Message: '{}'", name,
                    wrapper != null ? wrapper.getMessage() : "Details unknown.");
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Reporting problematic client requests.
        if (wrapper.getCode().equals(ResponseCode.BAD_REQUEST)) {
            log.info("Bad request diagnosed after attempt  to retrieve Asset Category with Name: '{}'. Message: '{}'", name, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // Reporting failure to locate the requested Asset Category.
        if (wrapper.getCode().equals(ResponseCode.NOT_FOUND)) {
            log.info("Observed fruitless attempt to retrieve Asset Category with Name: '{}'. Message: '{}'", name, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(404, HttpStatus.NOT_FOUND.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

        // Last type of error: SUCCESS indicator but no content.
        if (wrapper.getResponse() == null) {
            log.error("Internal error occurred after attempt  to retrieve Asset Category with Name: '{}'. Message: '{}'", name, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Logging success and returning the retrieved Asset Category.
        log.info("Successfully retrieved Asset Category from persistence layer with Name: '{}'.", name);
        return ResponseEntity.ok(wrapper.getResponse());
    }

    /******************************************************************************************************************
     * Method that connects to the persistence layer to retrieve all Asset Categories paginated.
     *
     * @param page The page to retrieve.
     * @param size The intended size of pages.
     * @return A wrapped data transfer object with either information on the retrieved Asset Categories
     * or failure messages.
     *****************************************************************************************************************/
    @Override
    public ResponseEntity retrieveAllAssetCategories(int page, int size) {

        // Querying the persistence layer.
        AssetCategoryResponsesWrapper wrapper;
        try {
            wrapper = services.retrieveAllAssetCategories(page, size);
        } catch (Exception e) {
            log.error("Internal error occurred after attempt to retrieve all Asset Categories.");
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Reporting server errors.
        if (wrapper == null
                || wrapper.getCode().equals(ResponseCode.ERROR)
                || wrapper.getCode().equals(ResponseCode.UNDEFINED)
                || wrapper.getCode().equals(ResponseCode.CONFLICT)) {
            log.error("Internal error occurred after attempt to retrieve all Asset Categories.");
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Reporting failure to locate any Asset Categories.
        if (wrapper.getCode().equals(ResponseCode.NOT_FOUND)) {
            log.info("Observed fruitless attempt to retrieve any Asset Category. Message: '{}'", wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(404, HttpStatus.NOT_FOUND.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

        // Last type of error: SUCCESS indicator but no content.
        if (wrapper.getListOfResponses() == null) {
            log.error("Internal error occurred after attempt  to retrieve any Asset Category. Message: '{}'", wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Logging success and returning the retrieved Asset Category.
        log.info("Successfully retrieved all Asset Category entities from persistence layer.");
        return ResponseEntity.ok(wrapper.getListOfResponses());
    }

    /******************************************************************************************************************
     * Method that connects to the persistence layer to record information on a new Asset Category.
     *
     * @param requestDto A data transfer object with values for the attributes of the Asset Category, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @Override
    public ResponseEntity createAssetCategory(AssetCategoryRequestDto requestDto) {

        // Checking that the mandatory assetCategory requestDto has a value.
        if (requestDto == null) {
            log.info("Attempt to create Asset Category without specifying configurations detected. Operation aborted.");
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    ErrorMessages.MISSING_DATA_INPUT.getErrorMessage(),
                    ErrorMessages.MISSING_DATA_INPUT.name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // Querying the persistence layer.
        AssetCategoryResponseWrapper wrapper;
        try {
            wrapper = services.createAssetCategory(requestDto);
        } catch (Exception e) {
            log.error("Internal error occurred after attempt to create new Asset Category with Name: '{}'. Message: '{}'",
                    requestDto.getName(), e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Reporting server errors.
        if (wrapper == null
                || wrapper.getCode().equals(ResponseCode.ERROR)
                || wrapper.getCode().equals(ResponseCode.NOT_FOUND)
                || wrapper.getCode().equals(ResponseCode.UNDEFINED)) {
            log.error("Internal error occurred after attempt to create new Asset Category with Name: '{}'. Message: '{}'",
                    requestDto.getName(), wrapper != null ? wrapper.getMessage() : "Details unknown.");
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Reporting failure due to Name conflict.
        if (wrapper.getCode().equals(ResponseCode.CONFLICT)) {
            log.info("Observed fruitless attempt to create new Asset Category with Name: '{}'. Message: '{}'",
                    requestDto.getName(), wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(409, HttpStatus.CONFLICT.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
        }

        // Reporting problematic client requests.
        if (wrapper.getCode().equals(ResponseCode.BAD_REQUEST)) {
            log.info("Bad request diagnosed after attempt to create new Asset Category with Name: '{}'. Message: '{}'",
                    requestDto.getName(), wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // Last type of error: SUCCESS indicator but no content.
        if (wrapper.getResponse() == null) {
            log.error("Internal error occurred after attempt to create new Asset Category with Name: '{}'. Message: '{}'",
                    requestDto.getName(), wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Logging success and returning the created Asset Category.
        log.info("Successfully created new Asset Category at persistence layer with ID '{}' and Name: '{}'.",
                wrapper.getResponse().getId(), wrapper.getResponse().getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(wrapper.getResponse());
    }

    /******************************************************************************************************************
     * Method that connects to the persistence layer to update information of an existing Asset Category
     * using its databaseID as unique identifier.
     *
     * @param requestDto A data transfer object with values for the attributes of the Asset Category, not null.
     * @param assetCategoryID A UUID that uniquely identifies an existing Asset Category in the database, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @Override
    public ResponseEntity updateAssetCategory(AssetCategoryRequestDto requestDto, String assetCategoryID) {

        // Checking that the mandatory assetCategory requestDto has a value.
        if (requestDto == null) {
            log.info("Attempt to update Asset Category without specifying configurations detected. Operation aborted.");
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    ErrorMessages.MISSING_DATA_INPUT.getErrorMessage(),
                    ErrorMessages.MISSING_DATA_INPUT.name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // Checking that the mandatory assetCategory identifier has a value.
        if (assetCategoryID == null || assetCategoryID.isBlank()) {
            log.info("Attempt to update Asset Category without specifying a unique UUID detected. Operation aborted.");
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    ErrorMessages.IDENTIFIER_MISSING.getErrorMessage(),
                    ErrorMessages.IDENTIFIER_MISSING.name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // An identifier that can be parsed to a UUID is mandatory.
        Boolean testResult = ValidatorUUID.isValidUUIDFormat(assetCategoryID);
        if (testResult == Boolean.FALSE) {
            log.info("Attempt to update Asset Category with non-UUID identifier detected. Operation aborted.");
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    ErrorMessages.IDENTIFIER_NOT_UUID.getErrorMessage(),
                    ErrorMessages.IDENTIFIER_NOT_UUID.name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // Querying the persistence layer.
        AssetCategoryResponseWrapper wrapper;
        try {
            wrapper = services.updateAssetCategory(requestDto, assetCategoryID);
        } catch (Exception e) {
            log.error("Internal error occurred after attempt to update Asset Category with ID: '{}'. Message: '{}'",
                    assetCategoryID, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Reporting server errors.
        if (wrapper == null
                || wrapper.getCode().equals(ResponseCode.ERROR)
                || wrapper.getCode().equals(ResponseCode.UNDEFINED)) {
            log.error("Internal error occurred after attempt to update Asset Category with ID: '{}'. Message: '{}'",
                    assetCategoryID, wrapper != null ? wrapper.getMessage() : "Details unknown.");
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Reporting failure to locate the requested Asset Category.
        if (wrapper.getCode().equals(ResponseCode.NOT_FOUND)) {
            log.info("Observed fruitless attempt to update Asset Category with ID: '{}'. Message: '{}'", assetCategoryID, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(404, HttpStatus.NOT_FOUND.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

        // Reporting failure due to Name conflict.
        if (wrapper.getCode().equals(ResponseCode.CONFLICT)) {
            log.info("Observed fruitless attempt to update Asset Category with ID: '{}'. Message: '{}'",
                    assetCategoryID, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(409, HttpStatus.CONFLICT.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
        }

        // Reporting problematic client requests.
        if (wrapper.getCode().equals(ResponseCode.BAD_REQUEST)) {
            log.info("Bad request diagnosed after attempt update Asset Category with ID: '{}'. Message: '{}'",
                    assetCategoryID, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // Last type of error: SUCCESS indicator but no content.
        if (wrapper.getResponse() == null) {
            log.error("Internal error occurred after attempt to update Asset Category with ID: '{}'. Message: '{}'",
                    requestDto.getName(), wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Logging success and returning the updated Asset Category.
        log.info("Successfully updated Asset Category at persistence layer with ID '{}' and Name: '{}'.",
                wrapper.getResponse().getId(), wrapper.getResponse().getName());
        return ResponseEntity.status(HttpStatus.OK).body(wrapper.getResponse());
    }

    /******************************************************************************************************************
     * Method that connects to the persistence layer to delete an existing Asset Category using its databaseID
     * as unique identifier.
     *
     * @param assetCategoryID A UUID that uniquely identifies an existing Asset Category in the database, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @Override
    public ResponseEntity deleteAssetCategory(String assetCategoryID) {

        // Checking that the mandatory assetCategory identifier has a value.
        if (assetCategoryID == null || assetCategoryID.isBlank()) {
            log.info("Attempt to delete Asset Category without specifying a unique UUID detected. Operation aborted.");
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    ErrorMessages.IDENTIFIER_MISSING.getErrorMessage(),
                    ErrorMessages.IDENTIFIER_MISSING.name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // An identifier that can be parsed to a UUID is mandatory.
        Boolean testResult = ValidatorUUID.isValidUUIDFormat(assetCategoryID);
        if (testResult == Boolean.FALSE) {
            log.info("Attempt to delete Asset Category with non-UUID identifier detected. Operation aborted.");
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    ErrorMessages.IDENTIFIER_NOT_UUID.getErrorMessage(),
                    ErrorMessages.IDENTIFIER_NOT_UUID.name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // Querying the persistence layer.
        AssetCategoryResponseWrapper wrapper;
        try {
            wrapper = services.deleteAssetCategory(assetCategoryID);
        } catch (Exception e) {
            log.error("Internal error occurred after attempt to delete Asset Category with ID: '{}'. Message: '{}'", assetCategoryID, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.toString(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Reporting server errors.
        if (wrapper == null
                || wrapper.getCode().equals(ResponseCode.ERROR)
                || wrapper.getCode().equals(ResponseCode.UNDEFINED)
                || wrapper.getCode().equals(ResponseCode.CONFLICT)) {
            log.error("Internal error occurred after attempt  to delete Asset Category with ID: '{}'. Message: '{}'",
                    assetCategoryID, wrapper != null ? wrapper.getMessage() : "Details unknown.");
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Reporting problematic client requests.
        if (wrapper.getCode().equals(ResponseCode.BAD_REQUEST)) {
            log.info("Bad request diagnosed after attempt to delete Asset Category with ID: '{}'. Message: '{}'", assetCategoryID, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // Reporting failure to locate the requested Asset Category.
        if (wrapper.getCode().equals(ResponseCode.NOT_FOUND)) {
            log.info("Observed fruitless attempt to delete AssetCategory with ID: '{}'. Message: '{}'", assetCategoryID, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(404, HttpStatus.NOT_FOUND.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

        // Last type of error: SUCCESS indicator but no content.
        if (wrapper.getResponse() == null) {
            log.error("Internal error occurred after attempt  to delete Asset Category with ID: '{}'. Message: '{}'", assetCategoryID, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Logging success and returning the deleted Asset Category.
        log.info("Successfully deleted Asset Category from persistence layer with ID: '{}'.", assetCategoryID);
        return ResponseEntity.ok(wrapper.getResponse());
    }

    /******************************************************************************************************************
     * Method that connects to the persistence layer to delete all existing Asset Categories.
     *
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @Override
    public ResponseEntity deleteAllAssetCategories() {

        // Querying the persistence layer.
        AssetCategoryResponseWrapper wrapper;
        try {
            wrapper = services.deleteAllAssetCategories();
        } catch (Exception e) {
            log.error("Internal error occurred after attempt to delete all Asset Category entities. Message: '{}'", e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.toString(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Reporting server errors.
        if (wrapper == null
                || wrapper.getCode().equals(ResponseCode.ERROR)
                || wrapper.getCode().equals(ResponseCode.UNDEFINED)
                || wrapper.getCode().equals(ResponseCode.CONFLICT)) {
            log.error("Internal error occurred after attempt  to delete all Asset Category entities. Message: '{}'",
                    wrapper != null ? wrapper.getMessage() : "Details unknown.");
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Reporting failure to locate any Asset Category.
        if (wrapper.getCode().equals(ResponseCode.NOT_FOUND)) {
            log.info("Observed fruitless attempt to all Asset Category entities. Message: '{}'", wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(404, HttpStatus.NOT_FOUND.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

        // Logging success and returning the deleted AssetCategory.
        log.info("Successfully deleted all Asset Category entities from persistence layer.");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted all Asset Categories from the persistence layer.");
    }

}
