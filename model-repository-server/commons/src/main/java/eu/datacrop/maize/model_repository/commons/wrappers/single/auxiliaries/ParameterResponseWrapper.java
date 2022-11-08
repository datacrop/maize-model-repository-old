package eu.datacrop.maize.model_repository.commons.wrappers.single.auxiliaries;

import eu.datacrop.maize.model_repository.commons.dtos.responses.auxiliaries.ParameterResponseDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.error.messages.ParameterErrorMessages;
import eu.datacrop.maize.model_repository.commons.wrappers.ResponseWrapper;

import java.io.Serial;
import java.io.Serializable;

public class ParameterResponseWrapper extends ResponseWrapper implements Serializable {

    @Serial
    private static final long serialVersionUID = 3704011340732383032L;

    /******************************************************************************************************************
     * A data transfer object representing a Parameter to be wrapped while returned as a response to an HTTP request.
     *****************************************************************************************************************/
    private ParameterResponseDto response;

    /******************************************************************************************************************
     * An error code in case the wrapper is used to report error.
     *****************************************************************************************************************/
    private ParameterErrorMessages errorCode;

    /******************************************************************************************************************
     * Constructor of the ParameterResponseWrapper class, both for Builder pattern and instantiation with "new".
     *****************************************************************************************************************/
    public ParameterResponseWrapper(ResponseCode code, String message, ParameterResponseDto response, ParameterErrorMessages errorCode) {
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
     * Empty constructor of the ParameterResponseWrapper class.
     *****************************************************************************************************************/
    public ParameterResponseWrapper() {
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
    public ParameterResponseDto getResponse() {
        return response;
    }

    /******************************************************************************************************************
     * "Setter" function for "response" attribute.
     *
     * @param response A value to assign to the object's "response" attribute, not null.
     *****************************************************************************************************************/
    public void setResponse(ParameterResponseDto response) {
        this.response = response;
    }

    /******************************************************************************************************************
     * "Getter" function for "errorCode" attribute.
     *
     * @return The current value of the object's "errorCode" attribute.
     *****************************************************************************************************************/
    public ParameterErrorMessages getErrorCode() {
        return errorCode;
    }

    /******************************************************************************************************************
     * "Setter" function for "errorCode" attribute.
     *
     * @param errorCode A value to assign to the object's "errorCode" attribute, not null.
     *****************************************************************************************************************/
    public void setErrorCode(ParameterErrorMessages errorCode) {
        this.errorCode = errorCode;
    }

    /******************************************************************************************************************
     * Transforms a ParameterResponseWrapper object to String.
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
