package eu.datacrop.maize.model_repository.commons.error.messages;

/**********************************************************************************************************************
 * This enumeration contains Response Codes to be used in Response Wrappers.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
public enum SystemErrorMessages {

    NOT_FOUND_ID("Failed to retrieve any System entities from the database with ID: "),

    NOT_FOUND_NAME("Failed to retrieve any System entities from the database with Name: "),

    NOT_FOUND_ALL("Failed to retrieve any System entities from the database."),

    CONFLICT("There is already another System with the same Name and ID: "),

    ERROR_ON_RETRIEVAL_ID("Error detected while attempting to retrieve System with ID: "),

    ERROR_ON_RETRIEVAL_NAME("Error detected while attempting to retrieve System with Name: "),

    ERROR_ON_RETRIEVAL_MANY("Error detected while attempting to retrieve collection of Systems."),

    ERROR_ON_CREATION("Error detected while attempting to create System with Name: "),

    ERROR_ON_UPDATE("Error detected while attempting to update System with ID: ");


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
