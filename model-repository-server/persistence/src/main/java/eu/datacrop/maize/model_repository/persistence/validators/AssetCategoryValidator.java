package eu.datacrop.maize.model_repository.persistence.validators;

import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.AssetCategoryRequestDto;
import eu.datacrop.maize.model_repository.commons.dtos.requests.templates.RequestDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.error.messages.AssetCategoryErrorMessages;
import eu.datacrop.maize.model_repository.commons.validators.Validator;
import eu.datacrop.maize.model_repository.commons.wrappers.single.entities.AssetCategoryResponseWrapper;
import org.springframework.stereotype.Service;

import java.util.Vector;

/**********************************************************************************************************************
 * This class implements methods to validate whether an incoming HTTP Request body contains attributes that are
 * valid according to the business rules for an Asset Category entity.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
@Service
public class AssetCategoryValidator implements Validator {

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
    public AssetCategoryResponseWrapper validateAttributes(RequestDto requestDto) throws IllegalArgumentException {

        // Checking input parameters.
        if (requestDto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method AssetCategoryValidator.validate().");
        }

        AssetCategoryRequestDto assetCategoryRequestDto = (AssetCategoryRequestDto) requestDto;

        String message;
        try {
            // Checking the request for mandatory fields that were erroneously provided without content.
            Vector<String> fields = getNamesOfFieldsThatAreErroneouslyNull(assetCategoryRequestDto);
            if (!fields.isEmpty()) {
                message = AssetCategoryErrorMessages.MANDATORY_FIELDS_MISSING.toString().concat(" Field(s): ").concat(fields.toString());
                return new AssetCategoryResponseWrapper(ResponseCode.BAD_REQUEST, message, null, AssetCategoryErrorMessages.MANDATORY_FIELDS_MISSING);
            }
        } catch (IllegalArgumentException e) {
            // Reporting internal server error.
            throw new IllegalArgumentException(e.getMessage());
        }

        // Reporting that attribute validation found no issues.
        return new AssetCategoryResponseWrapper(ResponseCode.SUCCESS, "Validation success.", null, null);
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
    public AssetCategoryResponseWrapper validateRelationships(RequestDto requestDto) throws IllegalArgumentException {

        if (requestDto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method AssetCategoryValidator.validate().");
        }

        // Always returns SUCCESS according to business logic Asset Category do not refer to other entities.
        return new AssetCategoryResponseWrapper(ResponseCode.SUCCESS, "Validation success.", null, null);
    }

    /******************************************************************************************************************
     * This method parses an Asset Category Request Data Transfer Object for fields that should have not been left
     * null according to business logic. Returns empty Vector if no erroneous fields have been located.
     *
     * @param   requestDto The data transfer object to be parsed, not null.
     * @return A collection of fields that have been marked as erroneous.
     *
     * @throws IllegalArgumentException - if the method parameter is null.
     *****************************************************************************************************************/
    private Vector<String> getNamesOfFieldsThatAreErroneouslyNull(AssetCategoryRequestDto requestDto) throws IllegalArgumentException {

        // Checking input parameters.
        if (requestDto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method AssetCategoryValidator.getNamesOfFieldsThatAreErroneouslyNull().");
        }

        Vector<String> fields = new Vector<String>();

        // Checking mandatory fields "Name" and "Description".
        if (requestDto.getName() == null || requestDto.getName().isBlank()) fields.add("name");

        // Logging and returning result.
        return fields;
    }
}
