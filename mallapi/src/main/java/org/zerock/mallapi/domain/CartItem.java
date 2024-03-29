package org.zerock.mallapi.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"cart"})   // 원래는 product 도 빼는게 좋다
@Table(
        name = "tbl_cart_item", indexes = {
                @Index(columnList = "cart_cno", name = "idx_cartitem_cart"),
                @Index(columnList = "product_pno, cart_cno", name = "idx_cartitem_pno_cart")

})
public class CartItem { // ElementCollection 으로도 만들 수 있지만 다른거 연습하기위해

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cino;

    @ManyToOne
    @JoinColumn(name = "product_pno")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_cno")
    private Cart cart;  // 원래는 케스케이딩도 생각해야함

    private int qty;

    public void changeQty(int qty) {
        this.qty = qty;
    }

}
