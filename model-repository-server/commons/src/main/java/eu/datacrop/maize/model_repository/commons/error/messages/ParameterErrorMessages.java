package eu.datacrop.maize.model_repository.commons.error.messages;

public enum ParameterErrorMessages {

    /******************************************************************************************************************
     * Indicates that a duplicate parameter was about to be persisted.
     *****************************************************************************************************************/
    DUPLICATE_PARAMETER("There is already a Parameter with the same name."),

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
     * Constructor of the ParameterErrorMessages enumeration.
     *****************************************************************************************************************/
    ParameterErrorMessages(String text) {
        this.text = text;
    }

    /******************************************************************************************************************
     * Transforms a ParameterErrorMessages enumeration object to String.
     *
     *  @return A string representation of the Object.
     *****************************************************************************************************************/
    @Override
    public String toString() {
        return text;
    }
}
