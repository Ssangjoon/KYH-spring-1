package com.grow.projectboard.controller;

import com.grow.projectboard.config.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@DisplayName("View 컨트롤러 - 인증")
@Import(SecurityConfig.class)
// MVC를 위한 테스트. Web Layer만 로드하며
// @Controller, @ControllerAdvice, @JsonComponent, @Convert, @GenericConverter,
// @Filter, @WebMvcConfigurer, @HandlerMethodArgumentResolver 등의 항목만 스캔하도록 제한하여 가벼운 테스트가 가능하다.
// 웹 어플리케이션과 관련된 Bean만 등록하기 때문에 통테보다 당연히 빠르다.
// Mock을 기반으로 한 테스트이다.
@WebMvcTest
public class AuthController {
    private final MockMvc mvc;

    // 테스트 패키지에서는 @Autowired 생략 안된다.
    public AuthController(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[view][GET] 로그인 페이지 - 정상호출")
    @Test
    public void givenNothing_whenTryingToLogin_thenReturnsLoginsPage() throws Exception {
        //Given
        //When & Then
        mvc.perform(get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML)); // 호환되는 타입이라면 pass
    }
}
