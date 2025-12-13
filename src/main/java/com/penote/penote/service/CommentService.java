package com.penote.penote.service;

import com.penote.penote.dto.CommentDto;
import com.penote.penote.entity.Article;
import com.penote.penote.entity.Comment;
import com.penote.penote.repository.ArticleRepository;
import com.penote.penote.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public CommentDto create(Long articleId, CommentDto dto) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다."));

        if (dto.getId() != null) {
            throw new IllegalArgumentException("댓글 생성 실패! 댓글 id는 없어야 합니다.");
        }
        Comment comment = new Comment(null, article, dto.getNickname(), dto.getBody());
        Comment created =  commentRepository.save(comment);
        return CommentDto.createCommentDto(created);
    }

    public CommentDto read(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 읽기 실패! 해당 댓글이 없습니다."));
        return CommentDto.createCommentDto(comment);
    }

    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 댓글이 없습니다."));

        if (dto.getId() != null && !dto.getId().equals(id)) {
            throw new IllegalArgumentException("댓글 수정 실패! id가 일치하지 않습니다.");
        }

        target.setNickname(dto.getNickname());
        target.setBody(dto.getBody());

        Comment updated = commentRepository.save(target);

        return CommentDto.createCommentDto(updated);
    }

    @Transactional
    public CommentDto delete(Long id) {
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 댓글이 없습니다."));

        commentRepository.delete(target);
        return CommentDto.createCommentDto(target);
    }

    public List<CommentDto> readAll(Long articleId) {
        List<Comment> comments = commentRepository.findByArticleId(articleId);

        List<CommentDto> dtos = new ArrayList<>();
        for (Comment comment : comments) {
            dtos.add(CommentDto.createCommentDto(comment));
        }

        return dtos;
    }

}
