package io.apptales.minipos.util.errors;

public class InvalidIdentifierFormatException extends RuntimeException {

    private final String identifier;

    public InvalidIdentifierFormatException(String identifier) {
        super("Invalid Id passed " + identifier);
        this.identifier = identifier;
    }
}
