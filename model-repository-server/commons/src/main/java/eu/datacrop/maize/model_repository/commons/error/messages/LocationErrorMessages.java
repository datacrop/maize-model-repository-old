package eu.datacrop.maize.model_repository.commons.error.messages;

/**********************************************************************************************************************
 * This enumeration contains Response Codes to be used in Response Wrappers for Locations.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
public enum LocationErrorMessages {

    /******************************************************************************************************************
     * Indicates that the Location does not adhere to business logic.
     *****************************************************************************************************************/
    INVALID_LOCATION("Either a GeoLocation (pair of coordinates) or a VirtualLocation must be provided."),

    /******************************************************************************************************************
     * Indicates that the back-end server malfunctioned.
     *****************************************************************************************************************/
    INTERNAL_SERVER_ERROR("Internal Server Error.");

    /******************************************************************************************************************
     * The text representing the enumeration values.
     *****************************************************************************************************************/
    private final String text;

    /******************************************************************************************************************
     * Constructor of the SystemErrorMessages enumeration.
     *****************************************************************************************************************/
    LocationErrorMessages(String text) {
        this.text = text;
    }

    /******************************************************************************************************************
     * Transforms a SystemErrorMessages enumeration object to String.
     *
     *  @return A string representation of the Object.
     *****************************************************************************************************************/
    @Override
    public String toString() {
        return text;
    }
}
