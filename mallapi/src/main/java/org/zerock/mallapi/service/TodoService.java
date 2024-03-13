package org.zerock.mallapi.service;

import org.springframework.transaction.annotation.Transactional;
import org.zerock.mallapi.domain.Todo;
import org.zerock.mallapi.dto.PageRequestDTO;
import org.zerock.mallapi.dto.PageResponseDTO;
import org.zerock.mallapi.dto.TodoDTO;

@Transactional
public interface TodoService {

    TodoDTO get(Long tno);

    Long register(TodoDTO dto);

    void modify(TodoDTO dto);

    void remove(Long tno);

    PageResponseDTO<TodoDTO> getList(PageRequestDTO pageRequestDTO);

    // java 8 버전부터 인터페이스에 default 구현체를 추가할 수 있음. 그래서 추상클래스가 애매해졌다..
    default TodoDTO entityToDTO(Todo todo) {

        TodoDTO todoDTO = TodoDTO.builder()
                .tno(todo.getTno())
                .title(todo.getTitle())
                .content(todo.getContent())
                .complete(todo.isComplete())
                .dueDate(todo.getDueDate())
                .build();

        return todoDTO;
    }

    default Todo dtoToEntity(TodoDTO todoDTO) {
        Todo todo = Todo.builder()
                .tno(todoDTO.getTno())
                .title(todoDTO.getTitle())
                .content(todoDTO.getContent())
                .complete(todoDTO.isComplete())
                .dueDate(todoDTO.getDueDate())
                .build();

        return todo;
    }

}
