package eu.datacrop.maize.model_repository.commons.enums;

public enum ResponseCode {

    SUCCESS("SUCCESS"),
    ERROR("ERROR"),
    NOT_FOUND("NOT_FOUND"),
    CONFLICT("CONFLICT"),
    UNDEFINED("UNDEFINED");

    private final String text;

    ResponseCode(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
