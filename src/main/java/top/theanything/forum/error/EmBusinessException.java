package top.theanything.forum.error;

import lombok.AllArgsConstructor;

/**
 * @author zhou
 * @version 1.0.0
 * @ClassName EmBusinessException.java
 * @Description
 * @createTime 2020年04月13日 20:48:00
 */
@AllArgsConstructor
public enum EmBusinessException implements CommonError {

    //通用错误类型10001
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    UNKNOWN_ERROR(10002,"未知错误"),

    //20000开头为用户信息相关错误定义
    USER_NOT_EXIST(20001,"用户不存在"),
    USER_LOGIN_FAIL(20002,"用户手机号或密码不正确"),
    USER_NOT_LOGIN(20003,"用户还未登陆")
    ;



    private int code;
    private String msg;


    @Override
    public int getErrCode() {
        return code;
    }

    @Override
    public String getErrMsg() {
        return msg;
    }
}
