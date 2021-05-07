package com.xiaowu5759.common.exception;


import com.xiaowu5759.common.enums.ErrorCodeEnum;

/**
 * 调用第三方服务出错
 *
 * @author xiaowu
 * @date 2020/7/7 16:29
 */
public class ServiceException extends BusinessException{
    public ServiceException(){
        super(ErrorCodeEnum.SERVICE_ERROR);
    }

    public ServiceException(ErrorCodeEnum errorCodeEnum){
        super(errorCodeEnum);
    }
}
