package me.albertalef.realtodoapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import me.albertalef.realtodoapp.model.ToDo;
import me.albertalef.realtodoapp.request.patch.EditToDoRequest;
import me.albertalef.realtodoapp.request.post.CreateToDoRequest;
import me.albertalef.realtodoapp.service.ToDoService;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/todo")
public class ToDoController {

	private final ToDoService service;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Return all Tasks in an Array", description = "This method can receive a page, a page size and a sort param")
	@ApiResponse(responseCode = "200", description = "Successful Query")
	public List<ToDo> list( @ParameterObject @PageableDefault Pageable pageable){
		return service.findAll(pageable);
	}

	@PostMapping(path = "/create")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Create a new Task")
	@ApiResponse(responseCode = "201", description = "Successful Operation")
	public ToDo create(@RequestBody @Validated CreateToDoRequest toDo){
		return service.save(toDo);
	}

	@GetMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Find a Task by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful Operation"),
			@ApiResponse(responseCode = "400", description = "When Task with given id does not exist")
	})
	public ToDo findById( @PathVariable int id){
		return service.findById(id);
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Remove a Task")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful Operation"),
			@ApiResponse(responseCode = "400", description = "When Task with given id does not exist")
	})
	public ToDo remove( @PathVariable int id){
		return service.remove(id);
	}


	@PatchMapping(path = "/finish/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	@Operation(summary = "Finish a Task")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "Successful Operation"),
			@ApiResponse(responseCode = "400", description = "When Task with given id does not exist or when the Task is already finished", content = @Content())
	})
	public ToDo finish( @PathVariable int id){
		return service.finish(id);
	}

	@PatchMapping(path = "/unfinish/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	@Operation(summary = "Undo Finish a Task")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "Successful Operation"),
			@ApiResponse(responseCode = "400", description = "When Task with given id does not exist or when the Task is not finished", content = @Content())
	})
	public ToDo unFinish( @PathVariable int id){
		return service.unFinish(id);
	}

	@PatchMapping(path = "/edit")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Edit a Task")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful Operation"),
			@ApiResponse(responseCode = "400", description = "When Task with given id does not exist or when the Task is already finished", content = @Content())
	})
	public ToDo edit( @RequestBody @Validated EditToDoRequest todo ){
		return service.edit(todo);
	}
}
