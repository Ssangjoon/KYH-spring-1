package com.grow.projectboard.service;

import com.grow.projectboard.domain.Article;
import com.grow.projectboard.dto.ArticleDto;
import com.grow.projectboard.dto.ArticleWithCommentsDto;
import com.grow.projectboard.repository.ArticleRepository;
import com.grow.projectboard.type.SearchType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional // TODO : 학습 필요
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        // blank => 빈문자열이거나 스페이스로만 이루어진 것도 포함한다.
        if(searchKeyword == null || searchKeyword.isBlank()){
            // service 코드는 도메인코드와 articleDto만 알게된다.
            return articleRepository.findAll(pageable).map(ArticleDto::from);
        }
        // 자바 11 이후 추천되는 switch
        return switch (searchType){
            case TITLE -> articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
            case CONTENT -> articleRepository.findByContentContaining(searchKeyword, pageable).map(ArticleDto::from);
            case ID -> articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable).map(ArticleDto::from);
            case NICKNAME -> articleRepository.findByUserAccount_NicknameContaining(searchKeyword, pageable).map(ArticleDto::from);
            case HASHTAG -> articleRepository.findByHashtag("#" + searchKeyword, pageable).map(ArticleDto::from);
        };
    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticle(Long articleId) {
        return articleRepository.findById(articleId).map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    public void saveArticle(ArticleDto dto) {
        articleRepository.save(dto.toEntity());
    }

    public void updateArticle(ArticleDto dto) {
        try{
            Article article = articleRepository.getReferenceById(dto.id());
            if(dto.title() != null){article.setTitle(dto.title()); }
            if(dto.content() != null){article.setContent(dto.content()); }
            article.setHashtag(dto.hashtag());
            // class level transaction에 의해 메서드 단위로 transaction이 묶여있다.
            // 그래서 transaction이 끝날 때 영속성 컨텍스트는 아티클이 변한 것을 감지한다.
            // 끝날 때 감지 된 부분에 대해 쿼리를 날린다.
            // 다만 코드레벨에서 명시하고 싶다면 save해도 된다.
            // articleRepository.save(article);
        } catch (EntityNotFoundException e){
            log.warn("업데이트 실패. 게시글을 찾을 수 없습니다. - dto: {}", dto);
        }
    }

    public void deleteArticle(long articleId) {
        articleRepository.deleteById(articleId);
    }

    public long getArticleCount() {
        return articleRepository.count();
    }

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticlesViaHashtag(String hashtag, Pageable pageable) {
        if(hashtag == null || hashtag.isBlank()){
            return Page.empty(pageable);
        }
        return articleRepository.findByHashtag(hashtag, pageable).map(ArticleDto::from);
    }

    public List<String> getHashtags() {
        return articleRepository.findAllDistinctHashtags();
    }
}
