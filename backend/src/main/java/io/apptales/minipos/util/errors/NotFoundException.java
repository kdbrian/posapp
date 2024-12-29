package io.apptales.minipos.util.errors;

public class NotFoundException extends RuntimeException {

    private final String itemId;

    public NotFoundException(String itemId) {
        super("Product not found with id : "+ itemId);
        this.itemId = itemId;
    }

}
