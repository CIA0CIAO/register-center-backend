package com.starter.controller;

import com.starter.entity.RestBean;
import com.starter.entity.user.AccountInfo;
import com.starter.entity.user.AccountPrivacy;
import com.starter.entity.user.AccountUser;
import com.starter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$";
    @Resource
    UserService userService;

    @GetMapping("/currentUser")
    public RestBean<AccountUser> getCurrentUser(@SessionAttribute("accountUser") AccountUser accountUser) {
        return RestBean.success(accountUser);
    }

    @PostMapping("/save-info")
    public RestBean<String> saveUserInfo(@RequestBody @Validated AccountInfo accountInfo,
                                         @SessionAttribute("accountUser") AccountUser accountUser) {
        accountInfo.setUid(accountUser.getId());
        if (userService.saveUserInfo(accountInfo)) {
            //将session中存储的用户名更新
            accountUser.setUsername(accountInfo.getUsername());
            return RestBean.success();
        } else {
            return RestBean.failure(400, "用户名已经其他用户使用");
        }
    }

    @GetMapping("/get-info")
    public RestBean<AccountInfo> getUserInfo(@SessionAttribute("accountUser") AccountUser accountUser) {
        return RestBean.success(userService.getUserInfo(accountUser.getId()));
    }

    @PostMapping("/save-email")
    public RestBean<String> getUserInfo(@Pattern(regexp = EMAIL_REGEX) @RequestParam("email") String email,
                                        @SessionAttribute("accountUser") AccountUser accountUser) {
        if (userService.saveEmail(email, accountUser.getId())) {
            //将session中存储的邮件地址更新
            accountUser.setEmail(email);
            return RestBean.success();
        } else {
            return RestBean.failure(400, "邮件地址已经其他用户使用");
        }
    }

    @GetMapping("/get-email")
    public RestBean<String> getEmail(@SessionAttribute("accountUser") AccountUser accountUser) {
        return RestBean.success(accountUser.getEmail());
    }
    @PostMapping("/save-password")
    public RestBean<String> savePassword(@Length(min = 6, max = 16) @RequestParam("oldPassword") String oldPassword,
                                         @Length(min = 6, max = 16) @RequestParam("newPassword") String newPassword,
                                         @SessionAttribute("accountUser") AccountUser accountUser) {
        if (userService.savePassword(oldPassword, newPassword, accountUser.getId())) {
            return RestBean.success();
        }else {
            return RestBean.failure(400,"原密码错误，修改失败");
        }
    }
    @PostMapping("/save-privacy")
    public RestBean<Void> savePrivacy(@RequestBody AccountPrivacy privacy,
                                      @SessionAttribute("accountUser") AccountUser accountUser) {
        privacy.setUid(accountUser.getId());
        userService.saveUserPrivacy(privacy);
        return RestBean.success();
    }
    @GetMapping("/get-privacy")
    public RestBean<AccountPrivacy> getUserPrivacy(@SessionAttribute("accountUser") AccountUser accountUser) {
        return RestBean.success(userService.getUserPrivacy(accountUser.getId()));
    }

}
