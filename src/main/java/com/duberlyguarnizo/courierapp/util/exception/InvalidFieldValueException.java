package com.duberlyguarnizo.courierapp.util.exception;

public class InvalidFieldValueException extends RuntimeException {
    private final String fieldName;

    public InvalidFieldValueException(String message, String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return this.fieldName;
    }
}
