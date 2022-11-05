package eu.datacrop.maize.model_repository.persistence.validators;

import eu.datacrop.maize.model_repository.commons.dtos.requests.LocationRequestDto;
import eu.datacrop.maize.model_repository.commons.dtos.requests.templates.RequestDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.enums.SuccessOrFailure;
import eu.datacrop.maize.model_repository.commons.error.messages.LocationErrorMessages;
import eu.datacrop.maize.model_repository.commons.validators.Validator;
import eu.datacrop.maize.model_repository.commons.wrappers.ResponseWrapper;
import org.springframework.stereotype.Service;

/**********************************************************************************************************************
 * This class implements methods to validate whether an incoming HTTP Request body contains attributes that are
 * valid according to the business rules for a Location entity.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
@Service
public class LocationValidator implements Validator {

    /******************************************************************************************************************
     * This method triggers validation of the data transfer object's attributes.
     *
     * @param requestDto A data transfer object to validate, not null.
     * @return An abstract ResponseWrapper (the user will receive a more elaborate one, here it is used only for
     * internal intra-module communication).
     *
     * throws IllegalArgumentException, if requestDto is null.
     *****************************************************************************************************************/
    @Override
    public ResponseWrapper validateAttributes(RequestDto requestDto) throws IllegalArgumentException {
        // Checking input parameters.
        if (requestDto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method LocationValidator.validate().");
        }

        LocationRequestDto locationRequestDto = (LocationRequestDto) requestDto;

        // Checking that the Location is either a GeoLocation (coordinates pair) or a VirtualLocation.
        // Having none is also acceptable.
        SuccessOrFailure verdict;
        try {
            verdict = checkGeoVirtualBalance(locationRequestDto);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        ResponseWrapper wrapper;
        if (verdict.equals(SuccessOrFailure.SUCCESS)) {
            // Success.
            wrapper = new ResponseWrapper(ResponseCode.SUCCESS, "Validation success.");
        } else {
            // Failure.
            wrapper = new ResponseWrapper(ResponseCode.BAD_REQUEST, LocationErrorMessages.INVALID_LOCATION.toString());
        }

        // Returning attribute validation result.
        return wrapper;
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
    public ResponseWrapper validateRelationships(RequestDto requestDto) throws IllegalArgumentException {

        if (requestDto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method LocationValidator.validate().");
        }

        // Always returns null according to business logic Locations do not refer to other entities.
        return null;
    }

    /******************************************************************************************************************
     * This method asserts that a LocationRequestDto has either a GeoLocation (coordinates pair) or a VirtualLocation.
     * Having none is also acceptable.
     *
     * @param requestDto The data transfer object to be checked.
     * @return The verdict for SUCCESS or FAILURE of the check.
     *
     * @throws IllegalArgumentException if the method parameter is null.
     *****************************************************************************************************************/
    private SuccessOrFailure checkGeoVirtualBalance(LocationRequestDto requestDto) throws IllegalArgumentException {

        // Checking input parameters.
        if (requestDto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method LocationValidator.checkGeoVirtualBalance().");
        }

        double latitude = requestDto.getLatitude();
        double longitude = requestDto.getLongitude();
        String virtualLocation = requestDto.getVirtualLocation();

        boolean latitudeExists = (latitude != 0);
        boolean longitudeExists = (longitude != 0);
        boolean virtualExists = (!virtualLocation.isBlank());

        // The Location object must have either a GeoLocation (coordinates pair) or a VirtualLocation or nothing at all.
        if (latitudeExists && longitudeExists && !virtualExists) return SuccessOrFailure.SUCCESS;
        else if (!latitudeExists && !longitudeExists && virtualExists) return SuccessOrFailure.SUCCESS;
        else if (!latitudeExists && !longitudeExists && !virtualExists) return SuccessOrFailure.SUCCESS;
        else return SuccessOrFailure.FAILURE;
    }


}