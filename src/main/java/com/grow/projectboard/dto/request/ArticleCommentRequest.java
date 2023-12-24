package com.grow.projectboard.dto.request;

import com.grow.projectboard.dto.ArticleCommentDto;
import com.grow.projectboard.dto.UserAccountDto;

import java.io.Serializable;

public record ArticleCommentRequest(Long articleId, String content) {

    public static ArticleCommentRequest of(Long articleId, String content) {
        return new ArticleCommentRequest(articleId, content);
    }

    public ArticleCommentDto toDto(UserAccountDto userAccountDto) {
        return ArticleCommentDto.of(
                articleId,
                userAccountDto,
                content
        );
    }

}