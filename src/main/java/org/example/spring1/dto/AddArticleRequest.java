package org.example.spring1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.spring1.domain.Article;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequest {
	private String title;
	private String content;

	public Article toEntity() {
		return Article.builder()
				.title(title)
				.content(content)
				.build();
	}

}
