package com.penote.penote.controller;

import com.penote.penote.dto.CommentDto;
import com.penote.penote.entity.Article;
import com.penote.penote.repository.ArticleRepository;
import com.penote.penote.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentService commentService;

    // 글 목록
    @GetMapping("/article")
    public String index(Model model) {
        List<Article> articleList = articleRepository.findAll();
        model.addAttribute("articleList", articleList);
        return "article/index";
    }

    // 새 글 작성 폼
    @GetMapping("/article/new")
    public String newArticle() {
        return "article/new";
    }

    // 글 작성 처리
    @PostMapping("/article/create")
    public String create(Article article) {
        Article saved = articleRepository.save(article);
        return "redirect:/article/" + saved.getId();
    }

    // 글 상세 보기 (댓글 목록 포함)
    @GetMapping("/article/{id}")
    public String show(@PathVariable Long id, Model model) {
        // 글 조회
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 없습니다."));

        // 댓글 목록 조회
        List<CommentDto> comments = commentService.readAll(id);

        // Model에 담기
        model.addAttribute("article", article);
        model.addAttribute("comments", comments);

        return "article/show";
    }
}
