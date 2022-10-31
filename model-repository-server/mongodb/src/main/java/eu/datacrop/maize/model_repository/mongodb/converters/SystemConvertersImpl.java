package eu.datacrop.maize.model_repository.mongodb.converters;

import eu.datacrop.maize.model_repository.commons.dtos.requests.SystemRequestDto;
import eu.datacrop.maize.model_repository.commons.dtos.responses.LocationResponseDto;
import eu.datacrop.maize.model_repository.commons.dtos.responses.SystemResponseDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.wrappers.PaginationInfo;
import eu.datacrop.maize.model_repository.commons.wrappers.collection.SystemResponsesWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.SystemResponseWrapper;
import eu.datacrop.maize.model_repository.mongodb.converters.auxiliary.LocationConverters;
import eu.datacrop.maize.model_repository.mongodb.model.System;
import eu.datacrop.maize.model_repository.mongodb.model.auxiliary.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**********************************************************************************************************************
 * This auxiliary class performs transformations among MongoDb Location database entities and data transfer objects.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
@Slf4j
@Service
public class SystemConvertersImpl implements SystemConverters {

    @Autowired
    LocationConverters locationConverters;

    /*****************************************************************************************************************
     * This method transforms a System Request Data Transfer Object into its respective MongoDB Entity form.
     * Returns null on erroneous input.
     *
     * @param  dto The data transfer object to transform, not null.
     * @param  databaseID UUID that uniquely identifies a persisted System, receives value only on update requests.
     * @return The result of the transformation.
     *
     * @throws IllegalArgumentException if dto parameter is null.
     ****************************************************************************************************************/
    @Override
    public System convertRequestDtoToEntity(SystemRequestDto dto, String databaseID) throws IllegalArgumentException {

        if (dto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method convertRequestDtoToEntity().");
        }

        Location location = locationConverters.convertRequestDtoToEntity(dto.getLocation());

        System system = System.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .location(location)
                .organization(dto.getOrganization())
                .additionalInformation(dto.getAdditionalInformation())
                .build();

        if (databaseID.isBlank()) // RequestDTO used for first persistence of new entities.
        {
            system.setId(UUID.randomUUID().toString());
        } else // RequestDTO used to update existing entities.
        {
            system.setId(databaseID);
        }

        log.debug("Successfully converted RequestDto to MongoDB entity for System.");

        return system;
    }

    /*****************************************************************************************************************
     * This method transforms a System MongoDB Entity into its respective Request Data Transfer Response form.
     * The result is enclosed in a Wrapper object. Returns null on erroneous input.
     *
     * @param entity  The database entity to transform, not null.
     * @return The result of the transformation.
     *
     * @throws IllegalArgumentException if entity parameter is null.
     ****************************************************************************************************************/
    @Override
    public SystemResponseWrapper convertEntityToResponseWrapper(System entity) throws IllegalArgumentException {

        if (entity == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method convertEntityToResponseWrapper().");
        }

        // Performing transformation of contents.
        LocationResponseDto location = locationConverters.convertEntityToResponseDto(entity.getLocation());

        SystemResponseDto responseDto = SystemResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .location(location)
                .organization(entity.getOrganization())
                .additionalInformation(entity.getAdditionalInformation())
                .creationDate(entity.getCreationDate())
                .latestUpdateDate(entity.getLatestUpdateDate())
                .build();

        // Wrapping the result.
        SystemResponseWrapper wrapper = new SystemResponseWrapper();
        wrapper.setCode(ResponseCode.SUCCESS);
        wrapper.setMessage("Database transaction successfully concluded.");
        wrapper.setResponse(responseDto);

        log.debug("Successfully converted MongoDB entity to ResponseWrapper for System.");

        return wrapper;
    }

