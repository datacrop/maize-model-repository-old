package eu.datacrop.maize.model_repository.mongodb.converters.auxiliaries;

import eu.datacrop.maize.model_repository.commons.dtos.requests.auxiliaries.ParameterRequestDto;
import eu.datacrop.maize.model_repository.commons.dtos.responses.auxiliaries.ParameterResponseDto;
import eu.datacrop.maize.model_repository.mongodb.model.auxiliaries.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**********************************************************************************************************************
 * This auxiliary class performs transformations among MongoDb Parameter database entities and data transfer objects.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
@Slf4j
@Service
public class ParameterConvertersImpl implements ParameterConverters {

    /*****************************************************************************************************************
     * This method transforms a Parameter Request Data Transfer Object into its respective MongoDB Entity form.
     * Returns null on erroneous input.
     *
     * @param  dto the data transfer object to transform, not null.
     * @return the result of the transformation.
     *
     * @throws IllegalArgumentException if dto parameter is null.
     ****************************************************************************************************************/
    @Override
    public Parameter convertRequestDtoToEntity(ParameterRequestDto dto) throws IllegalArgumentException {

        if (dto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method convertRequestDtoToEntity().");
        }

        Parameter parameter = new Parameter(dto.getName(), dto.getDescription(), dto.getType(), dto.getDefaultValue());

        log.debug("Successfully converted RequestDto to MongoDB entity for Parameter.");

        return parameter;
    }

    /*****************************************************************************************************************
     * This method transforms a Parameter MongoDB Entity into its respective Request Data Transfer Response form.
     * Returns null on erroneous input.
     *
     * @param entity the database entity to transform, not null.
     * @return the result of the transformation.
     *
     * @throws IllegalArgumentException if entity parameter is null.
     ****************************************************************************************************************/
    @Override
    public ParameterResponseDto convertEntityToResponseDto(Parameter entity) throws IllegalArgumentException {

        if (entity == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method convertEntityToResponseDto().");
        }

        ParameterResponseDto parameter = new ParameterResponseDto(entity.getName(), entity.getDescription(),
                entity.getType(), entity.getDefaultValue());

        log.debug("Successfully converted MongoDB entity to ResponseWrapper for Parameter.");

        return parameter;
    }
}
