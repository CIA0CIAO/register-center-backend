package com.starter.service.impl;

import com.starter.entity.auth.Account;
import com.starter.mapper.UserMapper;
import com.starter.service.AuthorizeService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class AuthorizeServiceImpl implements AuthorizeService {
    @Value("${spring.mail.username}")
    String provider;
    @Resource
    UserMapper userMapper;
    @Resource
    MailSender mailSender;
    @Resource
    StringRedisTemplate template;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) {
            throw new UsernameNotFoundException("用户名不能为空");
        }
        Account account = userMapper.findAccountByNameOrEmail(username);
        if (account == null) {
            throw new UsernameNotFoundException("用户名或密码不正确");
        }
        return User
                .withUsername(account.getUsername())
                .password(account.getPassword())
                .roles("USER")
                .build();
    }

    @Override
    public String sendValidateEmail(String email, String sessionId, boolean hasAccount) {
        String key = "email:" + sessionId + ":" + email+ ":" + hasAccount;
        if (Boolean.TRUE.equals(template.opsForValue().get(key))) {
            Long expire = Optional.ofNullable(template.getExpire(key, TimeUnit.SECONDS)).orElse(0L);
            if (expire > 120) {
                return "请求频繁，请稍后再试";
            }
        }
        Account account = userMapper.findAccountByNameOrEmail(email);
        if(hasAccount && account == null){
            return "没有该邮件地址的账号";
        }
        if (!hasAccount && account != null) {
            return "该邮箱已被其他用户注册";
        }
//       1.生成相应的验证码
        Random random = new Random();
        int code = random.nextInt(899999) + 100000;
//       2.发送验证码到指定邮箱（过期时间三分钟）
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(provider);
        message.setTo(email);
        message.setSubject("ニコニコ验证码");
        message.setText("您的验证码为：" + code + "。三分钟内有效，请及时完成注册!如果不是本人操作,请忽略");
        try {
            mailSender.send(message);
//          3.把邮箱和对应的验证码存入redis
            template.opsForValue().set(key, String.valueOf(code), 3, TimeUnit.MINUTES);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return "邮件发送失败，请检测邮件地址是否有效";
        }
    }

    @Override
    public String vaildateAndRegister(String username, String password, String email, String code, String sessionId) {
        String key = "email:" + sessionId + ":" + email + ":false";
        if (Boolean.TRUE.equals(template.hasKey(key))) {
            //用户注册时，从redis里面取出键值对，验证是否一致
            String flag = template.opsForValue().get(key);
            if (flag == null) {
                return "验证码失效，请重新请求";
            }
            if (flag.equals(code)) {
                Account account = userMapper.findAccountByNameOrEmail(username);
                if(account != null) {
                    return "此用户名已被注册，请更换用户名";
                }
                template.delete(key);
                password = passwordEncoder.encode(password);
                if (userMapper.createAccount(username, password, email) > 0) {
                    return null;
                } else {
                    return "内部错误，请联系管理员";
                }
            } else {
                return "验证码错误，请检查后再提交";
            }
        } else {
            return "请先请求一封邮件";
        }
    }

    @Override
    public String validateOnly(String email, String code, String sessionId) {
        String key = "email:" + sessionId + ":" + email + ":true";
        if(Boolean.TRUE.equals(template.hasKey(key))) {
            String flag = template.opsForValue().get(key);
            if(flag == null) return "验证码失效，请重新请求";
            if(flag.equals(code)) {
                template.delete(key);
                return null;
            } else {
                return "验证码错误，请检查后再提交";
            }
        } else {
            return "请先请求一封验证码邮件";
        }
    }

    @Override
    public boolean resetPassword(String password, String email) {
        String encodedPassword = passwordEncoder.encode(password);
        int flag = userMapper.resetPasswordByEmail(encodedPassword, email);
        return flag > 0;
    }
}
