package eu.datacrop.maize.model_repository.api.services;

import eu.datacrop.maize.model_repository.api.error.ErrorMessage;
import eu.datacrop.maize.model_repository.api.error.ErrorMessages;
import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.VendorRequestDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.util.ValidatorUUID;
import eu.datacrop.maize.model_repository.commons.wrappers.collection.VendorResponsesWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.entities.VendorResponseWrapper;
import eu.datacrop.maize.model_repository.services.persistence.VendorPersistenceServicesDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


/**********************************************************************************************************************
 * This class implements the services offered by the API layer pertaining to Vendors.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
@Slf4j
@Service
public class VendorApiServicesImpl implements VendorApiServices {

    @Autowired
    VendorPersistenceServicesDao services;

    /******************************************************************************************************************
     * Method that connects to the persistence layer to retrieve an existing Vendor using its databaseID
     * as unique identifier.
     *
     * @param vendorID A UUID that uniquely identifies an existing Vendor in the database, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @Override
    public ResponseEntity retrieveVendorByDatabaseID(String vendorID) {

        // Checking that the mandatory vendor identifier has a value.
        if (vendorID == null || vendorID.isBlank()) {
            log.info("Attempt to retrieve Vendor without specifying a unique UUID detected. Operation aborted.");
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    ErrorMessages.IDENTIFIER_MISSING.getErrorMessage(),
                    ErrorMessages.IDENTIFIER_MISSING.name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // An identifier that can be parsed to a UUID is mandatory.
        Boolean testResult = ValidatorUUID.isValidUUIDFormat(vendorID);
        if (testResult == Boolean.FALSE) {
            log.info("Attempt to retrieve Vendor with non-UUID identifier detected. Operation aborted.");
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    ErrorMessages.IDENTIFIER_NOT_UUID.getErrorMessage(),
                    ErrorMessages.IDENTIFIER_NOT_UUID.name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // Querying the persistence layer.
        VendorResponseWrapper wrapper;
        try {
            wrapper = services.retrieveVendorByDatabaseID(vendorID);
        } catch (Exception e) {
            log.error("Internal error occurred after attempt to retrieve Vendor with ID: '{}'. Message: '{}'", vendorID, e.getMessage());
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
            log.error("Internal error occurred after attempt  to retrieve Vendor with ID: '{}'. Message: '{}'",
                    vendorID, wrapper != null ? wrapper.getMessage() : "Details unknown.");
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Reporting problematic client requests.
        if (wrapper.getCode().equals(ResponseCode.BAD_REQUEST)) {
            log.info("Bad request diagnosed after attempt  to retrieve Vendor with ID: '{}'. Message: '{}'", vendorID, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // Reporting failure to locate the requested Vendor.
        if (wrapper.getCode().equals(ResponseCode.NOT_FOUND)) {
            log.info("Observed fruitless attempt to retrieve Vendor with ID: '{}'. Message: '{}'", vendorID, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(404, HttpStatus.NOT_FOUND.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

        // Last type of error: SUCCESS indicator but no content.
        if (wrapper.getResponse() == null) {
            log.error("Internal error occurred after attempt  to retrieve Vendor with ID: '{}'. Message: '{}'", vendorID, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Logging success and returning the retrieved Vendor.
        log.info("Successfully retrieved Vendor from persistence layer with ID: '{}'.", vendorID);
        log.info("Successfully retrieved Vendor from persistence layer with ID: '{}'.", vendorID);
        return ResponseEntity.ok(wrapper.getResponse());
    }

    /******************************************************************************************************************
     * Method that connects to the persistence layer to retrieve an existing Vendor using its name
     * as unique identifier.
     *
     * @param name A human-readable string that uniquely identifies an existing Vendor in the database, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @Override
    public ResponseEntity retrieveVendorByName(String name) {

        // Checking that the mandatory vendor identifier has a value.
        if (name == null || name.isBlank()) {
            log.info("Attempt to retrieve Vendor without specifying name identifier detected. Operation aborted.");
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    ErrorMessages.IDENTIFIER_MISSING.getErrorMessage(),
                    ErrorMessages.IDENTIFIER_MISSING.name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // Querying the persistence layer.
        VendorResponseWrapper wrapper;
        try {
            wrapper = services.retrieveVendorByName(name);
        } catch (Exception e) {
            log.error("Internal error occurred after attempt to retrieve Vendor with Name: '{}'. Message: '{}'", name, e.getMessage());
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
            log.error("Internal error occurred after attempt  to retrieve Vendor with Name: '{}'. Message: '{}'", name,
                    wrapper != null ? wrapper.getMessage() : "Details unknown.");
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Reporting problematic client requests.
        if (wrapper.getCode().equals(ResponseCode.BAD_REQUEST)) {
            log.info("Bad request diagnosed after attempt  to retrieve Vendor with Name: '{}'. Message: '{}'", name, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // Reporting failure to locate the requested Vendor.
        if (wrapper.getCode().equals(ResponseCode.NOT_FOUND)) {
            log.info("Observed fruitless attempt to retrieve Vendor with Name: '{}'. Message: '{}'", name, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(404, HttpStatus.NOT_FOUND.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

        // Last type of error: SUCCESS indicator but no content.
        if (wrapper.getResponse() == null) {
            log.error("Internal error occurred after attempt  to retrieve Vendor with Name: '{}'. Message: '{}'", name, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Logging success and returning the retrieved Vendor.
        log.info("Successfully retrieved Vendor from persistence layer with Name: '{}'.", name);
        return ResponseEntity.ok(wrapper.getResponse());
    }

    /******************************************************************************************************************
     * Method that connects to the persistence layer to retrieve all Vendors paginated.
     *
     * @param page The page to retrieve.
     * @param size The intended size of pages.
     * @return A wrapped data transfer object with either information on the retrieved Vendors or failure messages.
     *****************************************************************************************************************/
    @Override
    public ResponseEntity retrieveAllVendors(int page, int size) {

        // Querying the persistence layer.
        VendorResponsesWrapper wrapper;
        try {
            wrapper = services.retrieveAllVendors(page, size);
        } catch (Exception e) {
            log.error("Internal error occurred after attempt to retrieve all Vendors.");
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
            log.error("Internal error occurred after attempt to retrieve all Vendors.");
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Reporting failure to locate any Vendors.
        if (wrapper.getCode().equals(ResponseCode.NOT_FOUND)) {
            log.info("Observed fruitless attempt to retrieve any Vendor. Message: '{}'", wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(404, HttpStatus.NOT_FOUND.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

        // Last type of error: SUCCESS indicator but no content.
        if (wrapper.getListOfResponses() == null) {
            log.error("Internal error occurred after attempt  to retrieve any Vendor. Message: '{}'", wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Logging success and returning the retrieved Vendor.
        log.info("Successfully retrieved all Vendor entities from persistence layer.");
        return ResponseEntity.ok(wrapper.getListOfResponses());
    }

    /******************************************************************************************************************
     * Method that connects to the persistence layer to record information on a new Vendor.
     *
     * @param requestDto A data transfer object with values for the attributes of the Vendor, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @Override
    public ResponseEntity createVendor(VendorRequestDto requestDto) {

        // Checking that the mandatory vendor requestDto has a value.
        if (requestDto == null) {
            log.info("Attempt to create Vendor without specifying configurations detected. Operation aborted.");
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    ErrorMessages.MISSING_DATA_INPUT.getErrorMessage(),
                    ErrorMessages.MISSING_DATA_INPUT.name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // Querying the persistence layer.
        VendorResponseWrapper wrapper;
        try {
            wrapper = services.createVendor(requestDto);
        } catch (Exception e) {
            log.error("Internal error occurred after attempt to create new Vendor with Name: '{}'. Message: '{}'",
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
            log.error("Internal error occurred after attempt to create new Vendor with Name: '{}'. Message: '{}'",
                    requestDto.getName(), wrapper != null ? wrapper.getMessage() : "Details unknown.");
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Reporting failure due to Name conflict.
        if (wrapper.getCode().equals(ResponseCode.CONFLICT)) {
            log.info("Observed fruitless attempt to create new Vendor with Name: '{}'. Message: '{}'",
                    requestDto.getName(), wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(409, HttpStatus.CONFLICT.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
        }

        // Reporting problematic client requests.
        if (wrapper.getCode().equals(ResponseCode.BAD_REQUEST)) {
            log.info("Bad request diagnosed after attempt to create new Vendor with Name: '{}'. Message: '{}'",
                    requestDto.getName(), wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // Last type of error: SUCCESS indicator but no content.
        if (wrapper.getResponse() == null) {
            log.error("Internal error occurred after attempt to create new Vendor with Name: '{}'. Message: '{}'",
                    requestDto.getName(), wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Logging success and returning the created Vendor.
        log.info("Successfully created new Vendor at persistence layer with ID '{}' and Name: '{}'.",
                wrapper.getResponse().getId(), wrapper.getResponse().getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(wrapper.getResponse());
    }

    /******************************************************************************************************************
     * Method that connects to the persistence layer to update information of an existing Vendor using its databaseID
     * as unique identifier.
     *
     * @param requestDto A data transfer object with values for the attributes of the Vendor, not null.
     * @param vendorID A UUID that uniquely identifies an existing Vendor in the database, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @Override
    public ResponseEntity updateVendor(VendorRequestDto requestDto, String vendorID) {

        // Checking that the mandatory vendor requestDto has a value.
        if (requestDto == null) {
            log.info("Attempt to update Vendor without specifying configurations detected. Operation aborted.");
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    ErrorMessages.MISSING_DATA_INPUT.getErrorMessage(),
                    ErrorMessages.MISSING_DATA_INPUT.name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // Checking that the mandatory vendor identifier has a value.
        if (vendorID == null || vendorID.isBlank()) {
            log.info("Attempt to update Vendor without specifying a unique UUID detected. Operation aborted.");
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    ErrorMessages.IDENTIFIER_MISSING.getErrorMessage(),
                    ErrorMessages.IDENTIFIER_MISSING.name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // An identifier that can be parsed to a UUID is mandatory.
        Boolean testResult = ValidatorUUID.isValidUUIDFormat(vendorID);
        if (testResult == Boolean.FALSE) {
            log.info("Attempt to update Vendor with non-UUID identifier detected. Operation aborted.");
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    ErrorMessages.IDENTIFIER_NOT_UUID.getErrorMessage(),
                    ErrorMessages.IDENTIFIER_NOT_UUID.name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // Querying the persistence layer.
        VendorResponseWrapper wrapper;
        try {
            wrapper = services.updateVendor(requestDto, vendorID);
        } catch (Exception e) {
            log.error("Internal error occurred after attempt to update Vendor with ID: '{}'. Message: '{}'",
                    vendorID, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Reporting server errors.
        if (wrapper == null
                || wrapper.getCode().equals(ResponseCode.ERROR)
                || wrapper.getCode().equals(ResponseCode.UNDEFINED)) {
            log.error("Internal error occurred after attempt to update Vendor with ID: '{}'. Message: '{}'",
                    vendorID, wrapper != null ? wrapper.getMessage() : "Details unknown.");
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Reporting failure to locate the requested Vendor.
        if (wrapper.getCode().equals(ResponseCode.NOT_FOUND)) {
            log.info("Observed fruitless attempt to update Vendor with ID: '{}'. Message: '{}'", vendorID, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(404, HttpStatus.NOT_FOUND.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

        // Reporting failure due to Name conflict.
        if (wrapper.getCode().equals(ResponseCode.CONFLICT)) {
            log.info("Observed fruitless attempt to update Vendor with ID: '{}'. Message: '{}'",
                    vendorID, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(409, HttpStatus.CONFLICT.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
        }

        // Reporting problematic client requests.
        if (wrapper.getCode().equals(ResponseCode.BAD_REQUEST)) {
            log.info("Bad request diagnosed after attempt update Vendor with ID: '{}'. Message: '{}'",
                    vendorID, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // Last type of error: SUCCESS indicator but no content.
        if (wrapper.getResponse() == null) {
            log.error("Internal error occurred after attempt to update Vendor with ID: '{}'. Message: '{}'",
                    requestDto.getName(), wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Logging success and returning the updated Vendor.
        log.info("Successfully updated Vendor at persistence layer with ID '{}' and Name: '{}'.",
                wrapper.getResponse().getId(), wrapper.getResponse().getName());
        return ResponseEntity.status(HttpStatus.OK).body(wrapper.getResponse());
    }

    /******************************************************************************************************************
     * Method that connects to the persistence layer to delete an existing Vendor using its databaseID
     * as unique identifier.
     *
     * @param vendorID A UUID that uniquely identifies an existing Vendor in the database, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @Override
    public ResponseEntity deleteVendor(String vendorID) {

        // Checking that the mandatory vendor identifier has a value.
        if (vendorID == null || vendorID.isBlank()) {
            log.info("Attempt to delete Vendor without specifying a unique UUID detected. Operation aborted.");
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    ErrorMessages.IDENTIFIER_MISSING.getErrorMessage(),
                    ErrorMessages.IDENTIFIER_MISSING.name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // An identifier that can be parsed to a UUID is mandatory.
        Boolean testResult = ValidatorUUID.isValidUUIDFormat(vendorID);
        if (testResult == Boolean.FALSE) {
            log.info("Attempt to delete Vendor with non-UUID identifier detected. Operation aborted.");
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    ErrorMessages.IDENTIFIER_NOT_UUID.getErrorMessage(),
                    ErrorMessages.IDENTIFIER_NOT_UUID.name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // Querying the persistence layer.
        VendorResponseWrapper wrapper;
        try {
            wrapper = services.deleteVendor(vendorID);
        } catch (Exception e) {
            log.error("Internal error occurred after attempt to delete Vendor with ID: '{}'. Message: '{}'", vendorID, e.getMessage());
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
            log.error("Internal error occurred after attempt  to delete Vendor with ID: '{}'. Message: '{}'",
                    vendorID, wrapper != null ? wrapper.getMessage() : "Details unknown.");
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Reporting problematic client requests.
        if (wrapper.getCode().equals(ResponseCode.BAD_REQUEST)) {
            log.info("Bad request diagnosed after attempt to delete Vendor with ID: '{}'. Message: '{}'", vendorID, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // Reporting failure to locate the requested Vendor.
        if (wrapper.getCode().equals(ResponseCode.NOT_FOUND)) {
            log.info("Observed fruitless attempt to delete Vendor with ID: '{}'. Message: '{}'", vendorID, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(404, HttpStatus.NOT_FOUND.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

        // Last type of error: SUCCESS indicator but no content.
        if (wrapper.getResponse() == null) {
            log.error("Internal error occurred after attempt  to delete Vendor with ID: '{}'. Message: '{}'", vendorID, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Logging success and returning the deleted Vendor.
        log.info("Successfully deleted Vendor from persistence layer with ID: '{}'.", vendorID);
        return ResponseEntity.ok(wrapper.getResponse());
    }

    /******************************************************************************************************************
     * Method that connects to the persistence layer to delete all existing Vendors.
     *
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @Override
    public ResponseEntity deleteAllVendors() {

        // Querying the persistence layer.
        VendorResponseWrapper wrapper;
        try {
            wrapper = services.deleteAllVendors();
        } catch (Exception e) {
            log.error("Internal error occurred after attempt to delete all Vendor entities. Message: '{}'", e.getMessage());
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
            log.error("Internal error occurred after attempt  to delete all Vendor entities. Message: '{}'",
                    wrapper != null ? wrapper.getMessage() : "Details unknown.");
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Reporting failure to locate any Vendor.
        if (wrapper.getCode().equals(ResponseCode.NOT_FOUND)) {
            log.info("Observed fruitless attempt to all Vendor entities. Message: '{}'", wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(404, HttpStatus.NOT_FOUND.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

        // Logging success and returning the deleted Vendor.
        log.info("Successfully deleted all Vendor entities from persistence layer.");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted all Vendors from the persistence layer.");
    }

}
