package com.grow.projectboard.controller;

import com.grow.projectboard.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfig.class)
// MVC를 위한 테스트. Web Layer만 로드하며
// @Controller, @ControllerAdvice, @JsonComponent, @Convert, @GenericConverter,
// @Filter, @WebMvcConfigurer, @HandlerMethodArgumentResolver 등의 항목만 스캔하도록 제한하여 가벼운 테스트가 가능하다.
// 웹 어플리케이션과 관련된 Bean만 등록하기 때문에 통테보다 당연히 빠르다.
// Mock을 기반으로 한 테스트이다.
@WebMvcTest(MainController.class) // 필요한 빈만 가져온다.
class MainControllerTest {
    private final MockMvc mvc;

    public MainControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    void givenNothing_whenRequestingRootPage_thenRedirectsToArticlesPage() throws Exception {
        //given
        // When & Then
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("forward:/articles"))
                .andExpect(forwardedUrl("/articles"))
                .andDo(MockMvcResultHandlers.print());
    }

}