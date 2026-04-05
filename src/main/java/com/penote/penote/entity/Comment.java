package com.penote.penote.entity;

import com.penote.penote.dto.UserDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article; //연관관계의 주인
    @ManyToOne
    @JoinColumn(name = "user_id") // DB에 user_id 컬럼이 생깁니다.
    private User user;
    private String nickname;
    private String body;

    public Comment(Object o, Article article, String nickname, String body) {
        this.user = user;
        this.article = article;
        this.nickname = nickname;
        this.body = body;
    }
}
