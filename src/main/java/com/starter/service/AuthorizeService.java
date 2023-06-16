package com.starter.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthorizeService extends UserDetailsService {
    /**
     * 为注册账号提供邮件校验
     * @param email
     * @param sessionId
     * @param hasAccount
     * @return
     */
    String sendValidateEmail(String email, String sessionId, boolean hasAccount);

    /**
     * 校验验证码并注册
     * @param username
     * @param password
     * @param email
     * @param code
     * @param sessionId
     * @return
     */
    String vaildateAndRegister(String username, String password, String email, String code, String sessionId);

    /**
     * 为重置密码提供邮件校验
     * @param email
     * @param code
     * @param sessionId
     * @return
     */
    String validateOnly(String email, String code, String sessionId);

    /**
     * 重置密码
     * @param password
     * @param email
     * @return
     */
    boolean resetPassword(String password, String email);
}
