package eu.datacrop.maize.model_repository.commons.wrappers;

import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;


/**********************************************************************************************************************
 * This class wraps the responses travelling from the persistence layer back to the API for a more complete
 * reporting of problems / unsuccessful requests.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/

@Builder
public class ResponseWrapper implements Serializable {

    @Serial
    private static final long serialVersionUID = 6615190985490046583L;

    /******************************************************************************************************************
     * A code indicating whether a request has been successful, or the reasons it failed.
     *****************************************************************************************************************/
    private ResponseCode code;

    /******************************************************************************************************************
     * A comment accompanying responses from the persistence layer indicating things that went good or bad.
     *****************************************************************************************************************/
    private String message;

    /******************************************************************************************************************
     * Constructor of the ResponseWrapper class, both for Builder pattern and instantiation with "new".
     *****************************************************************************************************************/
    public ResponseWrapper(ResponseCode code, String message) {
        this.code = code;
        this.message = message;
    }

    /******************************************************************************************************************
     * Empty constructor of the ResponseWrapper class.
     *****************************************************************************************************************/
    public ResponseWrapper() {
        this.code = ResponseCode.UNDEFINED;
        this.message = "";
    }

    /******************************************************************************************************************
     * "Getter" function for "code" attribute.
     *
     * @return The current value of the object's "code" attribute.
     *****************************************************************************************************************/
    public ResponseCode getCode() {
        return code;
    }

    /******************************************************************************************************************
     * "Setter" function for "code" attribute.
     *
     * @param code A value to assign to the object's "code" attribute, not null.
     *****************************************************************************************************************/
    public void setCode(ResponseCode code) {
        this.code = code;
    }

    /******************************************************************************************************************
     * "Getter" function for "message" attribute.
     *
     * @return The current value of the object's "message" attribute.
     *****************************************************************************************************************/
    public String getMessage() {
        return message;
    }

    /******************************************************************************************************************
     * "Setter" function for "message" attribute.
     *
     * @param message A value to assign to the object's "message" attribute, not null.
     *****************************************************************************************************************/
    public void setMessage(String message) {
        this.message = message;
    }

    /******************************************************************************************************************
     * Transforms a ResponseWrapper object to String.
     *
     * @return A string representation of the Object.
     *****************************************************************************************************************/
    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
