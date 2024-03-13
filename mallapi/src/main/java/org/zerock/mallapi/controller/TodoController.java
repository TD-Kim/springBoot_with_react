package org.zerock.mallapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.zerock.mallapi.dto.PageRequestDTO;
import org.zerock.mallapi.dto.PageResponseDTO;
import org.zerock.mallapi.dto.TodoDTO;
import org.zerock.mallapi.service.TodoService;

import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor    // 자동으로 주입받아야 하니까
@RequestMapping("/api/todo")
public class TodoController {
    // RestControllerAdvice 는 RestController 의 오류를 처리해주는 클래스

    private final TodoService todoService;

    @GetMapping("/{tno}")
    public TodoDTO get(@PathVariable("tno") Long tno) {

        return todoService.get(tno);
    }

    @GetMapping("/list")
    public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO) {

        log.info("list................" + pageRequestDTO);

        return todoService.getList(pageRequestDTO);

    }

    @PostMapping("/")
    public Map<String, Long> register(@RequestBody TodoDTO dto) { // json 으로 바꿔서 return 하는게 좋기 때문에 Map 으로 반환

        log.info("todoDTO: " + dto);

        Long tno = todoService.register(dto);

        return Map.of("TNO", tno);
    }

    @PutMapping("/{tno}")
    public Map<String, String> modify(@PathVariable("tno") Long tno, @RequestBody TodoDTO todoDTO) {

        todoDTO.setTno(tno);

        todoService.modify(todoDTO);

        return Map.of("RESULT", "SUCCESS");

    }

    @DeleteMapping("/{tno}")
    public Map<String, String> remove(@PathVariable Long tno) {
        todoService.remove(tno);

        return Map.of("RESULT", "SUCCESS");
    }
}
