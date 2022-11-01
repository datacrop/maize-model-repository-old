package eu.datacrop.maize.model_repository.persistence.validators;

import eu.datacrop.maize.model_repository.commons.dtos.requests.LocationRequestDto;
import eu.datacrop.maize.model_repository.commons.enums.SuccessOrFailure;

public interface LocationValidator {

    /******************************************************************************************************************
     * This method asserts that a LocationRequestDto has either a GeoLocation (coordinates pair) or a VirtualLocation.
     * Having none is also acceptable.
     *
     * @param requestDto The data transfer object to be checked.
     * @return The verdict for SUCCESS or FAILURE of the check.
     *
     * @throws IllegalArgumentException if the method parameter is null.
     *****************************************************************************************************************/
    SuccessOrFailure checkGeoVirtualBalance(LocationRequestDto requestDto) throws IllegalArgumentException;
}