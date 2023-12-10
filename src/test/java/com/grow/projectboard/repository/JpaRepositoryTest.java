package com.grow.projectboard.repository;

import com.grow.projectboard.config.JpaConfig;
import com.grow.projectboard.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
// Repository를 이용한 테스트를 진행할 때 인메모리 DB를 많이 사용한다.
// 들어가보면 @Transactional 이 들어가 있다. 트랜잭션이 동작하기 때문에 기본적으로 롤백된다고 보면 된다.
@DataJpaTest
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    public JpaRepositoryTest(@Autowired ArticleRepository articleRepository,@Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }
    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine(){
        List<Article> articles = articleRepository.findAll();

        assertThat(articles)
                .isNotNull();
    }
    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine(){
        long previousCount = articleRepository.count();
        Article article = Article.of("new article", "new content", "#spring");

        Article savedArticle = articleRepository.save(article);

        assertThat(articleRepository.count())
                .isEqualTo(previousCount + 1);
    }

    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorksFine(){
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#springboot";
        article.setHashtag(updatedHashtag);

        // save 부분을 saveAndFlush 로 바꾸면 쿼리문을 확인할 수 있다. 다만 업데이트 내용이 DB에 적용되지는 않고 마찬가지로 롤백된다.
        Article savedArticle = articleRepository.saveAndFlush(article);

        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag",updatedHashtag);
    }
    @DisplayName("delete 테스트")
    @Test
    void givenTestData_whenDeleting_thenWorksFine(){
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long priviousArticleCommnetCount = articleCommentRepository.count();
        int deletedCommentsSize = article.getArticleComments().size();

        articleRepository.delete(article);

        assertThat(articleRepository.count()).isEqualTo(previousArticleCount -1);
        assertThat(articleCommentRepository.count()).isEqualTo(priviousArticleCommnetCount - deletedCommentsSize);
    }
}