    /*****************************************************************************************************************
     * This method transforms a collection of System MongoDB Entities into its respective collection of Request Data
     * Transfer Responses form. The result is enclosed in a Wrapper object. Returns null on erroneous input.
     *
     * @param entitiesList The list of database entities to transform, not null, not empty.
     * @param paginationInfo A structure containing information regarding pagination, not null.
     * @return The result of the transformation.
     *
     * @throws IllegalArgumentException if entitiesList parameter is null or corresponds to an empty list.
     * @throws IllegalArgumentException if paginationInfo parameter is null.
     ****************************************************************************************************************/
    @Override
    public SystemResponsesWrapper convertEntitiesToResponseWrapper(List<System> entitiesList, PaginationInfo paginationInfo) throws IllegalArgumentException {

        if (entitiesList == null || entitiesList.isEmpty() || paginationInfo == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method convertEntitiesToResponseWrapper().");
        }

        List<SystemResponseDto> responseDtoList = new ArrayList<>();

        // Performing transformation of contents.
        for (System entity : entitiesList) {

            LocationResponseDto location = locationConverters.convertEntityToResponseDto(entity.getLocation());

            SystemResponseDto responseDto = SystemResponseDto.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .description(entity.getDescription())
                    .location(location)
                    .organization(entity.getOrganization())
                    .additionalInformation(entity.getAdditionalInformation())
                    .creationDate(entity.getCreationDate())
                    .latestUpdateDate(entity.getLatestUpdateDate())
                    .build();

            responseDtoList.add(responseDto);
        }

        // Wrapping the result.
        SystemResponsesWrapper wrapper = new SystemResponsesWrapper();
        wrapper.setCode(ResponseCode.SUCCESS);
        wrapper.setMessage("Database transaction successfully concluded.");
        wrapper.setListOfResponses(responseDtoList);
        wrapper.setPaginationInfo(paginationInfo);

        log.debug("Successfully converted MongoDB entities list to ResponseWrapper for Systems.");

        return wrapper;
    }

    /*****************************************************************************************************************
     * This method synthesizes a Wrapper object with error messages. To be used when database transactions fail.
     * Returns null on erroneous input. Wrapper for a single entity version.
     *
     * @param code Code indicating why the database transaction has been unsuccessful, not null.
     * @param message Comment accompanying the error report, not null.
     * @return The result of the transformation.
     *
     * @throws IllegalArgumentException if code parameter is null, or equals to SUCCESS or UNDEFINED.
     * @throws IllegalArgumentException if message parameter is null or an empty string.
     ****************************************************************************************************************/
    @Override
    public SystemResponseWrapper synthesizeResponseWrapperForError(ResponseCode code, String message) throws IllegalArgumentException {
        if (code == null || code.equals(ResponseCode.SUCCESS) || code.equals(ResponseCode.UNDEFINED) || message.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method synthesizeResponseWrapperForError().");
        }

        SystemResponseWrapper wrapper = new SystemResponseWrapper();
        wrapper.setCode(code);
        wrapper.setMessage(message);
        wrapper.setResponse(null);

        log.debug("Successfully produced ResponseWrapper for unsuccessful database transaction.");

        return wrapper;
    }

    /*****************************************************************************************************************
     * This method synthesizes a Wrapper object with error messages. To be used when database transactions fail.
     * Returns null on erroneous input. Wrapper for collection version.
     *
     * @param code Code indicating why the database transaction has been unsuccessful, not null.
     * @param message Comment accompanying the error report, not null.
     * @return The result of the transformation.
     *
     * @throws IllegalArgumentException if code parameter is null, or equals to SUCCESS or UNDEFINED.
     * @throws IllegalArgumentException if message parameter is null or an empty string.
     ****************************************************************************************************************/
    @Override
    public SystemResponsesWrapper synthesizeResponsesWrapperForError(ResponseCode code, String message) throws IllegalArgumentException {
        if (code == null || code.equals(ResponseCode.SUCCESS) || code.equals(ResponseCode.UNDEFINED) || message.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method synthesizeResponsesWrapperForError().");
        }

        SystemResponsesWrapper wrapper = new SystemResponsesWrapper();
        wrapper.setCode(code);
        wrapper.setMessage(message);
        wrapper.setListOfResponses(new ArrayList<>());

        log.debug("Successfully produced ResponseWrapper for unsuccessful database transaction.");

        return wrapper;
    }


}
