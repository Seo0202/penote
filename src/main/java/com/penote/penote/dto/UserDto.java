package com.penote.penote.dto;

import com.penote.penote.entity.Article;
import com.penote.penote.entity.Comment;
import com.penote.penote.entity.User;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long Id;
    private String userId;
    private String userPassword;
    private String userNickname;
    private BigDecimal userStarBalance;
    private String userProfilePicture;


    public User toEntity() {
        return new User(null, userId, userPassword, userNickname,
                userStarBalance, userProfilePicture);
    }


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public BigDecimal getUserStarBalance() {
        return userStarBalance;
    }

    public void setUserStarBalance(BigDecimal userStarBalance) {
        this.userStarBalance = userStarBalance;
    }

    public String getUserProfilePicture() {
        return userProfilePicture;
    }

    public void setUserProfilePicture(String userProfilePicture) {
        this.userProfilePicture = userProfilePicture;
    }
}
