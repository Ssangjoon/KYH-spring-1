package com.grow.projectboard.domain;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(name = "Article", indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Article extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // nullable 기본 true라서 false가 아니라면 컬럼 생략 해도 된다.
    @Setter @Column(nullable = false) private String title; // 제목
    @Setter @Column(nullable = false, length = 10000) private String content; // 내용
    @Setter private String hashtag; // 해시태그

    @ToString.Exclude // 순환참조 방지
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL) // 양방향 참조는 잘 안쓰기는 함
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    protected Article() {}

    // 메타데이터를 제외한 필드만
    // private 제어자로 막아놓고 팩토리 메서드로 편리하게 바로 생성하자
    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(String title, String content, String hashtag) {
        return new Article(title,content,hashtag);
    }

    // 동일성 동등성 검사
    // 그냥 lombok의 EqualsAndHashCode를 사용하면 전체 필드를 비교하기 때문에
    // Entity 클래스에서는 고유값인 id만 가지고 비교하기 위해서 따로 생성해 줌
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
