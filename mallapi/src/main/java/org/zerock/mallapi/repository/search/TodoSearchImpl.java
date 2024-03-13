package org.zerock.mallapi.repository.search;

import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.mallapi.domain.QTodo;
import org.zerock.mallapi.domain.Todo;
import org.zerock.mallapi.dto.PageRequestDTO;

import java.util.List;

// querydsl 을 쓰면 반드시 interface의 파일명뒤에 Impl 로 끝나야한다
@Log4j2
public class TodoSearchImpl extends QuerydslRepositorySupport implements TodoSearch {

    public TodoSearchImpl() {
        super(Todo.class);
    }

    @Override
    public Page<Todo> search1(PageRequestDTO pageRequestDTO) {

        log.info("search1.................................");

        QTodo todo = QTodo.todo;    // 쿼리를 날리기 위한 객체

        JPQLQuery<Todo> query = from(todo);



//        query.where(todo.title.contains("1"));

        // 스프링부트 3.x 버전
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(), Sort.by("tno").descending());

        // 페이징 처리
        this.getQuerydsl().applyPagination(pageable, query);

        List<Todo> list = query.fetch();  // 쿼리를 실제 실행, 목록데이터를 가져온다

        long total = query.fetchCount(); // Long 타입으로 결과 나온다

        return new PageImpl<>(query.fetch(), pageable, query.fetchCount());
    }


}
