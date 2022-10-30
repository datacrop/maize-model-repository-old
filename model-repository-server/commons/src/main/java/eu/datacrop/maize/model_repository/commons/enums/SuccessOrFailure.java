package eu.datacrop.maize.model_repository.commons.enums;

public enum SuccessOrFailure {

    SUCCESS("SUCCESS"),
    FAILURE("FAILURE");

    private final String text;

    SuccessOrFailure(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
