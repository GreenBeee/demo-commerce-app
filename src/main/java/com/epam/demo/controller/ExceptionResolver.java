package com.epam.demo.controller;

import com.epam.demo.exception.FixerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ExceptionResolver {

	@ExceptionHandler(value = IllegalArgumentException.class)
	public void handleIllegalArgumentException(HttpServletResponse response, IllegalArgumentException exception) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
	}

	@ExceptionHandler(value = FixerException.class)
	public void handleFixerException(HttpServletResponse response, FixerException exception) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
	}

}