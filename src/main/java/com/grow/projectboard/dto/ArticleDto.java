package com.grow.projectboard.dto;

import com.grow.projectboard.domain.Article;

import java.time.LocalDateTime;


public record ArticleDto(
        Long id,
        UserAccountDto userAccountDto,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {
    public static ArticleDto of(Long id, UserAccountDto userAccountDto, String title, String content, String hashtag, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleDto(id, userAccountDto, title, content, hashtag, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleDto from(Article entity) { // entity를 입력하면 dto로 반환
        return new ArticleDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtag(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public Article toEntity() { // 반대로 dto로 부터 entity를 반환
        return Article.of(
                userAccountDto.toEntity(),
                title,
                content,
                hashtag
        );
    }
    // article은 dto를 몰라도 된다.
    // 오로지 dto만 연관관계 매핑을 위해 article의 정보를 알고 있다.
    // 즉 dto의 변경에 article이 영향을 받지 않는다.
}