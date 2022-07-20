package com.xiaowu5759.common.exception;


import com.xiaowu5759.common.result.ErrorCodeEnum;

/**
 * 系统执行出错
 *
 * @author xiaowu
 * @date 2020/7/7 16:28
 */
public class SystemException extends BusinessException {
    public SystemException(){
        super(ErrorCodeEnum.SYSTEM_ERROR);
    }

    public SystemException(ErrorCodeEnum errorCodeEnum){
        super(errorCodeEnum);
    }
}
