package me.albertalef.realtodoapp.exception.details;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@SuperBuilder
public class MethodArgumentNotValidExceptionDetails extends ExceptionDetails {
	private List<JsonFieldError> fields;

	public static List<JsonFieldError> parseFields(List<FieldError> fields){
		return fields.stream()
				.map(oldField -> new JsonFieldError(oldField.getField(), oldField.getDefaultMessage()))
				.collect(Collectors.toList());
	}
}
