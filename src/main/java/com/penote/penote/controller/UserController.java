package com.penote.penote.controller;

import com.penote.penote.dto.UserDto;
import com.penote.penote.entity.User;
import com.penote.penote.repository.ArticleRepository;
import com.penote.penote.repository.UserRepository;
import com.penote.penote.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // 추가!

    @GetMapping("/user/account")
    public String account() {
        return "user/account";
    }

    @PostMapping("/user/accountMade")
    public String accountMade(UserDto dto, HttpSession session) {
        User user = new User();
        user.setUserId(dto.getUserId());
        user.setUserPassword(passwordEncoder.encode(dto.getUserPassword()));  // ✅ 암호화!
        user.setUserNickname(dto.getUserNickname());

        userRepository.save(user);

        session.setAttribute("loginUser", user);  // 세션에 저장
        return "user/accountWelcome";
    }

    @PostMapping("/user/login")
    public String login(@RequestParam String userId,
                        @RequestParam String userPassword, HttpServletRequest request) {

        User user = userRepository.findByUserId(userId).orElse(null);
        if (user == null)
            return "errors/login_error";

        if (!userService.match(userPassword, user))
            return "errors/login_error";

        HttpSession session = request.getSession();
        session.setAttribute("loginUser", user);

        return "redirect:/user/loginWelcome";
    }

    @GetMapping("/user/loginWelcome")
    public String loginWelcome() {
        return "user/loginWelcome";
    }

    @GetMapping("/article/list")
    public String list(Model model) {
        model.addAttribute("articles", articleRepository.findAll());
        return "article/list";
    }
}