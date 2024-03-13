package org.zerock.mallapi.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data   // DTO 는 막 가져다 쓰는 용도이기 때문에 getter setter 를 다 만든다.
@Builder
@AllArgsConstructor
@NoArgsConstructor  // 비어있는 생성자가 필요할 수도 있으니
public class TodoDTO {  // 일회용 컵같은 존재였음. bean 으로 관리가 되지 않는다.

    private Long tno;

    private String title;

    private String content;

    private boolean complete;

    private LocalDate dueDate;

}
