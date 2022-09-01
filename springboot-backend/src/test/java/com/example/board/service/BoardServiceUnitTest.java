package com.example.board.service;

//단위테스트 (서비스 관련 테스트)

/**
 * 서비스 단위 테스트 시  Repository 필요
 * Repository => 모의 객체로 만들 수 있음
 * @InjectMocks - @InjectMocks등록된 service객체가 만들어질때 @Mock로 등록된 Repository 주입 받는다.
 */

import com.example.board.domain.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BoardServiceUnitTest {

    @InjectMocks
    private PostService bookService;
    @Mock
    private PostRepository bookRepository;

    @Test
    public void 저장하기_테스트() {

    }

}
