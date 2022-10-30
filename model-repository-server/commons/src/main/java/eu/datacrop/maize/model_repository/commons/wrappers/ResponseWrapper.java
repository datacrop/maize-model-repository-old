package eu.datacrop.maize.model_repository.commons.wrappers;

import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;

import java.io.Serial;
import java.io.Serializable;

public class ResponseWrapper implements Serializable {

    @Serial
    private static final long serialVersionUID = 6615190985490046583L;

    private ResponseCode code;

    private String message;

    public ResponseCode getCode() {
        return code;
    }

    public void setCode(ResponseCode code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
