package com.starter.mapper;

import com.starter.entity.auth.Account;
import com.starter.entity.user.AccountInfo;
import com.starter.entity.user.AccountPrivacy;
import com.starter.entity.user.AccountUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
//    根据输入的文本查询用户名或邮箱用于判断账号是否存在，该方法只在登录注册环节使用，与findAccountUserByNameOrEmail区分
    @Select("select * from db_account where username = #{text} or email = #{text}")
    Account findAccountByNameOrEmail(String text);
//    根据输入的id查询用户
    @Select("select * from db_account where id = #{id}")
    Account findAccountById(int id);
//    根据输入的文本查询用户名或邮箱获取用户信息
    @Select("select * from db_account where username = #{text} or email = #{text}")
    AccountUser findAccountUserByNameOrEmail(String text);
//    注册用户
    @Insert("insert into db_account(username,password,email) values(#{username},#{password},#{email})")
    int createAccount(String username, String password, String email);
//    重置密码
    @Update("update db_account set password = #{password} where email = #{email}")
    int resetPasswordByEmail(String password, String email);
//    更新用户信息，当信息为空时插入，当主键id存在时为修改
    @Insert("""
            insert db_account_info (uid,sex,qq,wechat,phone,blog,`desc`)
            values(#{uid},#{sex},#{qq},#{wechat},#{phone},#{blog},#{desc})
            on duplicate key update
            uid = #{uid},sex = #{sex},qq = #{qq},wechat = #{wechat},phone = #{phone},blog = #{blog},`desc` = #{desc}
    """)
    void saveInfo(AccountInfo accountInfo);
//    根据用户uid更新用户名
    @Update("update db_account set username = #{name} where id = #{uid}")
    void updateUsername(String name, int uid);
//    根据用户uid获取用户信息
    @Select("select * from db_account_info,db_account where db_account_info.uid = db_account.id and db_account_info.uid = #{uid}")
    AccountInfo findAccountByUid(int uid);
//    根据用户uid更新邮箱
    @Update("update db_account set email = #{email} where id = #{uid}")
    void updateEmail(String email, int uid);
//    根据用户uid更新密码
    @Update("update db_account set password = #{password} where id = #{uid}")
    void updatePassword(String password, int uid);
//    根据用户uid更新隐私设置信息
    @Insert("""
            insert db_account_privacty (uid,email,qq,wechat,phone,blog,sex)
            values(#{uid},#{email},#{qq},#{wechat},#{phone},#{blog},#{sex})
            on duplicate key update
            uid = #{uid},email = #{email},qq = #{qq},wechat = #{wechat},phone = #{phone},blog = #{blog},sex = #{sex}
    """)
    void savePrivacy(AccountPrivacy privacy);
//    根据用户uid获取隐私设置信息
    @Select("select * from db_account_privacty where uid = #{uid}")
    AccountPrivacy findPrivacyByUid(int uid);
}
