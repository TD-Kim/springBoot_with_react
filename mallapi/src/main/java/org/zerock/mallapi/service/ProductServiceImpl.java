package org.zerock.mallapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.mallapi.domain.Product;
import org.zerock.mallapi.domain.ProductImage;
import org.zerock.mallapi.dto.PageRequestDTO;
import org.zerock.mallapi.dto.PageResponseDTO;
import org.zerock.mallapi.dto.ProductDTO;
import org.zerock.mallapi.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("pno").descending());

        Page<Object[]> result = productRepository.selectList(pageable);

        // Object[] => 0번째 product, 1번째 productImage
        // Object[] => 0번째 product, 1번째 productImage
        // Object[] => 0번째 product, 1번째 productImage

        List<ProductDTO> dtoList = result.get().map(arr -> {
            ProductDTO productDTO = null;

            Product product = (Product) arr[0];
            ProductImage productImage = (ProductImage) arr[1];

            productDTO = ProductDTO.builder()
                    .pno(product.getPno())
                    .pname(product.getPname())
                    .pdesc(product.getPdesc())
                    .price(product.getPrice())
                    .build();

            String imageStr = productImage.getFileName();
            productDTO.setUploadFileNames(List.of(imageStr));

            return productDTO;
        }).collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        return PageResponseDTO.<ProductDTO>withAll()
                .dtoList(dtoList)
                .totalCount(totalCount)
                .pageRequestDTO(pageRequestDTO)
                .build();


    }

    @Override
    public Long register(ProductDTO productDTO) {

        Product product = dtoToEntity(productDTO);

        log.info("-----------------------------");
        log.info(product);
        log.info(product.getImageList());

        Long pno = productRepository.save(product).getPno();

        return pno;
    }

    @Override
    public ProductDTO get(Long pno) {

        Optional<Product> result = productRepository.findById(pno);

        Product product = result.orElseThrow();

        return entityToDto(product);
    }

    @Override
    public void modify(ProductDTO productDTO) {
        // 조회
        Optional<Product> result = productRepository.findById(productDTO.getPno());

        Product product = result.orElseThrow();

        // 변경 내용 반영
        product.changePrice(productDTO.getPrice());
        product.changePname(productDTO.getPname());
        product.changePdesc(productDTO.getPdesc());
        product.changeDel(productDTO.isDelFlag());

        // 이미지 처리(삭제 되었는지 안되었는지 모름)
        // 이미지 처리를 하려면 어레이리스트를 다른 객체로 바꾸면 안되기 때문에(바뀌면 JPA 가 알지 못함) 어레이리스트를 비워줘야한다.
        List<String> uploadFileNames = productDTO.getUploadFileNames(); // 이미 저장된거를 가져오는건데 어떤건 저장됐고 어떤건 안됐는지 알 수 없어서 일단 다 지운다.

        product.clearList();

        if(uploadFileNames != null && !uploadFileNames.isEmpty()) {
            uploadFileNames.forEach(uploadName -> {
                product.addImageString(uploadName);
            });
        }

        // 저장
        productRepository.save(product);
    }

    @Override
    public void remove(Long pno) {
        productRepository.deleteById(pno);
    }

    private ProductDTO entityToDto(Product product) {

        ProductDTO productDTO = ProductDTO.builder()
                .pno(product.getPno())
                .pname(product.getPname())
                .pdesc(product.getPdesc())
                .price(product.getPrice())
                .delFlag(product.isDelFlag())
                .build();

        List<ProductImage> imageList = product.getImageList();

        if(imageList == null || imageList.isEmpty()) {
            return productDTO;
        }

        List<String> fileNameList = imageList.stream().map(productImage ->
                productImage.getFileName()).toList();

        productDTO.setUploadFileNames(fileNameList);

        return productDTO;
    }

    private Product dtoToEntity(ProductDTO productDTO) {

        Product product = Product.builder()
                .pno(productDTO.getPno())
                .pname(productDTO.getPname())
                .pdesc(productDTO.getPdesc())
                .price(productDTO.getPrice())
                .build();

        List<String> uploadFileNames = productDTO.getUploadFileNames();

        if(uploadFileNames == null || uploadFileNames.size() == 0) {
            return product;
        }

        uploadFileNames.forEach(fileName -> {
            product.addImageString(fileName);
        });

        return product;
    }
}
