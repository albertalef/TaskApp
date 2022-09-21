package me.albertalef.realtodoapp.mapper;

import me.albertalef.realtodoapp.model.ToDo;
import me.albertalef.realtodoapp.request.patch.EditToDoRequest;
import me.albertalef.realtodoapp.request.post.CreateToDoRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper

public abstract class ToDoMapper {

	public static final ToDoMapper INSTANCE = Mappers.getMapper(ToDoMapper.class);

	public abstract ToDo toToDo( CreateToDoRequest request );

}
