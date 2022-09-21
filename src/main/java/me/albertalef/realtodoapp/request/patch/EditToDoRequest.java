package me.albertalef.realtodoapp.request.patch;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class EditToDoRequest {

	@NotNull(message = "This field cannot be null")
	@Schema(description = "The id of the task to be edited")
	private int id;
	@Schema(description = "The resume of the task", defaultValue = "Modifying the resume from a task")
	private String resume;
	@Schema(description = "The resume of the task", defaultValue = "A new creative description")
	private String description;
}
