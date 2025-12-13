//package com.penote.penote.controller;
//
//import com.penote.penote.entity.Article;
//import com.penote.penote.dto.ArticleForm;
//import com.penote.penote.repository.ArticleRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import java.util.List;
//
//@Controller
//@RequiredArgsConstructor
//public class ArticleController {
//    private final ArticleRepository articleRepository;
//
//    @GetMapping("/article/new")
//    public String newArticleForm() {
//        return "article/new";
//    }
//
//    @PostMapping("/article/create")
//    public String createArticleForm(ArticleForm form) {
//        Article saved = articleRepository.save(form.toEntity());
//        return "redirect:/article/" + saved.getId();
//    }
//
//    @GetMapping("/article/{id}")
//    public String show(@PathVariable Long id, Model model) {
//        Article article = articleRepository.findById(id).orElse(null);
//        model.addAttribute("article", article);
//        return "article/show";
//    }
//
//    @GetMapping("/article")
//    public String index(Model model) {
//        List<Article> articleList = articleRepository.findAll();
//        model.addAttribute("articleList", articleList);
//        return "article/index";
//    }
//}
