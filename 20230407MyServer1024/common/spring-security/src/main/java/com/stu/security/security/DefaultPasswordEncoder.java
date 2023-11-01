package com.stu.security.security;



import com.stu.base.utils.MD5;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/******************************
 * 用途说明:
 * 作者姓名: Administrator
 * 创建时间: 2022-09-01 9:39
 ******************************/
@Component
public class DefaultPasswordEncoder implements PasswordEncoder {

    /**
     * 加密密码
     *
     * @param rawPassword
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return MD5.encrypt(rawPassword.toString());
    }

    /**
     * 判断密码是否正确
     *
     * @param rawPassword     the raw password to encode and match
     * @param encodedPassword the encoded password from storage to compare with
     * @return true if the raw password, after encoding, matches the encoded password from
     * storage
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(MD5.encrypt(rawPassword.toString()));
    }
}
