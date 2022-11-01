package eu.datacrop.maize.model_repository.api.error;

/**********************************************************************************************************************
 * This enumeration contains identifiers of generic error codes addressed to API clients.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
public enum ErrorMessages {

    /******************************************************************************************************************
     * Indicates that an incoming data transfer object lacks fields that are supposed to be mandatory.
     *****************************************************************************************************************/
    MANDATORY_FIELDS_MISSING("Request contains one or more mandatory fields that are null. Operation aborted."),

    /******************************************************************************************************************
     * Indicates that an incoming message body is of improper syntax.
     *****************************************************************************************************************/
    HTTP_MESSAGE_NOT_READABLE("JSON Parse Error occurred. Operation aborted."),

    /******************************************************************************************************************
     * Indicates that an incoming message body has attributes of improper data type.
     *****************************************************************************************************************/
    ERRONEOUS_PARAMETER_TYPE("A non-acceptable variable data type has been detected. Operation aborted."),

    /******************************************************************************************************************
     * Indicates that the back-end server malfunctioned.
     *****************************************************************************************************************/
    INTERNAL_SERVER_ERROR("Internal Server Error.");


    /******************************************************************************************************************
     * The text representing the enumeration values.
     *****************************************************************************************************************/
    private String errorMessage;

    /******************************************************************************************************************
     * Constructor of the ErrorMessages enumeration.
     *
     * @param errorMessage The text representing the enumeration values.
     *****************************************************************************************************************/
    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /******************************************************************************************************************
     * "Getter" method for "errorMessage" attribute.
     *
     * @return The current value of the object's "errorMessage" attribute.
     *****************************************************************************************************************/
    public String getErrorMessage() {
        return errorMessage;
    }

    /******************************************************************************************************************
     * "Setter" function for "errorMessage" attribute.
     *
     * @param errorMessage A value to assign to the object's "location" attribute, not null.
     *****************************************************************************************************************/
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /******************************************************************************************************************
     * Transforms an ErrorMessages enumeration object to String.
     *
     *  @return A string representation of the Object.
     *****************************************************************************************************************/
    @Override
    public String toString() {
        return "{" +
                "errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
