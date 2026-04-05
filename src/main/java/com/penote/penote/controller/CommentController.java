package com.penote.penote.controller;

import com.penote.penote.dto.CommentDto;
import com.penote.penote.entity.Article;
import com.penote.penote.entity.User;
import com.penote.penote.repository.ArticleRepository;
import com.penote.penote.repository.CommentRepository;
import com.penote.penote.repository.UserRepository;
import com.penote.penote.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.RequestToViewNameTranslator;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class CommentController {
    private final UserRepository userRepository;
    private final CommentService commentService;
    private final RequestToViewNameTranslator requestToViewNameTranslator;

    @PostMapping("/article/{articleId}/comments")
    public String create(@PathVariable Long articleId, CommentDto dto) {
        commentService.create(articleId, dto);
        return "redirect:/article/" + articleId;
    }

    @PostMapping("/comments/{id}/update")
    public String update(@PathVariable Long id, CommentDto dto,
                         @AuthenticationPrincipal UserDetails userDetails,
                         org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        if(userDetails == null) {
            return "redirect:/login";
        }
        try{
            User loginUser = userRepository.findByUserId
                    (userDetails.getUsername()).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
            commentService.update(id, dto, loginUser);

            CommentDto updated = commentService.update(id, dto, loginUser);
            redirectAttributes.addFlashAttribute("msg", "수정되었습니다.");
            return "redirect:/article/" + updated.getArticleId();

        }catch (Exception e){
            redirectAttributes.addFlashAttribute("msg", "수정 불가합니다.");
            return "redirect:/article";
        }
    }

    @PostMapping("/comments/{id}/delete")
    public String delete(@PathVariable Long id, CommentDto dto,
                         @AuthenticationPrincipal UserDetails userDetails,
                         org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        if(userDetails == null) {
            return "redirect:/login";
        }
        try{
            User loginUser = userRepository.findByUserId(userDetails.getUsername()).orElseThrow(()
            -> new RuntimeException("유저를 찾을 수 없습니다."));
            commentService.delete(id, loginUser);

            CommentDto deleted = commentService.delete(id, loginUser);
            redirectAttributes.addFlashAttribute("msg", "삭제되었습니다.");
            return "redirect:/article/" + deleted.getArticleId();

        }catch (Exception e){
            redirectAttributes.addFlashAttribute("msg", "삭제 불가능합니다.");
            return "redirect:/article";
        }
    }

}

