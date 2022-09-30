package com.example.fullstacktodolist.service;

import com.example.fullstacktodolist.dto.TodoDTO;
import com.example.fullstacktodolist.entity.TodoListEntity;
import com.example.fullstacktodolist.model.Todo;
import com.example.fullstacktodolist.repository.TodoRepository;
import com.example.fullstacktodolist.util.DateTimeUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TodoService {

    private final DateTimeUtil dateTimeUtil;
    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;

    public List<TodoDTO> addTodo(@NonNull String newTodo){

        //  Initialize new To do
        TodoListEntity todo = TodoListEntity.builder()
                .todoId(UUID.randomUUID())
                .todo(newTodo)
                .createdDate(dateTimeUtil.currentDate())
                .modifiedDate(dateTimeUtil.currentDate())
                .build();

        //Save to Db
        todoRepository.save(todo);


        return getAllTodos();


    }

    public List<TodoDTO> updateTodo(@NonNull Todo updatedTodo){
        //get to do
        TodoListEntity oldTodo = todoRepository.findByTodoId(updatedTodo.getTodoId());

        //check if todoId exist
        if(oldTodo == null) throw new RuntimeException("Todo does not exist");

        // Update To do
        TodoListEntity newTodo = TodoListEntity.builder()
                .todoId(oldTodo.getTodoId())
                .todo(updatedTodo.getTodo())
                .createdDate(oldTodo.getCreatedDate())
                .modifiedDate(dateTimeUtil.currentDate())
                .build();

        // save to database
        todoRepository.save(newTodo);

        return getAllTodos();

    }

    public List<TodoDTO> deleteTodo(UUID id){
        TodoListEntity todo = todoRepository.findByTodoId(id);

        if(todo == null) throw new RuntimeException("Todo Does Not Exist");

        //Delete To do
        todoRepository.deleteByTodoId(id);

        return getAllTodos();
    }

    public List<TodoDTO> getAllTodos(){
        List<TodoListEntity> allTodos = todoRepository.findAll();
        List<TodoDTO> updatedList = new ArrayList<>();

        allTodos.forEach(data ->{
            updatedList.add(modelMapper.map(data, TodoDTO.class));
        });

        return updatedList;
    }
}
