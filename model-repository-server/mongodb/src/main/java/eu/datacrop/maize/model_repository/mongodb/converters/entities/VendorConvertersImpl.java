package eu.datacrop.maize.model_repository.mongodb.converters.entities;

import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.VendorRequestDto;
import eu.datacrop.maize.model_repository.commons.dtos.responses.entities.VendorResponseDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.error.messages.VendorErrorMessages;
import eu.datacrop.maize.model_repository.commons.wrappers.PaginationInfo;
import eu.datacrop.maize.model_repository.commons.wrappers.collection.VendorResponsesWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.entities.VendorResponseWrapper;
import eu.datacrop.maize.model_repository.mongodb.model.entities.Vendor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**********************************************************************************************************************
 * This auxiliary class performs transformations among MongoDb Vendor database entities and data transfer objects.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
@Slf4j
@Service
public class VendorConvertersImpl implements VendorConverters {

    /*****************************************************************************************************************
     * This method transforms a Vendor Request Data Transfer Object into its respective MongoDB Entity form.
     * Returns null on erroneous input.
     *
     * @param  dto The data transfer object to transform, not null.
     * @param  databaseID UUID that uniquely identifies a persisted Vendor, receives value only on update requests.
     * @return The result of the transformation.
     *
     * @throws IllegalArgumentException if dto parameter is null.
     ****************************************************************************************************************/
    @Override
    public Vendor convertRequestDtoToEntity(VendorRequestDto dto, String databaseID) throws IllegalArgumentException {

        if (dto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method convertRequestDtoToEntity().");
        }

        Vendor vendor = Vendor.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();

        if (databaseID.isBlank()) // RequestDTO used for first persistence of new entities.
        {
            vendor.setId(UUID.randomUUID().toString());
            vendor.setCreationDate(LocalDateTime.now());
        } else // RequestDTO used to update existing entities.
        {
            vendor.setId(databaseID);
        }

        log.debug("Successfully converted RequestDto to MongoDB entity for Vendor.");

        return vendor;
    }

    /*****************************************************************************************************************
     * This method transforms a Vendor MongoDB Entity into its respective Request Data Transfer Response form.
     * The result is enclosed in a Wrapper object. Returns null on erroneous input.
     *
     * @param entity  The database entity to transform, not null.
     * @return The result of the transformation.
     *
     * @throws IllegalArgumentException if entity parameter is null.
     ****************************************************************************************************************/
    @Override
    public VendorResponseWrapper convertEntityToResponseWrapper(Vendor entity) throws IllegalArgumentException {

        if (entity == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method convertEntityToResponseWrapper().");
        }

        // Performing transformation of contents.
        VendorResponseDto responseDto = VendorResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .creationDate(entity.getCreationDate())
                .latestUpdateDate(entity.getLatestUpdateDate())
                .build();

        // Wrapping the result.
        VendorResponseWrapper wrapper = new VendorResponseWrapper();
        wrapper.setCode(ResponseCode.SUCCESS);
        wrapper.setMessage("Database transaction successfully concluded.");
        wrapper.setResponse(responseDto);

        log.debug("Successfully converted MongoDB entity to ResponseWrapper for Vendor.");

        return wrapper;
    }

    /*****************************************************************************************************************
     * This method transforms a collection of Vendor MongoDB Entities into its respective collection of Request Data
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
    public VendorResponsesWrapper convertEntitiesToResponseWrapper(List<Vendor> entitiesList, PaginationInfo paginationInfo) throws IllegalArgumentException {

        if (entitiesList == null || entitiesList.isEmpty() || paginationInfo == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method convertEntitiesToResponseWrapper().");
        }

        List<VendorResponseDto> responseDtoList = new ArrayList<>();

        // Performing transformation of contents.
        for (Vendor entity : entitiesList) {

            VendorResponseDto responseDto = VendorResponseDto.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .description(entity.getDescription())
                    .creationDate(entity.getCreationDate())
                    .latestUpdateDate(entity.getLatestUpdateDate())
                    .build();

            responseDtoList.add(responseDto);
        }

        // Wrapping the result.
        VendorResponsesWrapper wrapper = new VendorResponsesWrapper();
        wrapper.setCode(ResponseCode.SUCCESS);
        wrapper.setMessage("Database transaction successfully concluded.");
        wrapper.setListOfResponses(responseDtoList);
        wrapper.setPaginationInfo(paginationInfo);

        log.debug("Successfully converted MongoDB entities list to ResponseWrapper for Vendors.");

        return wrapper;
    }

    /*****************************************************************************************************************
     * This method synthesizes a Wrapper object with error messages. To be used when database transactions fail.
     * Wrapper for a single entity version.
     *
     * @param code Code indicating why the database transaction has been unsuccessful, not null.
     * @param message Comment accompanying the error report, not null.
     * @param errorMessage A code for the particular type of error, not null.
     * @return The result of the transformation.
     *
     * @throws IllegalArgumentException if code parameter is null, or equals to SUCCESS or UNDEFINED.
     * @throws IllegalArgumentException if message parameter is null or an empty string.
     ****************************************************************************************************************/
    @Override
    public VendorResponseWrapper synthesizeResponseWrapperForError(ResponseCode code, String message, VendorErrorMessages errorMessage) throws IllegalArgumentException {
        if (code == null || code.equals(ResponseCode.SUCCESS) || code.equals(ResponseCode.UNDEFINED) || message.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method synthesizeResponseWrapperForError().");
        }

        VendorResponseWrapper wrapper = new VendorResponseWrapper();
        wrapper.setCode(code);
        wrapper.setMessage(message);
        wrapper.setResponse(null);
        wrapper.setErrorCode(errorMessage);

        log.debug("Successfully produced ResponseWrapper for unsuccessful database transaction.");

        return wrapper;
    }

    /*****************************************************************************************************************
     * This method synthesizes a Wrapper object with error messages. To be used when database transactions fail.
     * Wrapper for collection version.
     *
     * @param code Code indicating why the database transaction has been unsuccessful, not null.
     * @param message Comment accompanying the error report, not null.
     * @param errorMessage A code for the particular type of error, not null.
     * @return The result of the transformation.
     *
     * @throws IllegalArgumentException if code parameter is null, or equals to SUCCESS or UNDEFINED.
     * @throws IllegalArgumentException if message parameter is null or an empty string.
     ****************************************************************************************************************/
    @Override
    public VendorResponsesWrapper synthesizeResponsesWrapperForError(ResponseCode code, String message, VendorErrorMessages errorMessage) throws IllegalArgumentException {
        if (code == null || code.equals(ResponseCode.SUCCESS) || code.equals(ResponseCode.UNDEFINED) || message.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method synthesizeResponsesWrapperForError().");
        }

        VendorResponsesWrapper wrapper = new VendorResponsesWrapper();
        wrapper.setCode(code);
        wrapper.setMessage(message);
        wrapper.setListOfResponses(new ArrayList<>());
        wrapper.setErrorCode(errorMessage);

        log.debug("Successfully produced ResponseWrapper for unsuccessful database transaction.");

        return wrapper;
    }
}
