package eu.datacrop.maize.model_repository.mongodb.converters.auxiliaries;

import eu.datacrop.maize.model_repository.commons.dtos.requests.auxiliaries.ParameterRequestDto;
import eu.datacrop.maize.model_repository.commons.dtos.responses.auxiliaries.ParameterResponseDto;
import eu.datacrop.maize.model_repository.mongodb.model.auxiliaries.Parameter;

/**********************************************************************************************************************
 * This auxiliary interface defines transformations among MongoDb Location database entities and data transfer objects.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
public interface ParameterConverters {

    /*****************************************************************************************************************
     * This method transforms a Parameter Request Data Transfer Object into its respective MongoDB Entity form.
     * Returns null on erroneous input.
     *
     * @param  dto the data transfer object to transform, not null.
     * @return the result of the transformation.
     *
     * @throws IllegalArgumentException if dto parameter is null.
     ****************************************************************************************************************/
    Parameter convertRequestDtoToEntity(ParameterRequestDto dto) throws IllegalArgumentException;

    /*****************************************************************************************************************
     * This method transforms a Parameter MongoDB Entity into its respective Request Data Transfer Response form.
     * Returns null on erroneous input.
     *
     * @param entity the database entity to transform, not null.
     * @return the result of the transformation.
     *
     * @throws IllegalArgumentException if entity parameter is null.
     ****************************************************************************************************************/
    ParameterResponseDto convertEntityToResponseDto(Parameter entity) throws IllegalArgumentException;
}
