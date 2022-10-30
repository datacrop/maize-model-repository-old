package eu.datacrop.maize.model_repository.commons.util;

import java.util.UUID;

/**********************************************************************************************************************
 * This class offers the functionality of a global tool that checks whether strings adhere to the UUID format.
 *
 * @author Angela-Maria Despotopoulou
 * @since version 0.3.0
 *********************************************************************************************************************/
public class ValidatorUUID {

    /******************************************************************************************************************
     * Constructor of the ValidatorUUID class (private to disallow instantiation).
     *****************************************************************************************************************/
    private ValidatorUUID() {
        throw new IllegalStateException("Utility classes should not be instantiated.");
    }

    /******************************************************************************************************************
     * Definition of a global function that checks whether strings adhere to the UUID format or not. Returns TRUE
     * or FALSE.
     *
     * @param testSubject The string to be checked for adherence to the UUID format, not null.
     * @return The result of the check (TRUE for UUID or FALSE for non-UUID).
     *****************************************************************************************************************/
    public static Boolean isValidUUIDFormat(final String testSubject) {
        try {
            UUID.fromString(testSubject);
            return Boolean.TRUE;
        } catch (IllegalArgumentException exception) {
            return Boolean.FALSE;
        }
    }

}
