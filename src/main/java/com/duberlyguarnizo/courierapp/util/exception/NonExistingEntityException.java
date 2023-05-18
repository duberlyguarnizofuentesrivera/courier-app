package com.duberlyguarnizo.courierapp.util.exception;

public class NonExistingEntityException extends RuntimeException {
    private final String entityName;

    public NonExistingEntityException(String message, String entityName) {
        super(message);
        this.entityName = entityName;
    }

    public String getEntityName() {
        return this.entityName;
    }
}
