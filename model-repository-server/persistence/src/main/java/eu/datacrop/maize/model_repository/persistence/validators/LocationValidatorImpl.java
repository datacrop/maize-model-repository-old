package eu.datacrop.maize.model_repository.persistence.validators;

import eu.datacrop.maize.model_repository.commons.dtos.requests.LocationRequestDto;
import eu.datacrop.maize.model_repository.commons.enums.SuccessOrFailure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LocationValidatorImpl implements LocationValidator {

    /******************************************************************************************************************
     * This method asserts that a LocationRequestDto has either a GeoLocation (coordinates pair) or a VirtualLocation.
     * Having none is also acceptable.
     *
     * @param requestDto The data transfer object to be checked.
     * @return The verdict for SUCCESS or FAILURE of the check.
     *
     * @throws IllegalArgumentException if the method parameter is null.
     *****************************************************************************************************************/
    @Override
    public SuccessOrFailure checkGeoVirtualBalance(LocationRequestDto requestDto) throws IllegalArgumentException {

        if (requestDto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method checkGeoVirtualBalance().");
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
