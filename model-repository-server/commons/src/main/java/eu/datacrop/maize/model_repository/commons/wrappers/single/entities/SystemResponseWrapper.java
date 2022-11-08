package eu.datacrop.maize.model_repository.commons.wrappers.single.entities;

import eu.datacrop.maize.model_repository.commons.dtos.responses.entities.SystemResponseDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.error.messages.SystemErrorMessages;
import eu.datacrop.maize.model_repository.commons.wrappers.ResponseWrapper;

import java.io.Serial;
import java.io.Serializable;

/**********************************************************************************************************************
 * This class wraps the responses travelling from the persistence layer back to the API for a more complete
 * reporting of problems / unsuccessful requests on IoT Systems. Used for Single Objects (as opposed to a Collection).
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
public class SystemResponseWrapper extends ResponseWrapper implements Serializable {

    @Serial
    private static final long serialVersionUID = 3273277217031230024L;

    /******************************************************************************************************************
     * A data transfer object representing an IoT System to be wrapped while returned as a response to an HTTP request.
     *****************************************************************************************************************/
    private SystemResponseDto response;

    /******************************************************************************************************************
     * An error code in case the wrapper is used to report error.
     *****************************************************************************************************************/
    private SystemErrorMessages errorCode;

    /******************************************************************************************************************
     * Constructor of the SystemResponseWrapper class, both for Builder pattern and instantiation with "new".
     *****************************************************************************************************************/
    public SystemResponseWrapper(ResponseCode code, String message, SystemResponseDto response, SystemErrorMessages errorCode) {
        this.setCode(code);
        if (code.equals(ResponseCode.SUCCESS)) {
            this.setMessage("Request has been successful.");
        } else {
            this.setMessage(message);
        }
        this.response = response;
        this.errorCode = errorCode;
    }

    /******************************************************************************************************************
     * Empty constructor of the SystemResponseWrapper class.
     *****************************************************************************************************************/
    public SystemResponseWrapper() {
        this.setCode(ResponseCode.UNDEFINED);
        this.setMessage(null);
        this.response = null;
        this.errorCode = null;
    }

    /******************************************************************************************************************
     * "Getter" function for "response" attribute.
     *
     * @return The current value of the object's "response" attribute.
     *****************************************************************************************************************/
    public SystemResponseDto getResponse() {
        return response;
    }

    /******************************************************************************************************************
     * "Setter" function for "response" attribute.
     *
     * @param response A value to assign to the object's "response" attribute, not null.
     *****************************************************************************************************************/
    public void setResponse(SystemResponseDto response) {
        this.response = response;
    }

    /******************************************************************************************************************
     * "Getter" function for "errorCode" attribute.
     *
     * @return The current value of the object's "errorCode" attribute.
     *****************************************************************************************************************/
    public SystemErrorMessages getErrorCode() {
        return errorCode;
    }

    /******************************************************************************************************************
     * "Setter" function for "errorCode" attribute.
     *
     * @param errorCode A value to assign to the object's "errorCode" attribute, not null.
     *****************************************************************************************************************/
    public void setErrorCode(SystemErrorMessages errorCode) {
        this.errorCode = errorCode;
    }

    /******************************************************************************************************************
     * Transforms a SystemResponseWrapper object to String.
     *
     * @return A string representation of the Object.
     *****************************************************************************************************************/
    @Override
    public String toString() {
        return "{" +
                "code=" + super.getCode() +
                ", message='" + super.getMessage() + '\'' +
                ", response='" + response + '\'' +
                ", response='" + response + '\'' +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}
