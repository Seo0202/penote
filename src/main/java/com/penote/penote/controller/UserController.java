package com.penote.penote.controller;

import com.penote.penote.Role;
import com.penote.penote.dto.UserDto;
import com.penote.penote.entity.User;
import com.penote.penote.repository.ArticleRepository;
import com.penote.penote.repository.UserRepository;
import com.penote.penote.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    @GetMapping("/user/account")
    public String account() {
        return "user/account";
    }

    @PostMapping("/user/accountMade")
    public String accountMade(UserDto dto, HttpSession session) {
        User user = new User();
        user.setUserId(dto.getUserId());
        user.setUserPassword(passwordEncoder.encode(dto.getUserPassword()));
        user.setUserNickname(dto.getUserNickname());
        user.setRole(Role.GENERAL);
        userRepository.save(user);
        session.setAttribute("loginUser", user);

        return "user/accountWelcome";
    }

    @GetMapping("/user/loginWelcome")
    public String loginWelcome(Model model, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser", loginUser);
        return "user/loginWelcome";
    }

    @GetMapping("/login") // 브라우저 주소창에 /login 입력 시
    public String loginPage() {
        return "user/login"; // templates 폴더 안의 login.html을 찾아서 보여줌
    }

    @GetMapping("/article/list")
    public String list(Model model) {
        model.addAttribute("articles", articleRepository.findAll());
        return "article/list";
    }
}