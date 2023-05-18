package com.duberlyguarnizo.courierapp.util.exception;

public class ExistingEntityException extends RuntimeException {
    private final String entityName;

    public ExistingEntityException(String message, String entityName) {
        super(message);
        this.entityName = entityName;
    }

    public String getEntityName() {
        return this.entityName;
    }
}
