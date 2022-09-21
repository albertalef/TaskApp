package me.albertalef.realtodoapp.handler;

import me.albertalef.realtodoapp.exception.BadRequestException;
import me.albertalef.realtodoapp.exception.details.BadRequestExceptionDetails;
import me.albertalef.realtodoapp.exception.details.MethodArgumentNotValidExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<BadRequestExceptionDetails> handleBadRequestException( BadRequestException ex  ){
		return new ResponseEntity<>(BadRequestExceptionDetails.builder()
				.error("Bad Request")
				.status(HttpStatus.BAD_REQUEST.value())
				.timestamp(LocalDateTime.now())
				.message(ex.getMessage())
				.build(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<MethodArgumentNotValidExceptionDetails> handleMethodArgumentNotValidException( MethodArgumentNotValidException ex){
		return new ResponseEntity<>(MethodArgumentNotValidExceptionDetails.builder()
				.error("Bad Request").status(HttpStatus.BAD_REQUEST.value())
				.message("Wrong Json proprieties")
				.timestamp(LocalDateTime.now())
				.fields(MethodArgumentNotValidExceptionDetails.parseFields(ex.getFieldErrors()))
				.build(), HttpStatus.BAD_REQUEST);
	}
}
