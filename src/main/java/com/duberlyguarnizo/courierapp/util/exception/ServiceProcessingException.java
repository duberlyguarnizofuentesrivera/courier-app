package com.duberlyguarnizo.courierapp.util.exception;

public class ServiceProcessingException extends RuntimeException{
    private final String service;

    public ServiceProcessingException(String message, String service) {
        super(message);
        this.service = service;
    }
    public String getServiceName() {
        return this.service;
    }
}
