package eu.datacrop.maize.model_repository.persistence.validators;

import eu.datacrop.maize.model_repository.commons.dtos.requests.LocationRequestDto;
import eu.datacrop.maize.model_repository.commons.dtos.requests.SystemRequestDto;
import eu.datacrop.maize.model_repository.commons.dtos.requests.templates.RequestDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.error.messages.LocationErrorMessages;
import eu.datacrop.maize.model_repository.commons.error.messages.SystemErrorMessages;
import eu.datacrop.maize.model_repository.commons.validators.Validator;
import eu.datacrop.maize.model_repository.commons.wrappers.ResponseWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.SystemResponseWrapper;
import org.springframework.stereotype.Service;

import java.util.Vector;

/**********************************************************************************************************************
 * This class implements methods to validate whether an incoming HTTP Request body contains attributes that are
 * valid according to the business rules for a System entity.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
@Service
public class SystemValidator implements Validator {

    @Override
    public SystemResponseWrapper validateAttributes(RequestDto requestDto) throws IllegalArgumentException {
        // Checking input parameters.
        if (requestDto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method SystemValidator.validate().");
        }

        SystemRequestDto systemRequestDto = (SystemRequestDto) requestDto;

        String message;
        try {
            // Checking the request for mandatory fields that were erroneously provided without content.
            Vector<String> fields = getNamesOfFieldsThatAreErroneouslyNull(systemRequestDto);
            if (!fields.isEmpty()) {
                message = SystemErrorMessages.MANDATORY_FIELDS_MISSING.toString().concat(" Field(s): ").concat(fields.toString());
                return new SystemResponseWrapper(ResponseCode.BAD_REQUEST, message, null, SystemErrorMessages.MANDATORY_FIELDS_MISSING);
            }

            // Checking that the location adheres to business logic.
            if (systemRequestDto.getLocation() != null) {
                ResponseWrapper verdict = validateLocation(systemRequestDto);
                if (verdict.getCode().equals(ResponseCode.BAD_REQUEST)) {
                    message = SystemErrorMessages.INVALID_LOCATION_STRUCTURE.toString();
                    return new SystemResponseWrapper(ResponseCode.BAD_REQUEST, message, null, SystemErrorMessages.INVALID_LOCATION_STRUCTURE);
                }
            }
        } catch (IllegalArgumentException e) {
            // Reporting internal server error.
            throw new IllegalArgumentException(e.getMessage());
        }

        // Reporting that attribute validation found no issues.
        return new SystemResponseWrapper(ResponseCode.SUCCESS, "Validation success.", null, null);
    }

    /******************************************************************************************************************
     * This method triggers validation of the data transfer object's external relationships.
     *
     * @param requestDto A data transfer object to validate, not null.
     * @return An abstract ResponseWrapper (the user will receive a more elaborate one, here it is used only for
     * internal intra-module communication).
     *
     * throws IllegalArgumentException, if requestDto is null.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper validateRelationships(RequestDto requestDto) throws IllegalArgumentException {

        if (requestDto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method SystemValidator.validate().");
        }

        // Always returns SUCCESS according to business logic Locations do not refer to other entities.
        return new SystemResponseWrapper(ResponseCode.SUCCESS, "Validation success.", null, null);
    }

    /******************************************************************************************************************
     * This method parses a System Request Data Transfer Object for fields that should have not been left null
     * according to business logic. Returns empty Vector if no erroneous fields have been located.
     *
     * @param   requestDto The data transfer object to be parsed, not null.
     * @return A collection of fields that have been marked as erroneous.
     *
     * @throws IllegalArgumentException if the method parameter is null.
     *****************************************************************************************************************/
    private Vector<String> getNamesOfFieldsThatAreErroneouslyNull(SystemRequestDto requestDto) throws IllegalArgumentException {

        // Checking input parameters.
        if (requestDto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method SystemValidator.getNamesOfFieldsThatAreErroneouslyNull().");
        }

        Vector<String> fields = new Vector<String>();

        // Checking mandatory fields "Name" and "Description".
        if (requestDto.getName() == null || requestDto.getName().isBlank()) fields.add("name");
        if (requestDto.getDescription() == null || requestDto.getDescription().isBlank()) fields.add("description");

        // Logging and returning result.
        return fields;
    }

    /******************************************************************************************************************
     * This method parses the Location information of a System Request Data Transfer Object to validate its contents.
     * Returns a SuccessOrFailure verdict.
     *
     * @param   requestDto The data transfer object to be parsed, not null.
     * @return The verdict for SUCCESS or FAILURE of the check.
     *
     * @throws IllegalArgumentException if the method parameter is null.
     *****************************************************************************************************************/
    private ResponseWrapper validateLocation(SystemRequestDto requestDto) throws IllegalArgumentException {

        // Checking input parameters.
        if (requestDto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method SystemValidator.validateLocation().");
        }

        // If the SystemRequestDto has no Location there is no problem at all. It is not a mandatory field.
        if (requestDto.getLocation() == null) {
            return new ResponseWrapper(ResponseCode.SUCCESS, "");
        }

        LocationRequestDto locDto = requestDto.getLocation();
        locDto.setValidator(new LocationValidator());

        // Validating Location of System Request DTO.
        ResponseWrapper validationResult;
        try {
            validationResult = requestDto.getLocation().performValidation();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        if (!validationResult.getCode().equals(ResponseCode.SUCCESS)) {
            return new ResponseWrapper(ResponseCode.BAD_REQUEST, LocationErrorMessages.INVALID_LOCATION.toString());
        }

        // Logging and returning result.
        return new ResponseWrapper(ResponseCode.SUCCESS, "Validation success.");
    }
}
