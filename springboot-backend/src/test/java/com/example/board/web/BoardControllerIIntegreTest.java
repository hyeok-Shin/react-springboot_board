package com.example.board.web;

import com.example.board.domain.Post;
import com.example.board.domain.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//통합테스트(전체 다 있다.)

/**
 * WebEnvironment.MOCK - 모의 웹 환경에서 테스트
 * WebEnvironment.RANDOM_PORT - 실제 웹 환경에서 테스트
 * MockMvc - 컨트롤러의 주소(url)을 테스트 할수 있는 라이브러리
 * @AutoConfigureMockMvc - MockMvc를 등록해준다.
 * @Transactional - 각각의 테스트함수가 종료될 때마다 트랙잭션을 rollback 해준다. (독립적 테스트)
 */
@Slf4j
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class BoardControllerIIntegreTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PostRepository postRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void init() {
        entityManager.createNativeQuery("ALTER TABLE post AUTO_INCREMENT = 1").executeUpdate();

    }

    //BDDMockito 패턴
    //BDDMockito 패턴
    @Test
    public void save_test() throws Exception {

        // given (테스트를 하기 위한 준비)
        Post post = new Post(null, "테스트 입니다.", "동혁");
        String content = new ObjectMapper().writeValueAsString(post); //Object를 json으로 바꾼다
        log.info(content);

        //when (테스트 실행)
        ResultActions resultActions = mockMvc.perform(post("/post") //url 지정
                .contentType(MediaType.APPLICATION_JSON) //던질 데이터 타입 지정
                .content(content) //실제로 던질 데이터 지정
                .accept(MediaType.APPLICATION_JSON)); //응답 데이터 타입 지정


        //then (검증)
        resultActions
                .andExpect(status().isCreated()) //201 응답 기대
                .andExpect(jsonPath("$.title").value("테스트 입니다.")) //검증하고 싶은 데이터($는 전제 $.aaa은 특정 데이터 - jsonPath문법 참고), 데이터 값
                .andDo(MockMvcResultHandlers.print()); //결과를 콘솔에 출력




    }

    @Test
    public void findAll_test() throws Exception {
        //given
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1L, "테스트 입니다.", "동혁"));
        posts.add(new Post(2L, "테스트2 입니다.", "동혁"));
        posts.add(new Post(3L, "테스트3 입니다.", "동혁"));
        postRepository.saveAll(posts);


        //when
        ResultActions resultActions = mockMvc.perform(get("/post")
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$", Matchers.hasSize(3))) //데이터 사이즈 검증
                .andExpect(jsonPath("$.[2].title").value("테스트3 입니다.")) //0번째 title 검증
                .andDo(MockMvcResultHandlers.print()); // 결과를 콘솔에 출력

    }

    @Test
    public void findById_test() throws Exception {

        //given
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1L, "테스트 입니다.", "동혁"));
        posts.add(new Post(2L, "테스트2 입니다.", "동혁"));
        posts.add(new Post(3L, "테스트3 입니다.", "동혁"));
        postRepository.saveAll(posts);

        Long id = 2L;

        //when
        ResultActions resultActions = mockMvc.perform(get("/post/{id}", id)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("테스트2 입니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void update_test() throws Exception {

        //given
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1L, "테스트 입니다.", "동혁"));
        posts.add(new Post(2L, "테스트2 입니다.", "동혁"));
        posts.add(new Post(3L, "테스트3 입니다.", "동혁"));
        postRepository.saveAll(posts);

        Long id = 3L;
        Post post = new Post(null, "테스트 수정", "동동");
        String content = new ObjectMapper().writeValueAsString(post);

        //when
        ResultActions resultActions = mockMvc.perform(put("/post/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.title").value("테스트 수정"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void delete_test() throws Exception {

        //given
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1L, "테스트 입니다.", "동혁"));
        posts.add(new Post(2L, "테스트2 입니다.", "동혁"));
        posts.add(new Post(3L, "테스트3 입니다.", "동혁"));
        postRepository.saveAll(posts);

        Long id = 1L;


        //when
        ResultActions resultActions = mockMvc.perform(delete("/post/{id}", id)
                .accept(MediaType.TEXT_PLAIN));


        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        //String(문자)를 응답할때 사용
        MvcResult requestResult = resultActions.andReturn();
        String result = requestResult.getResponse().getContentAsString();

        assertEquals("ok", result);
    }
}
