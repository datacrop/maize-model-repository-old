package eu.datacrop.maize.model_repository.api.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import eu.datacrop.maize.model_repository.commons.util.DateFormatter;

import java.time.LocalDateTime;

/**********************************************************************************************************************
 * This class is a data transfer object representing an Error Message. Used in HTTP responses that do not
 * correspond to successful responses (codes 200-299).
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
public class ErrorMessage {

    /******************************************************************************************************************
     * The code corresponding to an HTTP response status code.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status">HTTP response status codes</a>
     *****************************************************************************************************************/
    private int httpCode;

    /******************************************************************************************************************
     * A text representation of the HTTP response status code.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status">HTTP response status codes</a>
     *****************************************************************************************************************/
    private String httpText;

    /******************************************************************************************************************
     * A textual description of the error details.
     *****************************************************************************************************************/
    private String message;

    /******************************************************************************************************************
     * A custom key for the error.
     *****************************************************************************************************************/
    private String messageKey;

    /******************************************************************************************************************
     * A URL with more information on the error and how to resolve it. Optional field.
     *****************************************************************************************************************/
    private String href;

    /******************************************************************************************************************
     * The point in time when the error has been reported.
     *****************************************************************************************************************/
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime timestamp;

    /******************************************************************************************************************
     * Constructor for the ErrorMessage class. The timestamp is being set automatically to "now".
     *
     * @param httpCode The code corresponding to an HTTP response status code.
     * @param httpText A text representation of the HTTP response status code.
     * @param message A textual description of the error details.
     * @param messageKey A custom key for the error.
     * @param href A URL with more information on the error and how to resolve it. Optional field.
     *****************************************************************************************************************/
    public ErrorMessage(int httpCode, String httpText, String message, String messageKey, String href) {
        this.httpCode = httpCode;
        this.httpText = httpText;
        this.message = message;
        this.messageKey = messageKey;
        this.href = href;
        this.timestamp = LocalDateTime.now();
    }

    /******************************************************************************************************************
     * "Getter" method for "httpCode" attribute.
     *
     * @return The current value of the object's "httpCode" attribute.
     *****************************************************************************************************************/
    public int getHttpCode() {
        return httpCode;
    }

    /******************************************************************************************************************
     * "Setter" function for "httpCode" attribute.
     *
     * @param httpCode A value to assign to the object's "httpCode" attribute, not null.
     *****************************************************************************************************************/
    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    /******************************************************************************************************************
     * "Getter" method for "httpText" attribute.
     *
     * @return The current value of the object's "httpText" attribute.
     *****************************************************************************************************************/
    public String getHttpText() {
        return httpText;
    }

    /******************************************************************************************************************
     * "Setter" function for "httpText" attribute.
     *
     * @param httpText A value to assign to the object's "httpText" attribute, not null.
     *****************************************************************************************************************/
    public void setHttpText(String httpText) {
        this.httpText = httpText;
    }

    /******************************************************************************************************************
     * "Getter" method for "message" attribute.
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
     * "Getter" method for "messageKey" attribute.
     *
     * @return The current value of the object's "messageKey" attribute.
     *****************************************************************************************************************/
    public String getMessageKey() {
        return messageKey;
    }

    /******************************************************************************************************************
     * "Setter" function for "messageKey" attribute.
     *
     * @param messageKey A value to assign to the object's "messageKey" attribute, not null.
     *****************************************************************************************************************/
    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    /******************************************************************************************************************
     * "Getter" method for "href" attribute.
     *
     * @return The current value of the object's "href" attribute.
     *****************************************************************************************************************/
    public String getHref() {
        return href;
    }

    /******************************************************************************************************************
     * "Setter" function for "href" attribute.
     *
     * @param href A value to assign to the object's "href" attribute, not null.
     *****************************************************************************************************************/
    public void setHref(String href) {
        this.href = href;
    }

    /******************************************************************************************************************
     * "Getter" method for "timestamp" attribute. (Note: there is no corresponding "setter".)
     *
     * @return The current value of the object's "timestamp" attribute.
     *****************************************************************************************************************/
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /******************************************************************************************************************
     * Transforms an ErrorMessage object to String.
     *
     *  @return A string representation of the Object.
     *****************************************************************************************************************/
    @Override
    public String toString() {
        return "{" +
                "httpCode=" + httpCode +
                ", httpText='" + httpText + '\'' +
                ", message='" + message + '\'' +
                ", messageKey='" + messageKey + '\'' +
                ", href='" + href + '\'' +
                ", timestamp=" + ((timestamp != null) ? timestamp.format(DateFormatter.formatter) : "") +
                '}';
    }
}
