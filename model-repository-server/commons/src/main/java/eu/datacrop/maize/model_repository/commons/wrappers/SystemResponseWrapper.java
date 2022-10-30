package eu.datacrop.maize.model_repository.commons.wrappers;

import eu.datacrop.maize.model_repository.commons.dtos.responses.SystemResponseDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**********************************************************************************************************************
 * This class wraps the responses travelling from the persistence layer back to the API for a more complete
 * reporting of problems / unsuccessful requests on IoT Systems.
 *
 * @author Angela-Maria Despotopoulou
 * @since version 0.3.0
 *********************************************************************************************************************/
public class SystemResponseWrapper extends ResponseWrapper implements Serializable {

    @Serial
    private static final long serialVersionUID = 3273277217031230024L;
    /******************************************************************************************************************
     * A data transfer object representing a collection of IoT Systems to be wrapped while returned as a response
     * to an HTTP request.
     *****************************************************************************************************************/
    List<SystemResponseDto> listOfResponses;
    /******************************************************************************************************************
     * A data transfer object representing an IoT System to be wrapped while returned as a response to an HTTP request.
     *****************************************************************************************************************/
    private SystemResponseDto response;

    /******************************************************************************************************************
     * Constructor of the SystemResponseWrapper class, both for Builder pattern and instantiation with "new".
     *****************************************************************************************************************/
    public SystemResponseWrapper(ResponseCode code, String message, SystemResponseDto response) {
        this.setCode(code);
        if (code.equals(ResponseCode.SUCCESS)) {
            this.setMessage("Request has been successful.");
        } else {
            this.setMessage(message);
        }
        this.response = response;
        this.listOfResponses = new ArrayList<>();
    }

    /******************************************************************************************************************
     * Empty constructor of the SystemResponseWrapper class.
     *****************************************************************************************************************/
    public SystemResponseWrapper() {
        this.setCode(ResponseCode.UNDEFINED);
        this.setMessage(null);
        this.response = null;
        this.listOfResponses = new ArrayList<>();
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
     * "Getter" function for "listOfResponses" attribute.
     *
     * @return The current value of the object's "listOfResponses" attribute.
     *****************************************************************************************************************/
    public List<SystemResponseDto> getListOfResponses() {
        if (listOfResponses == null) {
            listOfResponses = new ArrayList<>();
        }
        return listOfResponses;
    }

    /******************************************************************************************************************
     * "Setter" function for "listOfResponses" attribute.
     *
     * @param listOfResponses A value to assign to the object's "listOfResponses" attribute.
     *****************************************************************************************************************/
    public void setListOfResponses(List<SystemResponseDto> listOfResponses) {
        if (listOfResponses == null) {
            this.listOfResponses = new ArrayList<>();
        } else {
            this.listOfResponses = listOfResponses;
        }
    }

    /******************************************************************************************************************
     * Transforms a SystemResponseWrapper object to String.
     *
     * @return A string representation of the Object.
     *****************************************************************************************************************/
    @Override
    public String toString() {
        return "{" +
                "response=" + response +
                ", listOfResponses=" + listOfResponses +
                '}';
    }
}
