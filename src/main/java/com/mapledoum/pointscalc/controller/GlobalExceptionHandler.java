package com.mapledoum.pointscalc.controller;

import com.mapledoum.pointscalc.exceptions.ErrorResponse;
import com.mapledoum.pointscalc.exceptions.InternalErrorException;
import com.mapledoum.pointscalc.exceptions.InvalidRequestException;
import com.mapledoum.pointscalc.exceptions.NoRecordFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.mapledoum.pointscalc.utils.PointsUtils.NO_RECORD_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(NoRecordFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNoRecordFoundException(NoRecordFoundException ex) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(NO_RECORD_FOUND);
        return errorResponse;
    }

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleInvalidRequestException(InvalidRequestException ex) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(InternalErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleInternalErrorException(InternalErrorException ex) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        return errorResponse;
    }

}
