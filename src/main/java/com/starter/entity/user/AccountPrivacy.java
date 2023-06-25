package com.starter.entity.user;

import lombok.Data;

@Data
public class AccountPrivacy {
    int uid;
    boolean email;
    boolean qq;
    boolean wechat;
    boolean phone;
    boolean blog;
    boolean sex;
}
