package com.duberlyguarnizo.courierapp.util;

import com.duberlyguarnizo.courierapp.util.exception.ExistingEntityException;
import com.duberlyguarnizo.courierapp.util.exception.InvalidFieldValueException;
import com.duberlyguarnizo.courierapp.util.exception.NonExistingEntityException;
import com.duberlyguarnizo.courierapp.util.exception.ServiceProcessingException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandlerAdvisor {
    @ExceptionHandler(InvalidFieldValueException.class)
    public @NotNull ProblemDetail handleInvalidFieldValueException(@NotNull InvalidFieldValueException e) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setTitle("Datos no válidos");
        detail.setDetail("Campo: " +e.getFieldName() + ". Error: " + e.getMessage());
        return detail;
    }

    @ExceptionHandler(ServiceProcessingException.class)
    public @NotNull ProblemDetail handleServiceProcessingException(@NotNull ServiceProcessingException e) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        detail.setTitle("Error en procesamiento");
        detail.setDetail("Servicio: " +e.getServiceName() + ". Error: " + e.getMessage());
        return detail;
    }

    @ExceptionHandler(ExistingEntityException.class)
    public @NotNull ProblemDetail handleEntityExistsException(@NotNull ExistingEntityException e) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setTitle("Error en servicio");
        detail.setDetail("Entidad: " +e.getEntityName() + ". Error: " + e.getMessage());
        return detail;
    }

    @ExceptionHandler(NonExistingEntityException.class)
    public @NotNull ProblemDetail handleEntityDoesNotExistsException(@NotNull NonExistingEntityException e) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        detail.setTitle("No existe el registro");
        detail.setDetail("Entidad: " +e.getEntityName() + ". Error: " + e.getMessage());
        return detail;
    }
}
