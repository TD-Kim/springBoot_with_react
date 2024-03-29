package org.zerock.mallapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.mallapi.domain.Cart;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT cart FROM Cart cart WHERE cart.owner.email = :email")    // 사용이유 : 카드가 없으면 추가 해야된다. 근데 카드가 있으면 그냥 카트 아이템만 추가하면 돼서
    Optional<Cart> getCartOfMember(@Param("email") String email);

}
