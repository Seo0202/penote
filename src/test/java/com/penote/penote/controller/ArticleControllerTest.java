//package com.penote.penote.controller;
//
//import com.penote.penote.entity.Article;
//import com.penote.penote.repository.ArticleRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@Transactional
//class ArticleControllerTest {
//
//    @Autowired
//    private WebApplicationContext context;
//
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ArticleRepository articleRepository;
//
//    @BeforeEach
//    void setUp() {
//        // 🔥 여기서 MockMvc 직접 생성
//        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
//    }
//
//    @Test
//    @DisplayName("GET /article → 글 목록 페이지 접속 성공")
//    void index() throws Exception {
//        mockMvc.perform(get("/article"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("article/index"));
//    }
//
//    @Test
//    @DisplayName("GET /article/new → 새 글 작성 폼 페이지 출력")
//    void newArticle() throws Exception {
//        mockMvc.perform(get("/article/new"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("article/new"));
//    }
//
//    @Test
//    @DisplayName("POST /article/create → 글 생성 및 리다이렉트")
//    void create() throws Exception {
//        String title = "테스트 제목";
//        String content = "테스트 내용";
//
//        mockMvc.perform(post("/article/create")
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                        .param("title", title)
//                        .param("content", content))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrlPattern("/article/*"));
//
//        Article saved = articleRepository.findAll().get(0);
//        assertThat(saved.getTitle()).isEqualTo(title);
//        assertThat(saved.getContent()).isEqualTo(content);
//    }
//
//
//    @Test
//    @DisplayName("GET /article/{id} → 글 상세 페이지 출력")
//    void show() throws Exception {
//        Article article = articleRepository.save(
//                new Article(null, "상세 테스트 제목", writer, "상세 내용")
//        );
//
//        mockMvc.perform(get("/article/" + article.getId()))
//                .andExpect(status().isOk())
//                .andExpect(view().name("article/show"))
//                .andExpect(model().attributeExists("article"));
//    }
//}
