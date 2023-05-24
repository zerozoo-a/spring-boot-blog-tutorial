package org.example.spring1.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.spring1.domain.Article;
import org.example.spring1.dto.AddArticleRequest;
import org.example.spring1.dto.UpdateArticleRequest;
import org.example.spring1.repository.BlogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
class BlogApiControllerTest {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;
	@Autowired
	BlogRepository blogRepository;
	@Autowired
	private WebApplicationContext context;

	@BeforeEach
	public void mockMvcSetUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.build();
		blogRepository.deleteAll();
	}

	@DisplayName("addArticle: success add post to blog")
	@Test
	public void addArticle() throws Exception {
		// given
		final String url = "/api/articles";
		final String title = "title";
		final String content = "content";
		final AddArticleRequest userRequest = new AddArticleRequest(title, content);

		// Serialize POJO to JSON
		final String requestBody = objectMapper.writeValueAsString(userRequest);

		// when
		// Request based on the setting
		ResultActions result = mockMvc.perform(post(url)
				.contentType(APPLICATION_JSON_VALUE)
				.content(requestBody));

		// then
		result.andExpect(status().isCreated());

		List<Article> articles = blogRepository.findAll();

		assertThat(articles.size()).isEqualTo(1);
		assertThat(articles.get(0).getTitle()).isEqualTo(title);
		assertThat(articles.get(0).getContent()).isEqualTo(content);
	}


	@DisplayName("findAllArticles: query blog articles")
	@Test
	public void findAllArticles() throws Exception {
		// given
		final String url = "/api/articles";
		final String title = "title";
		final String content = "content";

		// query for save
		blogRepository.save(Article.builder()
				.title(title)
				.content(content)
				.build());

		// when
		final ResultActions resultActions = mockMvc.perform(get(url)
				.accept(MediaType.APPLICATION_JSON));

		// then
		resultActions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].content").value(content))
				.andExpect(jsonPath("$[0].title").value(title));
	}

	@DisplayName("should query blog post")
	@Test
	public void findArticle() throws Exception {
		// given
		final String url = "/api/articles/{id}";
		final String title = "title";
		final String content = "content";

		Article savedArticle = blogRepository.save(
				Article.builder()
						.title(title)
						.content(content)
						.build());

		// when
		final ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()));

		// then
		resultActions.andExpect(status().isOk())
				.andExpect(jsonPath("$.content").value(content))
				.andExpect(jsonPath("$.title").value(title));
	}

	@DisplayName("deleteArticle: 블로그 글 삭제에 성공한다.")
	@Test
	public void deleteArticle() throws Exception {
		// given
		final String url = "/api/articles/{id}";
		final String title = "title";
		final String content = "content";

		Article savedArticle = blogRepository.save(Article.builder()
				.title(title)
				.content(content)
				.build());

		// when
		mockMvc.perform(delete(url, savedArticle.getId()))
				.andExpect(status().isOk());

		// then
		List<Article> articles = blogRepository.findAll();

		assertThat(articles).isEmpty();
	}

	@DisplayName("updateArticle: 블로그 글 수정에 성공한다.")
	@Test
	public void updateArticle() throws Exception {
		// given
		final String url = "/api/articles/{id}";
		final String title = "title";
		final String content = "content";

		Article savedArticle = blogRepository.save(Article.builder()
				.title(title)
				.content(content)
				.build());

		final String newTitle = "new Title!";
		final String newContent = "new Content!";

		UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);

		// when
		ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
				.contentType(APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(request)));

		// then
		result.andExpect(status().isOk());

		Article article = blogRepository.findById(savedArticle.getId()).get();

		assertThat(article.getTitle()).isEqualTo(newTitle);
		assertThat(article.getContent()).isEqualTo(newContent);
	}

}