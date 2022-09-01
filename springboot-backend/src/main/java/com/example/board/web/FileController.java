package com.example.board.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FileController {

    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadFile(MultipartFile file) throws IllegalAccessError, IOException {
        if (!file.isEmpty()) {
            log.info("file org name = {}", file.getOriginalFilename());
            log.info("file content type = {}", file.getContentType());

            file.transferTo(new File(Objects.requireNonNull(file.getOriginalFilename())));
        }
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
