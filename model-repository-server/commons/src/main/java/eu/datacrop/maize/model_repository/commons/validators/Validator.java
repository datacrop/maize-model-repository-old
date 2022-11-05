package eu.datacrop.maize.model_repository.commons.validators;

import eu.datacrop.maize.model_repository.commons.dtos.requests.templates.RequestDto;
import eu.datacrop.maize.model_repository.commons.wrappers.ResponseWrapper;

/**********************************************************************************************************************
 * This interface defines methods to validate whether an incoming HTTP Request body contains attributes that are
 * valid according to the business rules. Validators might check for mandatory fields without values, relationships
 * with other entities, invalid field formats etc. Implementations take place on the persistence layer.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
public interface Validator {

    /******************************************************************************************************************
     * This method triggers validation of the data transfer object's attributes.
     *
     * @param requestDto A data transfer object to validate, not null.
     * @return An abstract ResponseWrapper (the user will receive a more elaborate one, here it is used only for
     * internal intra-module communication).
     *
     * throws IllegalArgumentException, if requestDto is null.
     *****************************************************************************************************************/
    ResponseWrapper validateAttributes(RequestDto requestDto) throws IllegalArgumentException;

    /******************************************************************************************************************
     * This method triggers validation of the data transfer object's external relationships.
     *
     * @param requestDto A data transfer object to validate, not null.
     * @return An abstract ResponseWrapper (the user will receive a more elaborate one, here it is used only for
     * internal intra-module communication).
     *
     * throws IllegalArgumentException, if requestDto is null.
     *****************************************************************************************************************/
    ResponseWrapper validateRelationships(RequestDto requestDto) throws IllegalArgumentException;

}
