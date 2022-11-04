package eu.datacrop.maize.model_repository.api.services;

import eu.datacrop.maize.model_repository.api.error.ErrorMessage;
import eu.datacrop.maize.model_repository.api.error.ErrorMessages;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.util.ValidatorUUID;
import eu.datacrop.maize.model_repository.commons.wrappers.single.SystemResponseWrapper;
import eu.datacrop.maize.model_repository.persistence.daos.SystemPersistenceLayerDaos;
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
    SystemPersistenceLayerDaos persistenceLayer;

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
            log.info("Attempt to retrieve System without specifying systemID detected. Operation aborted.");
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
            wrapper = persistenceLayer.retrieveSystemByDatabaseID(systemID);
        } catch (Exception e) {
            log.error("Internal error occurred after attempt to retrieve System with ID: '{}'. Message: '{}'", systemID, e.getMessage());
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
            log.error("Internal error occurred after attempt  to retrieve System with ID: '{}'. Message: '{}'", systemID, wrapper.getMessage());
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
                    wrapper.getCode().toString(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // Reporting failure to locate the requested System.
        if (wrapper.getCode().equals(ResponseCode.NOT_FOUND)) {
            log.info("Observed fruitless attempt to retrieve System with ID: '{}'. Message: '{}'", systemID, wrapper.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(404, HttpStatus.NOT_FOUND.toString(),
                    wrapper.getMessage(),
                    wrapper.getCode().toString(), null);
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
}
