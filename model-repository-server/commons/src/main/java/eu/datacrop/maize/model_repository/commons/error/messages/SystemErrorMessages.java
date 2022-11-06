package eu.datacrop.maize.model_repository.commons.error.messages;

/**********************************************************************************************************************
 * This enumeration contains Response Codes to be used in Response Wrappers for IoT Systems.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
public enum SystemErrorMessages {

    /******************************************************************************************************************
     * Indicates that a search by database identifier failed to produce a match.
     *****************************************************************************************************************/
    SYSTEM_NOT_FOUND_ID("Failed to retrieve any System entities from the database with ID: "),

    /******************************************************************************************************************
     * Indicates that a search by human-readable identifier failed to produce a match.
     *****************************************************************************************************************/
    SYSTEM_NOT_FOUND_NAME("Failed to retrieve any System entities from the database with Name: "),

    /******************************************************************************************************************
     * Indicates that the document in the database is empty.
     *****************************************************************************************************************/
    NO_SYSTEMS_FOUND("Failed to retrieve any System entities from the database."),

    /******************************************************************************************************************
     * Indicates that the document in the database is empty.
     *****************************************************************************************************************/
    EXCEEDED_PAGE_LIMIT("Failed to retrieve any System entities because page limit has been exceeded."),

    /******************************************************************************************************************
     * Indicates that persistence has been aborted due to unique identifier conflict.
     *****************************************************************************************************************/
    CONFLICT("There is already another System with the same Name and ID: "),

    /******************************************************************************************************************
     * Indicates that persistence has been aborted due to absence of mandatory fields.
     *****************************************************************************************************************/
    MANDATORY_FIELDS_MISSING("Request contains one or more mandatory fields that are null. Operation aborted."),

    /******************************************************************************************************************
     * Indicates that persistence has been aborted due to a Location structure that does not conform to
     * the business model.
     *****************************************************************************************************************/
    INVALID_LOCATION_STRUCTURE("A System may be provided either with a pair of coordinates or a virtual location."),

    /******************************************************************************************************************
     * Indicates that a search by database identifier produced an error.
     *****************************************************************************************************************/
    ERROR_ON_RETRIEVAL_ID("Error detected while attempting to retrieve System with ID: "),

    /******************************************************************************************************************
     * Indicates that a search by human-readable identifier produced an error.
     *****************************************************************************************************************/
    ERROR_ON_RETRIEVAL_NAME("Error detected while attempting to retrieve System with Name: "),

    /******************************************************************************************************************
     * Indicates that a search for a collection of entities produced an error.
     *****************************************************************************************************************/
    ERROR_ON_RETRIEVAL_MANY("Error detected while attempting to retrieve collection of Systems."),

    /******************************************************************************************************************
     * Indicates that the insertion of a new entity produced an error.
     *****************************************************************************************************************/
    ERROR_ON_CREATION("Error detected while attempting to create System with Name: "),

    /******************************************************************************************************************
     * Indicates that the update of an existing entity produced an error.
     *****************************************************************************************************************/
    ERROR_ON_UPDATE("Error detected while attempting to update System with ID: "),

    /******************************************************************************************************************
     * Indicates that the deletion of an existing entity produced an error.
     *****************************************************************************************************************/
    ERROR_ON_DELETION_ID("Error detected while attempting to delete System with ID: "),

    /******************************************************************************************************************
     * Indicates that the deletion of a collection of entities produced an error.
     *****************************************************************************************************************/
    ERROR_ON_DELETION_MANY("Error detected while attempting to delete collection of Systems."),

    /******************************************************************************************************************
     * Indicates that operation has been aborted due to invalid input parameters.
     *****************************************************************************************************************/
    INVALID_PARAMETERS("Operation aborted due to invalid input parameters."),

    /******************************************************************************************************************
     * Indicates that operation has been aborted due to invalid format of input parameters.
     *****************************************************************************************************************/
    INVALID_PARAMETER_FORMAT("Operation aborted due to invalid parameter format."),

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
    SystemErrorMessages(String text) {
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
