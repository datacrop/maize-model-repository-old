package eu.datacrop.maize.model_repository.commons.error.messages;

/**********************************************************************************************************************
 * This enumeration contains Response Codes to be used in Response Wrappers for KvAttributes.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
public enum KvAttributeErrorMessages {

    /******************************************************************************************************************
     * Indicates that a duplicate parameter value was about to be persisted.
     *****************************************************************************************************************/
    DUPLICATE_PARAMETER_VALUE("Parameter Values sharing the same name detected:"),

    /******************************************************************************************************************
     * Indicates that persistence has been aborted due to absence of mandatory fields.
     *****************************************************************************************************************/
    MANDATORY_FIELDS_MISSING("Request contains one or more mandatory fields that are null. Operation aborted."),

    /******************************************************************************************************************
     * Indicates that the back-end server malfunctioned.
     *****************************************************************************************************************/
    INTERNAL_SERVER_ERROR("Internal Server Error.");

    /******************************************************************************************************************
     * The text representing the enumeration values.
     *****************************************************************************************************************/
    private final String text;

    /******************************************************************************************************************
     * Constructor of the KvAttributeErrorMessages enumeration.
     *****************************************************************************************************************/
    KvAttributeErrorMessages(String text) {
        this.text = text;
    }

    /******************************************************************************************************************
     * Transforms a KvAttributeErrorMessages enumeration object to String.
     *
     *  @return A string representation of the Object.
     *****************************************************************************************************************/
    @Override
    public String toString() {
        return text;
    }
}
