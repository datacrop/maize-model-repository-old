package eu.datacrop.maize.model_repository.commons.util;

import java.time.format.DateTimeFormatter;

public class DateFormatter {

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    private DateFormatter() {
        throw new IllegalStateException("Utility classes should not be instantiated.");
    }
}
