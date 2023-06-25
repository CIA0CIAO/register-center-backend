package com.starter.service.impl;

import com.starter.entity.auth.Account;
import com.starter.entity.user.AccountInfo;
import com.starter.entity.user.AccountPrivacy;
import com.starter.entity.user.AccountUser;
import com.starter.mapper.UserMapper;
import com.starter.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public boolean saveUserInfo(AccountInfo accountInfo) {
        AccountUser account = userMapper.findAccountUserByNameOrEmail(accountInfo.getUsername());
        if (account == null) {
            userMapper.updateUsername(accountInfo.getUsername(), accountInfo.getUid());
        }else if(account.getId() != accountInfo.getUid()){
            return false;
        }
        userMapper.saveInfo(accountInfo);
        return true;
    }

    @Override
    public AccountInfo getUserInfo(int uid) {
        return userMapper.findAccountByUid(uid);
    }

    @Override
    public boolean saveEmail(String email, int uid) {
        AccountUser account = userMapper.findAccountUserByNameOrEmail(email);
        if (account == null) {
            userMapper.updateEmail(email, uid);
        }else if(account.getId() != uid){
            return false;
        }
        return true;
    }

    @Override
    public boolean savePassword(String oldPassword, String newPassword, int uid) {
        Account account = userMapper.findAccountById(uid);
        //将旧密码与数据库密码进行比较
        if (passwordEncoder.matches(oldPassword,account.getPassword())){
            String encoded = passwordEncoder.encode(newPassword);
            userMapper.updatePassword(encoded, uid);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void saveUserPrivacy(AccountPrivacy accountPrivacy) {
        userMapper.savePrivacy(accountPrivacy);
    }

    @Override
    public AccountPrivacy getUserPrivacy(int uid) {
        return userMapper.findPrivacyByUid(uid);
    }
}
