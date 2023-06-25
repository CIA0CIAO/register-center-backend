package com.starter.service;

import com.starter.entity.user.AccountInfo;
import com.starter.entity.user.AccountPrivacy;

public interface UserService {
    /**
     * 保存用户信息且当信息存在时为修改信息
     * @param accountInfo
     */
    boolean saveUserInfo(AccountInfo accountInfo);

    /**
     * 通过用户uid查询用户的个人信息
     * @param uid
     * @return
     */
    AccountInfo getUserInfo(int uid);

    /**
     * 修改并保存邮件
     * @param email
     * @param uid
     * @return
     */
    boolean saveEmail(String email, int uid);

    /**
     * 修改并保存密码
     * @param oldPassword
     * @param newPassword
     * @param uid
     * @return
     */
    boolean savePassword(String oldPassword,String newPassword, int uid);

    /**
     * 保存用户隐私信息设置
     * @param accountPrivacy
     */
    void saveUserPrivacy(AccountPrivacy accountPrivacy);

    /**
     * 通过uid获取个人隐私设置信息
     * @param uid
     * @return
     */
    AccountPrivacy getUserPrivacy(int uid);
}
