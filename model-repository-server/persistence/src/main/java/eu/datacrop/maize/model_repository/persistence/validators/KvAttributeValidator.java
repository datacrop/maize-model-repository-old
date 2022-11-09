package eu.datacrop.maize.model_repository.persistence.validators;

import eu.datacrop.maize.model_repository.commons.dtos.requests.auxiliaries.KvAttributeRequestDto;
import eu.datacrop.maize.model_repository.commons.dtos.requests.auxiliaries.ParameterValueRequestDto;
import eu.datacrop.maize.model_repository.commons.dtos.requests.templates.RequestDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.error.messages.KvAttributeErrorMessages;
import eu.datacrop.maize.model_repository.commons.validators.Validator;
import eu.datacrop.maize.model_repository.commons.wrappers.single.auxiliaries.KvAttributeResponseWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.auxiliaries.ParameterValueResponseWrapper;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import static eu.datacrop.maize.model_repository.commons.error.messages.KvAttributeErrorMessages.MANDATORY_FIELDS_MISSING;

/**********************************************************************************************************************
 * This class implements methods to validate whether an incoming HTTP Request body contains attributes that are
 * valid according to the business rules for a KvAttribute entity.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
public class KvAttributeValidator implements Validator {

    /******************************************************************************************************************
     * This method triggers validation of the data transfer object's attributes.
     *
     * @param requestDto A data transfer object to validate, not null.
     * @return A ResponseWrapper (the user will receive a more elaborate one, here it is used only for
     * internal intra-module communication).
     *
     * throws IllegalArgumentException, if requestDto is null.
     *****************************************************************************************************************/
    @Override
    public KvAttributeResponseWrapper validateAttributes(RequestDto requestDto) throws IllegalArgumentException {

        // Checking input parameters.
        if (requestDto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method KvAttributeValidator.validate().");
        }

        KvAttributeRequestDto kvAttributeRequestDto = (KvAttributeRequestDto) requestDto;

        String message;
        KvAttributeResponseWrapper wrapper;
        try {
            // Checking the request for mandatory fields that were erroneously provided without content.
            Vector<String> fields = getNamesOfFieldsThatAreErroneouslyNull(kvAttributeRequestDto);
            if (!fields.isEmpty()) {
                message = MANDATORY_FIELDS_MISSING.toString().concat(" Field(s): ").concat(fields.toString());
                return new KvAttributeResponseWrapper(ResponseCode.BAD_REQUEST, message, null, MANDATORY_FIELDS_MISSING);
            }

            // Parsing the Parameter Values set for problems.
            wrapper = validateParameterValues(kvAttributeRequestDto);
            if (wrapper != null && !(wrapper.getCode().equals(ResponseCode.SUCCESS))) {
                return wrapper;
            }

        } catch (IllegalArgumentException e) {
            // Reporting internal server error.
            throw new IllegalArgumentException(e.getMessage());
        }

        // Reporting that attribute validation found no issues.
        return new KvAttributeResponseWrapper(ResponseCode.SUCCESS, "Validation success.", null, null);
    }

    /******************************************************************************************************************
     * This method triggers validation of the data transfer object's external relationships.
     *
     * @param requestDto A data transfer object to validate, not null.
     * @return A ResponseWrapper (the user will receive a more elaborate one, here it is used only for
     * internal intra-module communication).
     *
     * throws IllegalArgumentException, if requestDto is null.
     *****************************************************************************************************************/
    @Override
    public KvAttributeResponseWrapper validateRelationships(RequestDto requestDto) throws IllegalArgumentException {

        if (requestDto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method KvAttributeValidator.validate().");
        }

        // Always returns SUCCESS according to business logic Parameter Values do not refer to other entities.
        return new KvAttributeResponseWrapper(ResponseCode.SUCCESS, "Validation success.", null, null);
    }

    /******************************************************************************************************************
     * This method parses a Parameter Value Request Data Transfer Object for fields that should have not been left
     * null according to business logic. Returns empty Vector if no erroneous fields have been located.
     *
     * @param   requestDto The data transfer object to be parsed, not null.
     * @return A collection of fields that have been marked as erroneous.
     *
     * @throws IllegalArgumentException if the method parameter is null.
     *****************************************************************************************************************/
    private Vector<String> getNamesOfFieldsThatAreErroneouslyNull(KvAttributeRequestDto requestDto) throws IllegalArgumentException {

        // Checking input parameters.
        if (requestDto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method KvAttributeValidator.getNamesOfFieldsThatAreErroneouslyNull().");
        }

        Vector<String> fields = new Vector<String>();

        // Checking mandatory fields "Name" and "Description".
        if (requestDto.getName() == null || requestDto.getName().isBlank()) fields.add("name");
        if (requestDto.getParameterValues() == null) fields.add("kvAttributes.parameterValues");

        // Logging and returning result.
        return fields;
    }

    /******************************************************************************************************************
     * This method parses a Parameter Value Request Data Transfer Object for problematic Parameter Values.
     *
     * @param   requestDto The data transfer object to be parsed, not null.
     * @return A ResponseWrapper (the user will receive a more elaborate one, here it is used only for
     * internal intra-module communication).
     *
     * @throws IllegalArgumentException if the method parameter is null.
     *****************************************************************************************************************/
    private KvAttributeResponseWrapper validateParameterValues(KvAttributeRequestDto requestDto) throws IllegalArgumentException {

        // Checking input parameters.
        if (requestDto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method KvAttributeValidator.getNamesOfFieldsThatAreErroneouslyNull().");
        }

        // Validating one by one the Parameter Values...
        ParameterValueResponseWrapper wrapper;
        final Set<String> set = new HashSet<>();
        for (ParameterValueRequestDto parameterValue : requestDto.getParameterValues()) {
            // ...for missing field values.
            wrapper = (ParameterValueResponseWrapper) parameterValue.performValidation();
            if (wrapper != null && !(wrapper.getCode().equals(ResponseCode.SUCCESS))) {
                return new KvAttributeResponseWrapper(wrapper.getCode(), wrapper.getMessage(), null, KvAttributeErrorMessages.MANDATORY_FIELDS_MISSING);
                // The last parameter is a hack (no other types are currently returned. If they are added a modification is needed.
            }

            //...for duplicate value names.
            if (!set.add(parameterValue.getName())) {
                return new KvAttributeResponseWrapper(ResponseCode.BAD_REQUEST, KvAttributeErrorMessages.DUPLICATE_PARAMETER_VALUE.toString(), null, KvAttributeErrorMessages.DUPLICATE_PARAMETER_VALUE);
            }
        }

        // Reporting success of the valudation.
        return new KvAttributeResponseWrapper(ResponseCode.SUCCESS, "Validation success.", null, null);
    }

}


