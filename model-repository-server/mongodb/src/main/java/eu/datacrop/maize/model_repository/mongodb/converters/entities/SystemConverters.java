package eu.datacrop.maize.model_repository.mongodb.converters.entities;

import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.SystemRequestDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.error.messages.SystemErrorMessages;
import eu.datacrop.maize.model_repository.commons.wrappers.PaginationInfo;
import eu.datacrop.maize.model_repository.commons.wrappers.collection.SystemResponsesWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.entities.SystemResponseWrapper;
import eu.datacrop.maize.model_repository.mongodb.model.entities.System;

import java.util.List;

/**********************************************************************************************************************
 * This auxiliary interface defines transformations among MongoDb System database entities and data transfer objects.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
public interface SystemConverters {

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
    System convertRequestDtoToEntity(SystemRequestDto dto, String databaseID) throws IllegalArgumentException;

    /*****************************************************************************************************************
     * This method transforms a System MongoDB Entity into its respective Request Data Transfer Response form.
     * The result is enclosed in a Wrapper object. Returns null on erroneous input.
     *
     * @param entity  The database entity to transform, not null.
     * @return The result of the transformation.
     *
     * @throws IllegalArgumentException if entity parameter is null.
     ****************************************************************************************************************/
    SystemResponseWrapper convertEntityToResponseWrapper(System entity) throws IllegalArgumentException;

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
    SystemResponsesWrapper convertEntitiesToResponseWrapper(List<System> entitiesList, PaginationInfo paginationInfo) throws IllegalArgumentException;

    /*****************************************************************************************************************
     * This method synthesizes a Wrapper object with error messages. To be used when database transactions fail.
     * Returns null on erroneous input. Wrapper for a single entity version.
     *
     * @param code Code indicating why the database transaction has been unsuccessful, not null.
     * @param message Comment accompanying the error report, not null.
     * @param errorMessage A code for the particular type of error, not null.
     * @return The result of the transformation.
     *
     * @throws IllegalArgumentException if code parameter is null, or equals to SUCCESS or UNDEFINED.
     * @throws IllegalArgumentException if message parameter is null or an empty string.
     ****************************************************************************************************************/
    SystemResponseWrapper synthesizeResponseWrapperForError(ResponseCode code, String message, SystemErrorMessages errorMessage) throws IllegalArgumentException;

    /*****************************************************************************************************************
     * This method synthesizes a Wrapper object with error messages. To be used when database transactions fail.
     * Returns null on erroneous input. Wrapper for collection version.
     *
     * @param code Code indicating why the database transaction has been unsuccessful, not null.
     * @param message Comment accompanying the error report, not null.
     * @param errorMessage A code for the particular type of error, not null.
     * @return The result of the transformation.
     *
     * @throws IllegalArgumentException if code parameter is null, or equals to SUCCESS or UNDEFINED.
     * @throws IllegalArgumentException if message parameter is null or an empty string.
     ****************************************************************************************************************/
    SystemResponsesWrapper synthesizeResponsesWrapperForError(ResponseCode code, String message, SystemErrorMessages errorMessage) throws IllegalArgumentException;
}
