package me.albertalef.realtodoapp.request.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class CreateToDoRequest {

	@NotBlank(message = "This field cannot be blank")
	@Schema(description = "The resume of the task", defaultValue = "Create a Web Application")
	private String resume;
	@Schema(description = "The description of the task", defaultValue = "A Application using Spring Boot to present in the software process class")
	private String description;

}
