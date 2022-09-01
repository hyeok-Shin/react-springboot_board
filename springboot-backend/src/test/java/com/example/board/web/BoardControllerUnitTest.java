package com.example.board.web;
import com.example.board.domain.Post;
import com.example.board.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//단위 테스트 (Controller, Filter, ControllerAdvice)

/**
 * @WebMvcTest에 - @AutoConfigureMockMvc가 포함되어 있어 따로 선언 필요없음
 * @MockBean - bean 등록된다. (가짜 실제x)
 */
@Slf4j
@WebMvcTest
public class BoardControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;



    /**
     *
     * new ObjectMapper().writeValueAsString() - Object를 json으로 바꾼다.
     * new ObjectMapper().readValue() - json을 Object으로 바꾼다.
     */

    //BDDMockito 패턴
    @Test
    public void write_test() throws Exception {

        // given (테스트를 하기 위한 준비)
        Post post = new Post(null, "테스트 입니다", "동혁");
        String content = new ObjectMapper().writeValueAsString(post); //Object를 json으로 바꾼다
        log.info(content);

        when(postService.write(post)).thenReturn(new Post(1L, "테스트 입니다.", "동혁")); //결과값 미리 지정

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
        posts.add(new Post(2L, "테스트2 입니다", "동혁"));
        when(postService.selectAll()).thenReturn(posts);

        //when
        ResultActions resultActions = mockMvc.perform(get("/post")
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2))) //데이터 사이즈 검증
                .andExpect(jsonPath("$.[0].title").value("테스트 입니다.")) //0번째 title 검증
                .andDo(MockMvcResultHandlers.print()); // 결과를 콘솔에 출력



}

    @Test
    public void findById_test() throws Exception {

        //given
        Long id = 1L;
        when(postService.select(id)).thenReturn(new Post(1L, "테스트으으으", "동동"));

        //when
        ResultActions resultActions = mockMvc.perform(get("/post/{id}", id)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("테스트으으으"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void update_test() throws Exception {

        //given
        Long id = 1L;
        Post post = new Post(null, "테스트 수정해봐", "동동");
        String content = new ObjectMapper().writeValueAsString(post);

        when(postService.update(id, post)).thenReturn(new Post(1L, "테스트 수정", "동동"));

        //when
        ResultActions resultActions = mockMvc.perform(put("/post/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("테스트 수정"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void delete_test() throws Exception {

        //given
        Long id = 1L;

        when(postService.delete(id)).thenReturn("ok");

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
