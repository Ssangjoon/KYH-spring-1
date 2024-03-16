package com.grow.projectboard.controller;

import io.micrometer.core.instrument.binder.http.HttpRequestTags;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled("SpringDataRest 통합테스트는 불필요하므로 제외시킴")
@DisplayName("Data Rest - API 테스트")
@Transactional
// @AutoConfigureMockMvc + @SpringBootTest
// => 프로젝트 내부에 있는 스프링 빈을 보두 등록하여 테스트에 필요한 의존성을 추가한다.
// => 실제 운영환경에서 사용될 클래스들을 통합하여 테스트 하는 것.
@AutoConfigureMockMvc // Mock 테스트 시 필요한 의존성을 제공한다.
@SpringBootTest // 어플리케이션 내부의 모든 빈을 등록한다.
public class DataRestTest {
    private final MockMvc mockMvc;

    public DataRestTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @DisplayName("[api]게시글 리스트 조회")
    @Test
    void givenNothing_whenRequestingArticles_thenReturnsArticlesJsonResponse() throws Exception {

        mockMvc.perform(get("/api/articles"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.valueOf("application/hal+json")));
    }
    @DisplayName("[api]게시글 단건 조회")
    @Test
    void givenNothing_whenRequestingArticle_thenReturnsArticleJsonResponse() throws Exception {

        mockMvc.perform(get("/api/articles/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.valueOf("application/hal+json")));
    }
    @DisplayName("[api]게시글 -> 댓글 리스트 조회")
    @Test
    void givenNothing_whenRequestingArticleCommentsFromArticle_thenReturnsArticleCommentsJsonResponse() throws Exception {

        mockMvc.perform(get("/api/articles/2/articleComments"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.valueOf("application/hal+json")));
    }

    @DisplayName("[api]댓글 리스트 조회")
    @Test
    void givenNothing_whenRequestingArticleComments_thenReturnsArticleCommentsJsonResponse() throws Exception {

        mockMvc.perform(get("/api/articleComments"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.valueOf("application/hal+json")));
    }
    @DisplayName("[api]댓글 단건 조회")
    @Test
    void givenNothing_whenRequestingArticleComment_thenReturnsArticleCommentsJsonResponse() throws Exception {

        mockMvc.perform(get("/api/articleComments/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.valueOf("application/hal+json")));
    }
}
//@WebMvcTest // 슬라이스 테스트 (최소한의 테스트)
