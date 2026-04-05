package com.penote.penote.service;

import com.penote.penote.entity.User;
import com.penote.penote.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // 1. DB에서 사용자 조회
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + userId));

        // 2. 시큐리티가 인식할 수 있도록 ROLE_ 접두사 추가
        // DB에 "GENERAL"이라고 저장되어 있다면 "ROLE_GENERAL"이 됩니다.
        String roleName = String.valueOf(user.getRole());
        if (!roleName.startsWith("ROLE_")) {
            roleName = "ROLE_" + roleName;
        }

        // 3. 스프링 시큐리티 전용 UserDetails 객체 생성 및 반환
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserId())
                .password(user.getUserPassword()) // 암호화된 비밀번호여야 함
                .authorities(Collections.singleton(new SimpleGrantedAuthority(roleName)))
                .build();
    }
}