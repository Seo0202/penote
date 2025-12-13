package com.penote.penote.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name="user_id", unique = true, nullable = false)
    private String userId;
    private String userPassword;
    private String userNickname;
    private BigDecimal userStarBalance;
    private String userProfilePicture;

    public Object getPassword() {
        return userPassword;
    }
}
