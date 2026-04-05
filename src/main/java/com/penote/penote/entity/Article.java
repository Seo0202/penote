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
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User writer;

    @Lob
    private String content;
    public UserDto getUserDto() {
        return UserDto.fromEntity(this.writer);
    }

    public User getWriter() {
        return this.writer; // DTO로 바꾸지 말고 엔티티 그대로 리턴!
    }
}
