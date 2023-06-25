package com.starter.controller;

import com.starter.entity.RestBean;
import com.starter.service.AuthorizeService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthorizeController {
    private final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$";
    private final String USERNAME_REGEX = "^[a-zA-Z0-9一-龥]+$";
    @Resource
    AuthorizeService authorizeService;

    @PostMapping("/validate-register-email")
    public RestBean<String> validateRegisterEmail(@Pattern(regexp = EMAIL_REGEX) @RequestParam("email") String email,
                                          HttpSession session) {
        String description = authorizeService.sendValidateEmail(email, session.getId(),false);
        if(description == null){
            return RestBean.success("邮件已发送，请注意查收");
        }else{
            return RestBean.failure(400, description);
        }

    }
    @PostMapping("/validate-reset-email")
    public RestBean<String> validateResetEmail(@Pattern(regexp = EMAIL_REGEX) @RequestParam("email") String email,
                                          HttpSession session) {
        String description = authorizeService.sendValidateEmail(email, session.getId(),true);
        if(description == null){
            return RestBean.success("邮件已发送，请注意查收");
        }else{
            return RestBean.failure(400, description);
        }

    }
    @PostMapping("/register")
    public RestBean<String> registerUser(@Pattern(regexp = USERNAME_REGEX) @Length(min = 2, max = 8) @RequestParam("username") String username,
                                     @Length(min = 6, max = 16) @RequestParam("password") String password,
                                     @Pattern(regexp = EMAIL_REGEX) @RequestParam("email") String email,
                                     @Length(min = 6, max = 6) @RequestParam("code") String code, HttpSession session) {
        String description = authorizeService.vaildateAndRegister(username, password, email, code, session.getId());
        if(description == null){
            return RestBean.success("注册成功");
        }else{
            return RestBean.failure(400, description);
        }
    }

    @PostMapping("/start-reset")
    public RestBean<String> startRest(@Pattern(regexp = EMAIL_REGEX) @RequestParam("email") String email,
                                      @Length(min = 6, max = 6) @RequestParam("code") String code, HttpSession session){
        String description = authorizeService.validateOnly(email,code,session.getId());
        if(description == null){
            session.setAttribute("reset-password",email);
            return RestBean.success();
        }else {
            return RestBean.failure(400,description);
        }
    }

    @PostMapping("/do-reset")
    public RestBean<String> resetPassword(@Length(min = 6, max = 16) @RequestParam("password") String password,
                                          HttpSession session){
        String email = (String) session.getAttribute("reset-password");
        if(email == null) {
            return RestBean.failure(401, "请先完成邮箱验证");
        } else if(authorizeService.resetPassword(password, email)){
            session.removeAttribute("reset-password");
            return RestBean.success("密码重置成功");
        } else {
            return RestBean.failure(500, "内部错误，请联系管理员");
        }
    }
}
