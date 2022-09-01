package com.example.board.web;

import com.example.board.domain.Post;
import com.example.board.domain.PostRepository;
import com.example.board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor //자동으로 final 생성자 생성
@RestController
public class PostController {

    PostRepository postRepository;

    private final PostService postService;
    //<?> 어떤타입인지 정해지지 않았다? 알아서 들어간다
    //return <>안에 타입 생략가능
    @CrossOrigin
    @PostMapping("/post") //@RequestBody를 선언하면 json으로 받는다.
    public ResponseEntity<?> write(@RequestPart(name = "post", required = false) Post post, @RequestPart(name = "file", required = false) MultipartFile file) throws IOException, IllegalAccessError {

        if (file!=null) {
            log.info("file org name = {}", file.getOriginalFilename());
            log.info("file content type = {}", file.getContentType());
            UUID uuid = UUID.randomUUID();
            String uploadFileName = uuid.toString() + '_' + file.getOriginalFilename();
            post.setFilePath("C:\\Users\\User\\Desktop\\files");
            post.setFileName(uploadFileName);
            file.transferTo(new File(Objects.requireNonNull(uploadFileName)));

        }

        return new ResponseEntity<>(postService.save(post), HttpStatus.CREATED);


    }

    @CrossOrigin
    @GetMapping("/download")
    public ResponseEntity<?> fileDownload(@RequestParam("fileName") String fileName) throws UnsupportedEncodingException {
        System.out.println("왔냐");
       new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        String path = "C:/Users/User/Desktop/files/" + fileName;
        try {
            Path filePath = Paths.get(path);
            Resource resource = new InputStreamResource(Files.newInputStream(filePath)); // 파일 resource 얻기
            File file = new File(path);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(file.getName()).build());  // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를 알려주는 헤더
            System.out.println("왔냐");
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch(Exception e) {

            return new ResponseEntity<>(null, HttpStatus.CONFLICT);

        }
    }

    @CrossOrigin
    @GetMapping("/post")
    public ResponseEntity<?> findAll(Long layer, String searchKeyword, @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
           return new  ResponseEntity<>(postService.selectOrigin(layer, searchKeyword, searchKeyword, pageable), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/reply/{originNo}/{layer}")
    public ResponseEntity<?> findByOriginNoAndLayer(@PathVariable Long originNo, @PathVariable Long layer, Pageable pageable) {
        System.out.println("여기야");
        return new ResponseEntity<>(postService.selectReply(originNo, layer, pageable), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/post/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return new ResponseEntity<>(postService.select(id), HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("/post/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Post post) {
        return new ResponseEntity<>(postService.update(id, post), HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id, @RequestParam("fileName") String fileName) {
        String filePath = "C:/Users/User/Desktop/files/";
        File file = new File(filePath +fileName);
        if(file.exists()) {
        file.delete();
        }

        return new ResponseEntity<>(postService.delete(id), HttpStatus.OK);
    }
}
