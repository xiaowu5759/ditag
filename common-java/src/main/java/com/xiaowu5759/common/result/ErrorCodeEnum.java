package com.xiaowu5759.common.result;

/**
 * 错误码
 * 堆栈（stack_trace）、错误信息(error_message)、错误码（error_code）、提示信息（user_tip）
 * 参考java开发手册
 *
 * @author xiaowu
 * @date 2020/7/7 10:10
 */
public enum ErrorCodeEnum {
    /* 成功 */
    SUCCESS("00000", "一切ok"),

    /* 一级宏观码 用户 */
    USER_ERROR("A0001", "用户端错误"),
    USER_REGISTER_ERROR("A0100", "用户注册错误"),

    /* 一级宏观码 当前系统 */
    SYSTEM_ERROR("B0001", "系统执行出错"),

    /* 一级宏观码 第三方服务 */
    SERVICE_ERROR("C0001", "调用第三方服务出错");

    private String errCode;

    private String errMsg;

    // 枚举类构造函数
    ErrorCodeEnum(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
