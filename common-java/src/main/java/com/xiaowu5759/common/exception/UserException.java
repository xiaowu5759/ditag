package com.xiaowu5759.common.exception;


import com.xiaowu5759.common.result.ErrorCodeEnum;

/**
 * 用户端错误
 *
 * @author xiaowu
 * @date 2020/7/7 15:09
 */
public class UserException extends BusinessException{
    // 构造函数
    public UserException(){
        super(ErrorCodeEnum.USER_ERROR);
    }

    public UserException(ErrorCodeEnum errorCodeEnum){
        super(errorCodeEnum);
    }

}
