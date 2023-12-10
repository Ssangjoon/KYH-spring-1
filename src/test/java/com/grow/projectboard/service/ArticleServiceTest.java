package com.grow.projectboard.service;

import com.grow.projectboard.domain.Article;
import com.grow.projectboard.dto.ArticleDto;
import com.grow.projectboard.dto.ArticleUpdateDto;
import com.grow.projectboard.repository.ArticleRepository;
import com.grow.projectboard.type.SearchType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import javax.inject.Inject;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.doNothing;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class) // 스프링 테스트 패키지에 포함되어 있어 따로 설치할 필요 x
class ArticleServiceTest {
    @Inject private ArticleService sut; // System Under Test의 약어, 테스트 대상임을 의미
    @Mock private ArticleRepository articleRepository;

    @DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다.")
    @Test
    void givenSearchParameters_whenSearchArticles_thenReturnsArticles(){
        // given
        // when
        Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "search keyword"); // 제목, 본문, ID,  닉네임, 해시태그

        // then
        assertThat(articles).isNotNull();
    }

    @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
    @Test
    void givenArticleId_whenSearchArticles_thenReturnsArticles(){
        // given
        // when
        ArticleDto article = sut.searchArticle(1L);

        // then
        assertThat(article).isNotNull();
    }

    @DisplayName("게시글 정보를 입력하면 게시글을 생성한다.")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSavesArticle(){
        // given
        given(articleRepository.save(any(Article.class))).willReturn(null);

        // when
        sut.saveArticle(ArticleDto.of(LocalDateTime.now(), "ssang", "title", "content", "#java"));

        // then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글 Id와 수정정보를 입력하면, 게시글을 수정한다.")
    @Test
    void givenArticleIdAndModifiedInfo_whenUpdatingArticle_thenUpdatesArticle(){
        // given
        given(articleRepository.save(any(Article.class))).willReturn(null);

        // when
        sut.updateArticle(1L, ArticleUpdateDto.of( "title", "content", "#java"));

        // then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글 Id와 수정정보를 입력하면, 게시글을 삭제한다.")
    @Test
    void givenArticleId_whenDeletingArticle_thenDeletesArticle(){
        // given
        willDoNothing().given(articleRepository).delete(any(Article.class));

        // when
        sut.deleteArticle(1L);

        // then
        then(articleRepository).should().save(any(Article.class));
    }
}