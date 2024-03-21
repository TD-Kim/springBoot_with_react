package org.zerock.mallapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.mallapi.domain.Product;
import org.zerock.mallapi.repository.search.ProductSearch;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductSearch {

    @EntityGraph(attributePaths = "imageList")
    @Query("SELECT p FROM Product p WHERE p.pno = :pno")
    Optional<Product> selectOne(@Param("pno") Long pno);

    @Modifying
    @Query("UPDATE Product p SET p.delFlag = :delFlag WHERE p.pno = :pno")
    void updateToDelete(@Param("pno") Long pno, @Param("delFlag") boolean flag);

    // 상품 목록을 가져올 때 이미지도 같이 나오기 때문에 같이 처리를 해야함
    // 상품 하나당 리스트가 10개라면 상품 하나씩 쿼리가 날아감. 리스트 가져오는 쿼리 한 번, 상품 10개 쿼리 한 번. 이렇게 되면 서버 죽음
    @Query("SELECT p, pi FROM Product p LEFT JOIN p.imageList pi WHERE pi.ord = 0 and p.delFlag = false")
    Page<Object[]> selectList(Pageable pageable);
}
