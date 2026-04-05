package com.penote.penote.entity;

import com.penote.penote.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails; // 1. 임포트 확인!

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;

@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name="users")
// 2. 여기에 implements UserDetails 를 추가합니다! (옷 입히기 시작)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name="user_id", unique = true, nullable = false)
    private String userId;

    private String userPassword;
    private String userNickname;
    private BigDecimal userStarBalance;
    private String userProfilePicture;

    public User(Object o, String userId, String userPassword, String userNickname, BigDecimal userStarBalance, String userProfilePicture) {
    }

    // ---------------------------------------------------------
    // 3. 아래는 Spring Security가 요구하는 필수 메서드들입니다 (규격 맞추기)
    // ---------------------------------------------------------

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 우리 Role(General, Admin) 앞에 "ROLE_"을 붙여서 권한을 알려줍니다.
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return this.userPassword; // DB에 저장된 암호화된 비밀번호 리턴
    }

    @Override
    public String getUsername() {
        return this.userId; // 로그인 아이디로 쓸 필드 리턴
    }

    // 아래 4개는 계정의 상태를 묻는 건데, 일단 다 true(정상)로 둡니다.
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}