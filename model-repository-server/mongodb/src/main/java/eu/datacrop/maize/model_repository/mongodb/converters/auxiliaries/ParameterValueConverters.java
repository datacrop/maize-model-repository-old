package eu.datacrop.maize.model_repository.mongodb.converters.auxiliaries;

import eu.datacrop.maize.model_repository.commons.dtos.requests.auxiliaries.ParameterValueRequestDto;
import eu.datacrop.maize.model_repository.commons.dtos.responses.auxiliaries.ParameterValueResponseDto;
import eu.datacrop.maize.model_repository.mongodb.model.auxiliaries.ParameterValue;

/**********************************************************************************************************************
 * This auxiliary interface defines transformations among MongoDb Location database entities and data transfer objects.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
public interface ParameterValueConverters {

    /*****************************************************************************************************************
     * This method transforms a Parameter Value Request Data Transfer Object into its respective MongoDB Entity form.
     * Returns null on erroneous input.
     *
     * @param  dto the data transfer object to transform, not null.
     * @return the result of the transformation.
     *
     * @throws IllegalArgumentException if dto parameter is null.
     ****************************************************************************************************************/
    ParameterValue convertRequestDtoToEntity(ParameterValueRequestDto dto) throws IllegalArgumentException;

    /*****************************************************************************************************************
     * This method transforms a Parameter Value MongoDB Entity into its respective Request Data Transfer Response form.
     * Returns null on erroneous input.
     *
     * @param entity the database entity to transform, not null.
     * @return the result of the transformation.
     *
     * @throws IllegalArgumentException if entity parameter is null.
     ****************************************************************************************************************/
    ParameterValueResponseDto convertEntityToResponseDto(ParameterValue entity) throws IllegalArgumentException;
}
