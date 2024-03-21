package org.zerock.mallapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    // 데이터베이스에 파일을 저장하면 성능이 안나옴.
    // 조회할 때는 파일의 이름만 가지고 조회

    private Long pno;

    private String pname;

    private int price;

    private String pdesc;

    private boolean delFlag;

    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>();      // 진짜 파일

    @Builder.Default
    private List<String> uploadFileNames = new ArrayList<>();

}
