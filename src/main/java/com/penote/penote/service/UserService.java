package com.penote.penote.service;

import com.penote.penote.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public boolean match(String rawPassword, User user) {
        if (rawPassword == null || user == null)
            return false;

        return passwordEncoder.matches(
                rawPassword,           // 사용자가 입력한 비밀번호 (평문)
                (String) user.getUserPassword() // DB에 저장된 암호화된 비밀번호
        );
    }
}
