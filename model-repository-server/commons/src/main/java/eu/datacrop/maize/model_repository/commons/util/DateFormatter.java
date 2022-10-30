package eu.datacrop.maize.model_repository.commons.util;

import java.time.format.DateTimeFormatter;

/**********************************************************************************************************************
 * This class offers the functionality of a global DateTime Formatter.
 *
 * @author Angela-Maria Despotopoulou
 * @since version 0.3.0
 *********************************************************************************************************************/
public class DateFormatter {

    /******************************************************************************************************************
     * Definition of the global DateTime Formatter.
     *****************************************************************************************************************/
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    /******************************************************************************************************************
     * Constructor of the DateTimeFormatter class (private to disallow instantiation).
     *****************************************************************************************************************/
    private DateFormatter() {
        throw new IllegalStateException("Utility classes should not be instantiated.");
    }
}
