package eu.datacrop.maize.model_repository.api.services;

import eu.datacrop.maize.model_repository.api.error.ErrorMessage;
import eu.datacrop.maize.model_repository.api.error.ErrorMessages;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.util.ValidatorUUID;
import eu.datacrop.maize.model_repository.commons.wrappers.collection.SystemResponsesWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.SystemResponseWrapper;
import eu.datacrop.maize.model_repository.services.persistence.SystemPersistenceServicesDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


/**********************************************************************************************************************
 * This class implements the services offered by the API layer pertaining to IoT Systems.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
@Slf4j
@Service
public class SystemApiServicesImpl implements SystemApiServices {

    @Autowired
    SystemPersistenceServicesDao services;

    /******************************************************************************************************************
     * Method that connects to the persistence layer to retrieve an existing IoT System using its databaseID
     * as unique identifier.
     *
     * @param systemID A UUID that uniquely identifies an existing System in the database, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @Override
    public ResponseEntity retrieveSystemByDatabaseID(String systemID) {

        // Checking that the mandatory system identifier has a value.
        if (systemID.isBlank()) {
            log.info("Attempt to retrieve System without specifying a unique UUID detected. Operation aborted.");
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    ErrorMessages.IDENTIFIER_MISSING.getErrorMessage(),
                    ErrorMessages.IDENTIFIER_MISSING.name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // An identifier that can be parsed to a UUID is mandatory.
        Boolean testResult = ValidatorUUID.isValidUUIDFormat(systemID);
        if (testResult == Boolean.FALSE) {
            log.info("Attempt to retrieve System with non-UUID identifier detected. Operation aborted.");
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    ErrorMessages.IDENTIFIER_NOT_UUID.getErrorMessage(),
                    ErrorMessages.IDENTIFIER_NOT_UUID.name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // Querying the persistence layer.
        SystemResponseWrapper wrapper;
        try {
            wrapper = services.retrieveSystemByDatabaseID(systemID);
        } catch (Exception e) {
            log.error("Internal error occurred after attempt to retrieve System with ID: '{}'. Message: '{}'", systemID, e.getMessage());
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
            log.error("Internal error occurred after attempt  to retrieve System with ID: '{}'. Message: '{}'",
                    systemID, wrapper != null ? wrapper.getMessage() : "Details unknown.");
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Reporting problematic client requests.
        if (wrapper.getCode().equals(ResponseCode.BAD_REQUEST)) {
            log.info("Bad request diagnosed after attempt  to retrieve System with ID: '{}'. Message: '{}'", systemID, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // Reporting failure to locate the requested System.
        if (wrapper.getCode().equals(ResponseCode.NOT_FOUND)) {
            log.info("Observed fruitless attempt to retrieve System with ID: '{}'. Message: '{}'", systemID, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(404, HttpStatus.NOT_FOUND.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

        // Last type of error: SUCCESS indicator but no content.
        if (wrapper.getResponse() == null) {
            log.error("Internal error occurred after attempt  to retrieve System with ID: '{}'. Message: '{}'", systemID, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Logging success and returning the retrieved System.
        log.info("Successfully retrieved System from persistence layer with ID: '{}'.", systemID);
        return ResponseEntity.ok(wrapper.getResponse());
    }

    /******************************************************************************************************************
     * Method that connects to the persistence layer to retrieve an existing IoT System using its name
     * as unique identifier.
     *
     * @param name A human-readable string that uniquely identifies an existing System in the database, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @Override
    public ResponseEntity retrieveSystemByName(String name) {

        // Checking that the mandatory system identifier has a value.
        if (name.isBlank()) {
            log.info("Attempt to retrieve System without specifying name identifier detected. Operation aborted.");
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    ErrorMessages.IDENTIFIER_MISSING.getErrorMessage(),
                    ErrorMessages.IDENTIFIER_MISSING.name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // Querying the persistence layer.
        SystemResponseWrapper wrapper;
        try {
            wrapper = services.retrieveSystemByName(name);
        } catch (Exception e) {
            log.error("Internal error occurred after attempt to retrieve System with Name: '{}'. Message: '{}'", name, e.getMessage());
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
            log.error("Internal error occurred after attempt  to retrieve System with Name: '{}'. Message: '{}'", name,
                    wrapper != null ? wrapper.getMessage() : "Details unknown.");
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Reporting problematic client requests.
        if (wrapper.getCode().equals(ResponseCode.BAD_REQUEST)) {
            log.info("Bad request diagnosed after attempt  to retrieve System with Name: '{}'. Message: '{}'", name, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // Reporting failure to locate the requested System.
        if (wrapper.getCode().equals(ResponseCode.NOT_FOUND)) {
            log.info("Observed fruitless attempt to retrieve System with Name: '{}'. Message: '{}'", name, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(404, HttpStatus.NOT_FOUND.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

        // Last type of error: SUCCESS indicator but no content.
        if (wrapper.getResponse() == null) {
            log.error("Internal error occurred after attempt  to retrieve System with Name: '{}'. Message: '{}'", name, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Logging success and returning the retrieved System.
        log.info("Successfully retrieved System from persistence layer with Name: '{}'.", name);
        return ResponseEntity.ok(wrapper.getResponse());
    }

    /******************************************************************************************************************
     * Method that connects to the persistence layer to retrieve all Systems paginated.
     *
     * @param page The page to retrieve.
     * @param size The intended size of pages.
     * @return A wrapped data transfer object with either information on the retrieved Systems or failure messages.
     *****************************************************************************************************************/
    @Override
    public ResponseEntity retrieveAllSystems(int page, int size) {

        // Querying the persistence layer.
        SystemResponsesWrapper wrapper;
        try {
            wrapper = services.retrieveAllSystems(page, size);
        } catch (Exception e) {
            log.error("Internal error occurred after attempt to retrieve all Systems.");
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
            log.error("Internal error occurred after attempt to retrieve all Systems.");
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Reporting failure to locate any Systems.
        if (wrapper.getCode().equals(ResponseCode.NOT_FOUND)) {
            log.info("Observed fruitless attempt to retrieve any System. Message: '{}'", wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(404, HttpStatus.NOT_FOUND.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

        // Last type of error: SUCCESS indicator but no content.
        if (wrapper.getListOfResponses() == null) {
            log.error("Internal error occurred after attempt  to retrieve any System. Message: '{}'", wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Logging success and returning the retrieved System.
        log.info("Successfully retrieved all System entities from persistence layer.");
        return ResponseEntity.ok(wrapper.getListOfResponses());
    }

    /******************************************************************************************************************
     * Method that connects to the persistence layer to delete an existing IoT System using its databaseID
     * as unique identifier.
     *
     * @param systemID A UUID that uniquely identifies an existing System in the database, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @Override
    public ResponseEntity deleteSystem(String systemID) {

        // Checking that the mandatory system identifier has a value.
        if (systemID.isBlank()) {
            log.info("Attempt to delete System without specifying a unique UUID detected. Operation aborted.");
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    ErrorMessages.IDENTIFIER_MISSING.getErrorMessage(),
                    ErrorMessages.IDENTIFIER_MISSING.name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // An identifier that can be parsed to a UUID is mandatory.
        Boolean testResult = ValidatorUUID.isValidUUIDFormat(systemID);
        if (testResult == Boolean.FALSE) {
            log.info("Attempt to delete System with non-UUID identifier detected. Operation aborted.");
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    ErrorMessages.IDENTIFIER_NOT_UUID.getErrorMessage(),
                    ErrorMessages.IDENTIFIER_NOT_UUID.name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // Querying the persistence layer.
        SystemResponseWrapper wrapper;
        try {
            wrapper = services.deleteSystem(systemID);
        } catch (Exception e) {
            log.error("Internal error occurred after attempt to delete System with ID: '{}'. Message: '{}'", systemID, e.getMessage());
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
            log.error("Internal error occurred after attempt  to delete System with ID: '{}'. Message: '{}'",
                    systemID, wrapper != null ? wrapper.getMessage() : "Details unknown.");
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Reporting problematic client requests.
        if (wrapper.getCode().equals(ResponseCode.BAD_REQUEST)) {
            log.info("Bad request diagnosed after attempt  to delete System with ID: '{}'. Message: '{}'", systemID, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // Reporting failure to locate the requested System.
        if (wrapper.getCode().equals(ResponseCode.NOT_FOUND)) {
            log.info("Observed fruitless attempt to delete System with ID: '{}'. Message: '{}'", systemID, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(404, HttpStatus.NOT_FOUND.toString(),
                    wrapper.getMessage(),
                    wrapper.getErrorCode().name(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

        // Last type of error: SUCCESS indicator but no content.
        if (wrapper.getResponse() == null) {
            log.error("Internal error occurred after attempt  to delete System with ID: '{}'. Message: '{}'", systemID, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.name(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        // Logging success and returning the deleted System.
        log.info("Successfully delete System from persistence layer with ID: '{}'.", systemID);
        return ResponseEntity.ok(wrapper.getResponse());
    }

}
