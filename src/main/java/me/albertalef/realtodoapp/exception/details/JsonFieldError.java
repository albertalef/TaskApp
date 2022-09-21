package me.albertalef.realtodoapp.exception.details;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder
public class JsonFieldError {
	private String fieldName;
	private String errorMessage;
}
