package com.skytouch.management.controller;

import com.skytouch.commonlibrary.model.ResponseStatus;
import com.skytouch.management.exception.MicroserviceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.net.ConnectException;

@ControllerAdvice
@Slf4j
public class ProductsExceptionHandler {
    public static final String MODEL = "responseStatus";
    public static final String ERROR_VIEW = "/error";

    public ResponseStatus createResponseStatus(String exception, String exceptionMessage) {
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setSuccess(false);
        responseStatus.setMessage(exceptionMessage);
        responseStatus.setException(exception);
        return responseStatus;
    }

    @ExceptionHandler(value = Exception.class)
    public ModelAndView handleException(Exception exception) {
        final ModelAndView modelAndView = new ModelAndView();
        ResponseStatus responseStatus = createResponseStatus(exception.getClass().getName(), exception.getMessage());
        modelAndView.addObject(MODEL, responseStatus);
        modelAndView.setViewName(ERROR_VIEW);
        log.error("Unhandled exception", exception);

        return modelAndView;
    }

    @ExceptionHandler(value = ConnectException.class)
    public ModelAndView handleConnectException(ConnectException connectException) {
        final ModelAndView modelAndView = new ModelAndView();
        ResponseStatus responseStatus = createResponseStatus(connectException.getClass().getName(), connectException.getMessage());
        modelAndView.addObject(MODEL, responseStatus);
        modelAndView.setViewName(ERROR_VIEW);
        log.error("ConnectionException", connectException);

        return modelAndView;
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ModelAndView handleRuntimeException(RuntimeException runtimeException) {
        final ModelAndView modelAndView = new ModelAndView();
        ResponseStatus responseStatus = createResponseStatus(runtimeException.getClass().getName(), runtimeException.getMessage());
        modelAndView.addObject(MODEL, responseStatus);
        modelAndView.setViewName(ERROR_VIEW);
        log.error("RuntimeException", runtimeException);

        return modelAndView;
    }

    @ExceptionHandler(value = MicroserviceException.class)
    public ModelAndView handleMicroServiceException(MicroserviceException microserviceException) {
        final ModelAndView modelAndView = new ModelAndView();
        ResponseStatus responseStatus = createResponseStatus(microserviceException.getClass().getName(), microserviceException.getMessage());
        modelAndView.addObject(MODEL, responseStatus);
        modelAndView.setViewName(ERROR_VIEW);
        log.error("MicroserviceException", microserviceException);

        return modelAndView;
    }
}
