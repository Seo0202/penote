package com.penote.penote.dto;

import com.penote.penote.entity.Article;
import com.penote.penote.entity.User;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private String title;
    private String content;

}
