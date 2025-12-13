package com.penote.penote.controller;

import com.penote.penote.dto.UserDto;
import com.penote.penote.entity.User;
import com.penote.penote.repository.ArticleRepository;
import com.penote.penote.repository.UserRepository;
import com.penote.penote.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.file.attribute.UserPrincipalLookupService;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/user/account")
    public String account()
    { return "user/account"; }

    @PostMapping("/user/accountMade")
    public String accountMade(UserDto dto) {
        User user = new User();
        user.setUserId(dto.getUserId());
        user.setUserPassword(dto.getUserPassword());
        user.setUserNickname(dto.getUserNickname());

        userRepository.save(user);
        return "user/accountWelcome";
    }

    @PostMapping("/user/login")
    public String login(@RequestParam String userId,
                        @RequestParam String userPassword) {

        User user = userRepository.findByUserId(userId).orElse(null);
        if (user == null)
            return "redirect:/error/error.html";

        if(!userService.distinct(userPassword, user))
            return "redirect:/error/error.html";

        return "article/list";


    }

    @GetMapping("/article/list")
    public String list(Model model) {
        model.addAttribute("articles", articleRepository.findAll());
        return "article/list";
    }

}
