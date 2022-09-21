package me.albertalef.realtodoapp.exception.details;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class ExceptionDetails {
	protected LocalDateTime timestamp;
	protected int status;
	protected String error;
	protected String message;
}
