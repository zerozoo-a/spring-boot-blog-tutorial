package org.example.spring1.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.spring1.domain.Article;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class ArticleViewResponse {
	private Long id;
	private String title;
	private String content;
	private LocalDateTime createdAt;

	public ArticleViewResponse(Article article) {
		this.id = article.getId();
		this.title = article.getTitle();
		this.content = article.getContent();
		this.createdAt = article.getCreatedAt();
	}
}
