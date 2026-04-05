package com.penote.penote.controller;

import com.penote.penote.dto.ArticleDto;
import com.penote.penote.dto.CommentDto;
import com.penote.penote.entity.Article;
import com.penote.penote.entity.User;
import com.penote.penote.repository.ArticleRepository;
import com.penote.penote.repository.UserRepository;
import com.penote.penote.service.ArticleService;
import com.penote.penote.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CommentService commentService;

    // 생성자 주입 (추천 방식)
    public ArticleController(ArticleService articleService,
                             UserRepository userRepository,
                             ArticleRepository articleRepository,
                             CommentService commentService) {
        this.articleService = articleService;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.commentService = commentService;
    }

    @GetMapping("/")
    public String home() {
        return "article/index";
    }

    // 모든 글 목록
    @GetMapping("/article")
    public String index(Model model) {
        List<Article> articleList = articleRepository.findAll();
        model.addAttribute("articleList", articleList);
        return "article/list";
    }

    // 내 글 목록
    @GetMapping("/article/my")
    public String myList(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) return "redirect:/login";

        User loginUser = userRepository.findByUserId(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        List<Article> articleList = articleRepository.findByWriter(Optional.ofNullable(loginUser));
        model.addAttribute("myArticleList", articleList);
        return "article/list";
    }

    // 새 글 작성 폼
    @GetMapping("/article/new")
    public String newArticle() {
        return "article/new";
    }

    // 글 작성 처리 (수정됨)
    @PostMapping("/article/create")
    public String create(Article article, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) return "redirect:/login";

        User loginUser = userRepository.findByUserId(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        article.setWriter(loginUser); // 엔티티 타입의 유저를 세팅
        Article saved = articleRepository.save(article);
        return "redirect:/article/" + saved.getId();
    }

    // 글 상세 보기
    @GetMapping("/article/{id}")
    public String show(@PathVariable Long id, Model model) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 없습니다."));

        List<CommentDto> comments = commentService.readAll(id);

        model.addAttribute("article", article);
        model.addAttribute("comments", comments);
        return "article/show";
    }

    // 글 수정 폼
    @GetMapping("/article/{id}/edit")
    public String edit(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails,
                       Model model, RedirectAttributes rttr) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 없습니다."));
        if (userDetails == null || !article.getWriter().getUserId().equals(userDetails.getUsername())) {
            rttr.addFlashAttribute("msg", "본인의 글만 수정할 수 있습니다.");
            return "redirect:/article/" + id; // 상세 페이지로 튕겨내기
        }
        model.addAttribute("article", article);
        return "article/edit";
    }

    // 글 수정 처리 (수정됨)
    @PostMapping("/article/{id}/update")
    public String update(@PathVariable Long id, ArticleDto dto,
                         @AuthenticationPrincipal UserDetails userDetails,
                         org.springframework.web.servlet.mvc.support.RedirectAttributes rttr) {
        if (userDetails == null) return "redirect:/login";


        try {
            User loginUser = userRepository.findByUserId(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
            // 삭제 실행
            articleService.update(id, dto, loginUser);


            rttr.addFlashAttribute("msg", "수정되었습니다.");
            return "redirect:/article";
        } catch (Exception e) {
            rttr.addFlashAttribute("msg", "수정 실패: " + e.getMessage());
            return "redirect:/article/" + id; // 실패 시 상세페이지로 이동하며 팝업!
        }

    }


//    // 글 삭제 처리 (수정됨)
//    @GetMapping("/article/{id}/delete")
//    public String delete(@PathVariable Long id,
//                         @AuthenticationPrincipal UserDetails userDetails) {
//        if (userDetails == null) return "redirect:/login";
//
//        User loginUser = userRepository.findByUserId(userDetails.getUsername())
//                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
//
//        articleService.delete(id, loginUser);
//        return "redirect:/article";
//    }

    @GetMapping("/article/{id}/delete")
    public String delete(@PathVariable Long id,
                         @AuthenticationPrincipal UserDetails userDetails,
                         org.springframework.web.servlet.mvc.support.RedirectAttributes rttr) {

        if (userDetails == null) return "redirect:/login";

        try {
            User loginUser = userRepository.findByUserId(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

            // 삭제 실행
            articleService.delete(id, loginUser);


            rttr.addFlashAttribute("msg", "삭제되었습니다.");
            return "redirect:/article";
        } catch (Exception e) {
            rttr.addFlashAttribute("msg", "삭제 실패: " + e.getMessage());
            return "redirect:/article/" + id; // 실패 시 상세페이지로 이동하며 팝업!
        }
    }

    @GetMapping("/loginWelcome")
    public String loginWelcome() {
        return "user/loginWelcome";
    }
}