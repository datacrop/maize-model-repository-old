package eu.datacrop.maize.model_repository.persistence.validators;

import eu.datacrop.maize.model_repository.commons.dtos.requests.auxiliaries.ParameterValueRequestDto;
import eu.datacrop.maize.model_repository.commons.dtos.requests.templates.RequestDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.error.messages.ParameterValueErrorMessages;
import eu.datacrop.maize.model_repository.commons.validators.Validator;
import eu.datacrop.maize.model_repository.commons.wrappers.single.auxiliaries.ParameterValueResponseWrapper;

import java.util.Vector;

/**********************************************************************************************************************
 * This class implements methods to validate whether an incoming HTTP Request body contains attributes that are
 * valid according to the business rules for a Parameter Value entity.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
public class ParameterValueValidator implements Validator {

    /******************************************************************************************************************
     * This method triggers validation of the data transfer object's attributes.
     *
     * @param requestDto A data transfer object to validate, not null.
     * @return A ResponseWrapper (the user will receive a more elaborate one, here it is used only for
     * internal intra-module communication).
     *
     * throws IllegalArgumentException - if the method parameter is null.
     *****************************************************************************************************************/
    @Override
    public ParameterValueResponseWrapper validateAttributes(RequestDto requestDto) throws IllegalArgumentException {

        // Checking input parameters.
        if (requestDto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method ParameterValueValidator.validate().");
        }

        ParameterValueRequestDto parameterValueRequestDto = (ParameterValueRequestDto) requestDto;

        String message;
        try {
            // Checking the request for mandatory fields that were erroneously provided without content.
            Vector<String> fields = getNamesOfFieldsThatAreErroneouslyNull(parameterValueRequestDto);
            if (!fields.isEmpty()) {
                message = ParameterValueErrorMessages.MANDATORY_FIELDS_MISSING.toString().concat(" Field(s): ").concat(fields.toString());
                return new ParameterValueResponseWrapper(ResponseCode.BAD_REQUEST, message, null, ParameterValueErrorMessages.MANDATORY_FIELDS_MISSING);
            }
        } catch (IllegalArgumentException e) {
            // Reporting internal server error.
            throw new IllegalArgumentException(e.getMessage());
        }

        // Reporting that attribute validation found no issues.
        return new ParameterValueResponseWrapper(ResponseCode.SUCCESS, "Validation success.", null, null);
    }

    /******************************************************************************************************************
     * This method triggers validation of the data transfer object's external relationships.
     *
     * @param requestDto A data transfer object to validate, not null.
     * @return A ResponseWrapper (the user will receive a more elaborate one, here it is used only for
     * internal intra-module communication).
     *
     * throws IllegalArgumentException - if the method parameter is null.
     *****************************************************************************************************************/
    @Override
    public ParameterValueResponseWrapper validateRelationships(RequestDto requestDto) throws IllegalArgumentException {

        if (requestDto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method ParameterValueValidator.validate().");
        }

        // Always returns SUCCESS according to business logic Parameter Values do not refer to other entities.
        return new ParameterValueResponseWrapper(ResponseCode.SUCCESS, "Validation success.", null, null);
    }

    /******************************************************************************************************************
     * This method parses a Parameter Value Request Data Transfer Object for fields that should have not been left
     * null according to business logic. Returns empty Vector if no erroneous fields have been located.
     *
     * @param   requestDto The data transfer object to be parsed, not null.
     * @return A collection of fields that have been marked as erroneous.
     *
     * throws IllegalArgumentException - if the method parameter is null.
     *****************************************************************************************************************/
    private Vector<String> getNamesOfFieldsThatAreErroneouslyNull(ParameterValueRequestDto requestDto) throws IllegalArgumentException {

        // Checking input parameters.
        if (requestDto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method ParameterValueValidator.getNamesOfFieldsThatAreErroneouslyNull().");
        }

        Vector<String> fields = new Vector<String>();

        // Checking mandatory fields "Name" and "Description".
        if (requestDto.getName() == null || requestDto.getName().isBlank()) fields.add("name");
        if (requestDto.getValue() == null) fields.add("value");

        // Logging and returning result.
        return fields;
    }
}
