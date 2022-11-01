package eu.datacrop.maize.model_repository.mongodb.converters.auxiliary;

import eu.datacrop.maize.model_repository.commons.dtos.requests.LocationRequestDto;
import eu.datacrop.maize.model_repository.commons.dtos.responses.LocationResponseDto;
import eu.datacrop.maize.model_repository.mongodb.model.auxiliary.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**********************************************************************************************************************
 * This auxiliary class performs transformations among MongoDb Location database entities and data transfer objects.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
@Slf4j
@Service
public class LocationConvertersImpl implements LocationConverters {

    /*****************************************************************************************************************
     * This method transforms a Location Request Data Transfer Object into its respective MongoDB Entity form.
     * Returns null on erroneous input.
     *
     * @param  dto the data transfer object to transform, not null.
     * @return the result of the transformation.
     *
     * @throws IllegalArgumentException if dto parameter is null.
     ****************************************************************************************************************/
    @Override
    public Location convertRequestDtoToEntity(LocationRequestDto dto) throws IllegalArgumentException {

        if (dto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method convertRequestDtoToEntity().");
        }

        Location location = new Location(dto.getLatitude(), dto.getLongitude(), dto.getVirtualLocation());

        log.debug("Successfully converted RequestDto to MongoDB entity for Location.");

        return location;
    }

    /*****************************************************************************************************************
     * This method transforms a Location MongoDB Entity into its respective Request Data Transfer Response form.
     * Returns null on erroneous input.
     *
     * @param entity the database entity to transform, not null.
     * @return the result of the transformation.
     *
     * @throws IllegalArgumentException if entity parameter is null.
     ****************************************************************************************************************/
    @Override
    public LocationResponseDto convertEntityToResponseDto(Location entity) throws IllegalArgumentException {

        if (entity == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method convertEntityToResponseDto().");
        }

        LocationResponseDto location = new LocationResponseDto(entity.getLatitude(), entity.getLongitude(),
                entity.getVirtualLocation());

        log.debug("Successfully converted MongoDB entity to ResponseWrapper for Location.");

        return location;
    }
}
