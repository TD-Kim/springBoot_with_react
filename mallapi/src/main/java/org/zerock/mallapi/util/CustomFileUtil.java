package org.zerock.mallapi.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Log4j2
@RequiredArgsConstructor
public class CustomFileUtil {

    @Value("${org.zerock.upload.path}")
    private String uploadPath;

    @PostConstruct  // 생성자 대신 많이 씀. 초기화 시킬 때
    public void init() {
        File tempFolder = new File(uploadPath);
        if(!tempFolder.exists()) {
            tempFolder.mkdir();
        }

        uploadPath = tempFolder.getAbsolutePath();
        log.info("-------------------------");
        log.info(uploadPath);
    }

    public List<String> saveFiles(List<MultipartFile> files) throws RuntimeException {

        if(files == null || files.size() == 0) {
            return null;
        }

        List<String> uploadNames = new ArrayList<>();

        for(MultipartFile file : files) {
            // 중복파일명을 방지하기위해 uuid 사용
            String savedName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path savePath = Paths.get(uploadPath, savedName);   // 경로 생성
            try {
                Files.copy(file.getInputStream(), savePath);    // 원본 파일 업로드

                // 용량이 클 수 있으니 이미지는 썸네일을 만들어준다. thumbnailator 라이브러리 사용
                String contentType = file.getContentType();     // Mime type 반환

                // 이미지 파일이라면
                if(contentType != null || contentType.startsWith("image")) {
                    Path thumbnailPath = Paths.get(uploadPath, "s_" + savedName);
                    Thumbnails.of(savePath.toFile()).size(200, 200).toFile(thumbnailPath.toFile()); // 사이즈 200, 200
                }


                uploadNames.add(savedName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }   // end for loop

        return uploadNames;
    }

    public ResponseEntity<Resource> getFile(String fileName) {
        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);

        if(!resource.isReadable()) {
            resource = new FileSystemResource((uploadPath + File.separator + "default.jpeg"));
        }

        HttpHeaders headers = new HttpHeaders();

        try {
            // probeContentType : 마임타입을 확인하지 못하면 null 을 반환한다. 실제 파일의 내용이 아니라 확장자를 이용하여 마임타입을 판단한다.
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    public void deleteFiles(List<String> fileNames) {
        if(fileNames == null || fileNames.size() == 0) {
            return;
        }

        fileNames.forEach(fileName -> {
            // 썸네일 삭제
            String thumbnailFileName = "s_" + fileName;

            Path thumbnaillPath = Paths.get(uploadPath, thumbnailFileName);
            Path filePath = Paths.get(uploadPath, fileName);

            try {
                // 파일이 존재하면 삭제해라
                Files.deleteIfExists(filePath);
                Files.deleteIfExists(thumbnaillPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }

}
