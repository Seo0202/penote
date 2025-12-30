package com.penote.penote.repository;

import com.penote.penote.entity.Article;
import com.penote.penote.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Long> {

    List<Article> findByWriter(User writer);
}
