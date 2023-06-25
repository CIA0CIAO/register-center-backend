package com.starter.entity.user;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AccountInfo {
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9一-龥]+$";
    int uid;
    @Pattern(regexp = USERNAME_REGEX)
    @Length(min = 2, max = 11)
    String username;
    String sex;
    @Length(max = 11)
    String phone;
    @Length(max = 11)
    String qq;
    @Length(max = 30)
    String wechat;
    @Length(max = 50)
    String blog;
    @Length(max = 500)
    String desc;
}
