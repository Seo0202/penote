package com.penote.penote.controller;

import com.penote.penote.entity.Article;
import com.penote.penote.repository.ArticleRepository;
import com.penote.penote.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
class CommentControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        // 🔥 MockMvc 직접 생성
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("댓글 등록 테스트")
    void createComment() throws Exception {
        // given: 댓글 달 기사 하나 저장
        Article article = articleRepository.save(
                new Article(null, "테스트 제목", "테스트 내용")
        );

        String nickname = "회원A";
        String body = "좋은 글이네요!";

        // when & then: 댓글 생성 요청
        mockMvc.perform(post("/article/" + article.getId() + "/comments")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("nickname", nickname)
                        .param("body", body))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/article/" + article.getId()));

        // DB 저장 검증
        assertThat(commentRepository.findByArticleId(article.getId()).size()).isEqualTo(1);
        assertThat(commentRepository.findByArticleId(article.getId()).get(0).getBody())
                .isEqualTo(body);
    }
}
