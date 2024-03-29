package org.zerock.mallapi.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "owner")
@Table(name = "tbl_cart", indexes = {@Index(name="idx_cart_email", columnList = "member_owner")})
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cno;


    @OneToOne
    @JoinColumn(name = "member_owner")
    private Member owner;
    // 여기서는 조인컬럼을 쓰는 이유는 인덱스를 쓰기 위해서.
    // 실제로 조회를 할 때는 사용자의 ID(혹은 이메일)를 가지고 조회하기 때문에 결과적으로 JoinColumn의 name은 사용자의 이메일이 들어간다.
}
