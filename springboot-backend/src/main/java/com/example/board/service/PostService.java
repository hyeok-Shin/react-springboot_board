package com.example.board.service;

import com.example.board.domain.Post;
import com.example.board.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//기능 정의, 트랜잭션 관리
@RequiredArgsConstructor //자동으로 final 생성자 생성
@Service
public class PostService {

    private final PostRepository postRepository;

    @Transactional //서비스 함수가 종료될 때 commit할지 rollback할지 트랙잭션 관리
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Transactional(readOnly = true) //변경감지 비활성화, update시 정합성 유지, insert의 유령데이터현상(팬텀현상) 못막음
    public Post select(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalIdentifierException("id를 확인해주세요!!"));
    }

    @Transactional(readOnly = true)
    public Page<Post> selectReply(Long originNo, Long layer, Pageable pageable) {
        return postRepository.selectReply(originNo, layer, pageable);
    }

    public Page<Post> selectOrigin(Long layer, String title, String content,  Pageable pageable) {
        return postRepository.selectOrigin(layer, title, content, pageable);

    }



    @Transactional
    public Post update(Long id, Post post) {
        //Dirty Checking update
        Post postEntity = postRepository.findById(id)
                .orElseThrow(() -> new IllegalIdentifierException("id를 확인해주세요!!")); //영속화 (post Object) -> 영속성 컨텍스트에 보관
        postEntity.setTitle(post.getTitle());
        postEntity.setContent(post.getContent());
        return postEntity;
    } //함수 종료 > 트랜쟉션 종료 > 영속화 되어있는 데이터를 DB로 갱신(flush > commit ----> Dirty Checking)

    @Transactional
    public String delete(Long id) {
        postRepository.deleteById(id); //오류가 발생하면 Exception
        return "ok";
    }

    @Transactional
    public String download(Long id) {
        postRepository.deleteById(id); //오류가 발생하면 Exception
        return "ok";
    }

}
