package eu.datacrop.maize.model_repository.commons.error.exceptions;

/**********************************************************************************************************************
 * This class defines an Exception to denote that a method's input parameter does not adhere as intended to
 * the UUID format.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
public class NonUuidArgumentException extends IllegalArgumentException {

    /*****************************************************************************************************************
     * Constructs a new exception with the specified detail message.
     *
     * @param message The detail message of the Exception.
     *****************************************************************************************************************/
    public NonUuidArgumentException(String message) {
        super(message);
    }
}
