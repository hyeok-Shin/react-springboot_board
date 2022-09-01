package com.example.board.domain;


//단위 테스트 (DB 관현 테스트)
/**
 * Replace.ANY- 가짜 DB로 테스트
 * Replace.NONE - 실제 DB로 테스트
 * @Transactional - 각의 테스트함수가 종료될 때마다 트랙잭션을 rollback 해준다. (독립적 테스트)
 * @DataJpaTest - Repository들을 다 등록해줘서 Mok로 등록할 필요없이  @Autowired 사용
 */

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class BoardRepositoryUnitTest {
    
    @Autowired
    private PostRepository bookRepository;

    @Test
    public void save_테스트() {

        //given
//        Post book = new Post(null, "책제목1", "책저자1");

        //when
        Post bookEntity = bookRepository.save(book);

        //then
        assertEquals("책제목1", bookEntity.getTitle());
    }
}
