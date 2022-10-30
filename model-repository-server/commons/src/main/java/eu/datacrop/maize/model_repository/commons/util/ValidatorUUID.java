package eu.datacrop.maize.model_repository.commons.util;

import java.util.UUID;

public class ValidatorUUID {

    private ValidatorUUID() {
        throw new IllegalStateException("Utility classes should not be instantiated.");
    }

    public static Boolean isValidUUIDFormat(String testSubject) {
        try {
            UUID.fromString(testSubject);
            return Boolean.TRUE;
        } catch (IllegalArgumentException exception) {
            return Boolean.FALSE;
        }
    }

}
