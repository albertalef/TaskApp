package me.albertalef.realtodoapp.service;

import lombok.RequiredArgsConstructor;
import me.albertalef.realtodoapp.exception.BadRequestException;
import me.albertalef.realtodoapp.mapper.ToDoMapper;
import me.albertalef.realtodoapp.model.ToDo;
import me.albertalef.realtodoapp.repository.ToDoRepository;
import me.albertalef.realtodoapp.request.patch.EditToDoRequest;
import me.albertalef.realtodoapp.request.post.CreateToDoRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ToDoService {

	private final ToDoRepository repository;

	public List<ToDo> findAll( Pageable pageable ) {
		return repository.findAll(pageable).getContent();
	}
	public ToDo save( CreateToDoRequest toDo ) {
		return repository.save(ToDoMapper.INSTANCE.toToDo(toDo));
	}
	public ToDo findById( int id ) {
		return repository.findById(id).orElseThrow(() -> new BadRequestException("To Do not found"));
	}
	public ToDo remove( int id ) {
		ToDo todo = findById(id);
		repository.delete(todo);
		return todo;
	}
	public ToDo finish( int id ) {
		ToDo todo = findById(id);

		if(todo.isFinishedAt()) throw new BadRequestException("This To Do is already finished!");

		todo.setFinishedAt(LocalDateTime.now());
		return repository.save(todo);
	}
	public ToDo edit( EditToDoRequest request ) {
		ToDo todo = findById(request.getId());

		if(todo.isFinishedAt()) throw new BadRequestException("You cannot do that, this todo is already finished");

		String resume = request.getResume();
		String description = request.getDescription();

		if(resume != null && !resume.isBlank()) todo.setResume(resume);
		if(description != null && !description.isBlank()) todo.setDescription(description);
		return repository.save(todo);
	}
	public List<ToDo> removeAll( List<Integer> ids ) {
		List<ToDo> toDoList = repository.findAllById(ids);

		repository.deleteAll(toDoList);

		return toDoList;
	}
}
