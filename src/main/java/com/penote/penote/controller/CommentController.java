package com.penote.penote.controller;

import com.penote.penote.dto.CommentDto;
import com.penote.penote.entity.Article;
import com.penote.penote.repository.ArticleRepository;
import com.penote.penote.repository.CommentRepository;
import com.penote.penote.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/article/{articleId}/comments")
    public String create(@PathVariable Long articleId, CommentDto dto) {
        commentService.create(articleId, dto);
        return "redirect:/article/" + articleId;
    }

    @PostMapping("/comments/{id}/update")
    public String update(@PathVariable Long id, CommentDto dto) {
        CommentDto updated = commentService.update(id, dto);
        return "redirect:/article/" + updated.getArticleId();
    }

    @PostMapping("/comments/{id}/delete")
    public String delete(@PathVariable Long id) {
        CommentDto deleted = commentService.delete(id);
        return "redirect:/article/" + deleted.getArticleId();
    }


}

