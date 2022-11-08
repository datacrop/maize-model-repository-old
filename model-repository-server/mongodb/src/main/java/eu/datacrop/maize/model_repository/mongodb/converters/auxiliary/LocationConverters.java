package eu.datacrop.maize.model_repository.mongodb.converters.auxiliary;

import eu.datacrop.maize.model_repository.commons.dtos.requests.auxiliaries.LocationRequestDto;
import eu.datacrop.maize.model_repository.commons.dtos.responses.auxiliaries.LocationResponseDto;
import eu.datacrop.maize.model_repository.mongodb.model.auxiliaries.Location;

/**********************************************************************************************************************
 * This auxiliary interface defines transformations among MongoDb Location database entities and data transfer objects.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
public interface LocationConverters {

    /*****************************************************************************************************************
     * This method transforms a Location Request Data Transfer Object into its respective MongoDB Entity form.
     * Returns null on erroneous input.
     *
     * @param  dto the data transfer object to transform, not null.
     * @return the result of the transformation.
     *
     * @throws IllegalArgumentException if dto parameter is null.
     ****************************************************************************************************************/
    Location convertRequestDtoToEntity(LocationRequestDto dto) throws IllegalArgumentException;

    /*****************************************************************************************************************
     * This method transforms a Location MongoDB Entity into its respective Request Data Transfer Response form.
     * Returns null on erroneous input.
     *
     * @param entity the database entity to transform, not null.
     * @return the result of the transformation.
     *
     * @throws IllegalArgumentException if entity parameter is null.
     ****************************************************************************************************************/
    LocationResponseDto convertEntityToResponseDto(Location entity) throws IllegalArgumentException;
}
