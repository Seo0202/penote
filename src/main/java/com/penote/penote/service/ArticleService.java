package com.penote.penote.service;

import com.penote.penote.Role;
import com.penote.penote.dto.ArticleDto;
import com.penote.penote.dto.CommentDto;
import com.penote.penote.entity.Article;
import com.penote.penote.entity.Comment;
import com.penote.penote.entity.User;
import com.penote.penote.repository.ArticleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
//    @Transactional
//    public ArticleDto update(Long id, ArticleDto dto) {
//        Article target = articleRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("글 수정 실패! 댓글이 없습니다."));
//
//        if (dto.getId() != null && !dto.getId().equals(id)) {
//            throw new IllegalArgumentException("글 수정 실패! id가 일치하지 않습니다.");
//        }
//
//        target.setTitle(dto.getTitle());
//        target.setContent(dto.getContent());
//        Article updated = articleRepository.save(target);
//
//        return ArticleDto.createArticleDto(updated);
//    }
//
//    @Transactional
//    public ArticleDto delete(Long id) {
//        Article target = articleRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("글 삭제 실패! 글이 없습니다."));
//
//        articleRepository.delete(target);
//        return ArticleDto.createArticleDto(target);
//    }

    @Transactional
    public ArticleDto update(Long id, ArticleDto dto, User currentUser) { // 1. 현재 유저 추가
        Article target = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("글 수정 실패! 해당 글이 없습니다."));

        // [권한 체크] 작성자 본인이거나 관리자인지 확인
        // Article 엔티티에 User 필드가 있다고 가정합니다 (예: target.getUser())
        if (!target.getWriter().getUserId().equals(currentUser.getUserId()) && currentUser.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("글 수정 권한이 없습니다.");
        }

        target.setTitle(dto.getTitle());
        target.setContent(dto.getContent());
        // JPA의 변경 감지(Dirty Checking) 덕분에 save()를 명시적으로 안 해도 되지만, 명확성을 위해 둡니다.

        Article updated = articleRepository.save(target);

        return ArticleDto.createArticleDto(updated);
    }

    @Transactional
    public ArticleDto delete(Long id, User currentUser) { // 2. 현재 유저 추가
        Article target = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("글 삭제 실패! 글이 없습니다."));

        // [권한 체크] 작성자 본인이거나 관리자인지 확인
        if (!target.getWriter().getUserId().equals(currentUser.getUserId()) && currentUser.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("글 삭제 권한이 없습니다.");
        }

        articleRepository.delete(target);
        return ArticleDto.createArticleDto(target);
    }
}

