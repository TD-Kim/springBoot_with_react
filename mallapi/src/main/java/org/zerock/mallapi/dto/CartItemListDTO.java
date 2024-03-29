package org.zerock.mallapi.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class CartItemListDTO {

    private Long cino;

    private int qty;

    private String pname;

    private int price;

    private Long pno;

    private String imageFile;   // 이미지 파일명

    // JPA projections 사용하기 위해 생성자 만듬

    public CartItemListDTO(Long cino, int qty, String pname, int price, Long pno, String imageFile) {
        this.cino = cino;
        this.qty = qty;
        this.pname = pname;
        this.price = price;
        this.pno = pno;
        this.imageFile = imageFile;
    }
}
