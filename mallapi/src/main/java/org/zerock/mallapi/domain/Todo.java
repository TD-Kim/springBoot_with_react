package org.zerock.mallapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@ToString
@Getter // 엔티티는 불변으로 만드는게 좋아서 Getter 만
@Builder    // 여러개의 생성자를 만들때 귀찮아서 Builder를 쓰는데 세트로 아래 어노테이션 두개를 같이 자주 쓴다.
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_todo")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // strategy : 키 생성 전략
    private Long tno;

    @Column(length = 500, nullable = false)
    private String title;

    private String content;

    private boolean complete;

    private LocalDate dueDate;

    public void changeTno(Long tno) {
        this.tno = tno;
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void changeComplete(boolean complete) {
        this.complete = complete;
    }

    public void changeDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
