package com.starter.controller;

import com.starter.entity.RestBean;
import com.starter.entity.user.AccountUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @GetMapping("/currentUser")
    public RestBean<AccountUser> getCurrentUser(@SessionAttribute("accountUser") AccountUser accountUser) {
        return RestBean.success(accountUser);
    }
}
