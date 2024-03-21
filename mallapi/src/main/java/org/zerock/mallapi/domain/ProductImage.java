package org.zerock.mallapi.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {

    private String fileName;

    private int ord;    // 순번. ord 가 0번인 애가 화면에 출력되게끔 목록에서 출력하기 위해서 만듬

    public void setOrd(int ord) {
        this.ord = ord;
    }
}
