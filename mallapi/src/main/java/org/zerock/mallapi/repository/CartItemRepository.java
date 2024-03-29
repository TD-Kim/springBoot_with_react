package org.zerock.mallapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.mallapi.domain.CartItem;
import org.zerock.mallapi.dto.CartItemListDTO;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // 특정한 사용자의 모든 장바구니 아이템들을 가져올 경우 input -> email, out -> CartItemListDTO
    // 이전에는 DTO로 바꾸는거는 service 에서 처리했는데 projections 를 이용할거기 때문에

    @Query("SELECT new org.zerock.mallapi.dto.CartItemListDTO(ci.cino, ci.qty, p.pname, p.price, p.pno, pi.fileName) " +
            " FROM CartItem ci " +
            "INNER JOIN Cart mc ON ci.cart = mc" +
            " LEFT JOIN Product p ON ci.product = p " +
            " LEFT JOIN p.imageList pi " +
            "WHERE pi.ord = 0" +
            "  AND mc.owner.email = :email " +
            "ORDER BY ci desc")
    List<CartItemListDTO> getItemsOfCartDTOByEmail(@Param("email") String email);

    // 이메일, 상품번호로 해당 상품이 장바구니 아이템으로 존재하는지 확인
    @Query("SELECT ci FROM CartItem ci LEFT JOIN Cart c ON ci.cart = c WHERE c.owner.email = :email AND ci.product.pno = :pno")
    CartItem getItemOfPno(@Param("email") String email, @Param("pno") Long pno);

    // 장바구니 아이템 번호로 장바구니 번호를 얻어오려고 하는 경우
    @Query("SELECT c.cno FROM Cart c LEFT JOIN CartItem ci ON ci.cart = c WHERE ci.cino = :cino")
    Long getCartFromItem(@Param("cino") Long cino);

    // 장바구니 번호로 모든 장바구니 아이템들 조회
    @Query("SELECT new org.zerock.mallapi.dto.CartItemListDTO(ci.cino, ci.qty, p.pname, p.price, p.pno, pi.fileName)  " +
            " FROM CartItem ci " +
            "INNER JOIN Cart mc ON ci.cart = mc " +
            " LEFT JOIN Product p ON ci.product = p " +
            " LEFT JOIN p.imageList pi " +
            "WHERE pi.ord = 0 " +
            "  AND mc.cno = :cno " +
            "ORDER BY ci desc")
    List<CartItemListDTO> getItemsOfCartDTOByCart(@Param("cno") Long cno);

}
