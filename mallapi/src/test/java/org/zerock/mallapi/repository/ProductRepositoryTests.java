package org.zerock.mallapi.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mallapi.domain.Product;
import org.zerock.mallapi.dto.PageRequestDTO;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testInsert() {

        for (int i = 0; i < 10; i++) {
            Product product = Product.builder().pname("Test").pdesc("Test Desc").price(1000).build();

            product.addImageString(UUID.randomUUID() + "_" + "IMAGE1.jpg");
            product.addImageString(UUID.randomUUID() + "_" + "IMAGE2.jpg");
            productRepository.save(product);

        }

    }

//    @Transactional  // 이게 없으면 에러가 난다. -> 테이블을 두 번 조회해야하기 때문에 session 이 두개 필요함. 근데 없으면 세션이 하나밖에 안생김
//    @Test
//    public void testRead() {
//        Long pno = 1L;
//
//        Optional<Product> result = productRepository.findById(pno);
//
//        Product product = result.orElseThrow();
//
//        log.info(product);
//
//        log.info(product.getImageList());
//    }

    @Test
    public void testRead() {
        Long pno = 1L;

        Optional<Product> result = productRepository.selectOne(pno);

        Product product = result.orElseThrow();

        log.info(product);

        log.info(product.getImageList());
    }

    @Commit
    @Transactional
    @Test
    public void testDelete() {
        Long pno = 2L;

        productRepository.updateToDelete(2L, true);
    }

    @Test
    public void testUpdate() {
        Product product = productRepository.selectOne(1L).get();

        product.changePrice(3000);

        product.clearList();    // 다른 어레이리스트로 바꾸면 안된다.

        product.addImageString(UUID.randomUUID() + "_" + "PIMAGE1.jpg");
        product.addImageString(UUID.randomUUID() + "_" + "PIMAGE2.jpg");
        product.addImageString(UUID.randomUUID() + "_" + "PIMAGE3.jpg");

        productRepository.save(product);
    }

    @Test
    public void testList() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("pno").descending());

        Page<Object[]> result = productRepository.selectList(pageable);

        result.getContent().forEach(arr -> log.info(Arrays.toString(arr)));
    }

    @Test
    public void testSearch() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();

        productRepository.searchList(pageRequestDTO);
    }
}
