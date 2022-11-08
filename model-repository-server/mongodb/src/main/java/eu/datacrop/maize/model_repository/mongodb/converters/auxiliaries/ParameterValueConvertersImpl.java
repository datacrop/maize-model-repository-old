package eu.datacrop.maize.model_repository.mongodb.converters.auxiliaries;

import eu.datacrop.maize.model_repository.commons.dtos.requests.auxiliaries.ParameterValueRequestDto;
import eu.datacrop.maize.model_repository.commons.dtos.responses.auxiliaries.ParameterValueResponseDto;
import eu.datacrop.maize.model_repository.mongodb.model.auxiliaries.ParameterValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**********************************************************************************************************************
 * This auxiliary class performs transformations among MongoDb Parameter Value database entities and data
 * transfer objects.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
@Slf4j
@Service
public class ParameterValueConvertersImpl implements ParameterValueConverters {

    /*****************************************************************************************************************
     * This method transforms a Parameter Value Request Data Transfer Object into its respective MongoDB Entity form.
     * Returns null on erroneous input.
     *
     * @param  dto the data transfer object to transform, not null.
     * @return the result of the transformation.
     *
     * @throws IllegalArgumentException if dto parameter is null.
     ****************************************************************************************************************/
    @Override
    public ParameterValue convertRequestDtoToEntity(ParameterValueRequestDto dto) throws IllegalArgumentException {

        if (dto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method convertRequestDtoToEntity().");
        }

        ParameterValue parameterValue = new ParameterValue(dto.getName(), dto.getValue());

        log.debug("Successfully converted RequestDto to MongoDB entity for Parameter Value.");

        return parameterValue;
    }

    /*****************************************************************************************************************
     * This method transforms a Parameter Value MongoDB Entity into its respective Request Data Transfer Response form.
     * Returns null on erroneous input.
     *
     * @param entity the database entity to transform, not null.
     * @return the result of the transformation.
     *
     * @throws IllegalArgumentException if entity parameter is null.
     ****************************************************************************************************************/
    @Override
    public ParameterValueResponseDto convertEntityToResponseDto(ParameterValue entity) throws IllegalArgumentException {

        if (entity == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method convertEntityToResponseDto().");
        }

        ParameterValueResponseDto parameterValue = new ParameterValueResponseDto(entity.getName(), entity.getValue());

        log.debug("Successfully converted MongoDB entity to ResponseWrapper for Parameter Value.");

        return parameterValue;
    }
}
