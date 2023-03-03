package com.nayeem.biswas.core.base.exceptions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.nayeem.biswas.core.base.response.AppResponse;

import java.util.HashMap;
import java.util.Map;
/**
 * @Project core-service
 * @author Md. Nayeemul Islam
 * @Since Feb 07, 2023
 */

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ServiceExceptionHandlers {

    @ExceptionHandler(ServiceExceptionHolder.ResourceNotFoundException.class)
    public AppResponse handleUserNotFoundException(final ServiceExceptionHolder.ResourceNotFoundException ex) {
        log.error(ex.getMessage());
        return AppResponse.build(HttpStatus.NOT_FOUND).message(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServiceExceptionHolder.ResourceNotFoundDuringWriteRequestException.class)
    public AppResponse handleUserNotFoundException(final ServiceExceptionHolder.ResourceNotFoundDuringWriteRequestException ex) {
        log.error(ex.getMessage());
        return AppResponse.build(HttpStatus.BAD_REQUEST).message(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServiceExceptionHolder.ResourceAlreadyExistsException.class)
    public AppResponse handleAlreadyExistsException(final ServiceExceptionHolder.ResourceAlreadyExistsException ex) {
        log.error(ex.getMessage());
        return AppResponse.build(HttpStatus.BAD_REQUEST).message(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServiceExceptionHolder.BadRequestException.class)
    public AppResponse handleCustomizedBadRequestException(final ServiceExceptionHolder.BadRequestException ex) {
        log.error(ex.getMessage());
        return AppResponse.build(HttpStatus.BAD_REQUEST).message(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServiceExceptionHolder.IllegalDateFormatException.class)
    public AppResponse handleIllegalDateFormatException(final ServiceExceptionHolder.IllegalDateFormatException ex) {
        log.error(ex.getMessage());
        return AppResponse.build(HttpStatus.BAD_REQUEST).message(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServiceExceptionHolder.TokenValidationException.class)
    public AppResponse handleTokenValidationException(final ServiceExceptionHolder.TokenValidationException ex) {
        log.error(ex.getMessage());
        return AppResponse.build(HttpStatus.BAD_REQUEST).message(ex.getMessage());
    }


    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public AppResponse handleUnsupportedMediaType(final HttpMediaTypeNotSupportedException ex) {
        log.error(ex.getMessage());
        return AppResponse.build(HttpStatus.UNSUPPORTED_MEDIA_TYPE).message(ex.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public AppResponse handleValidationError(final HttpMessageNotReadableException ex) {
        log.error(ex.getMessage());
        return AppResponse.build(HttpStatus.UNPROCESSABLE_ENTITY).message("Could not read the request body");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public AppResponse handleInvalidRequestParam(final MethodArgumentTypeMismatchException ex) {
        log.error(ex.getMessage());
        return AppResponse.build(HttpStatus.BAD_REQUEST).message("Invalid Request Parameters");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AppResponse handleValidationError(final MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Errors validationErrors = ex.getBindingResult();
        String message = validationErrors.getErrorCount() + " invalid request parameters.";
        log.error(ex.getMessage());
        return AppResponse.build(HttpStatus.BAD_REQUEST).body(errors).message(message);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public AppResponse handleNoHandlerFoundError(final NoHandlerFoundException ex) {
        log.error(ex.getMessage());
        return AppResponse.build(HttpStatus.BAD_REQUEST).message("Invalid URL");
    }

    //added by Hadi to handle 500 ServiceException
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServiceExceptionHolder.ServiceException.class)
    public AppResponse handleInternalServerError(final ServiceExceptionHolder.ServiceException ex) {
        ex.printStackTrace();
        log.error(ex.getMessage());
        return AppResponse.build(HttpStatus.BAD_REQUEST).message(ex.getMessage());
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public AppResponse handleThrowable(final Exception ex) {
        System.out.println("internal server error start");
        ex.printStackTrace();
        log.error(ex.getMessage());
        return AppResponse.build(HttpStatus.BAD_REQUEST).message("Internal server error while processing request." + ex.getClass().getCanonicalName());
    }
}
