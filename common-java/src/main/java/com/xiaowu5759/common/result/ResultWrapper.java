package com.xiaowu5759.common.result;


import com.xiaowu5759.common.enums.ErrorCodeEnum;
import com.xiaowu5759.common.exception.BusinessException;

/**
 * 统一返回结果-包装器
 *
 * @author xiaowu
 * @date 2020/7/7 10:35
 */
public class ResultWrapper {

    public static ResultVO ok(){
        return new ResultVO(ErrorCodeEnum.SUCCESS);
    }

    public static <T> ResultVO<T> ok(T data){
        return new ResultVO<T>(data);
    }

    public static ResultVO fail(){
        return new ResultVO(ErrorCodeEnum.SYSTEM_ERROR);
    }

    public static <T> ResultVO<T> fail(T data){
        return new ResultVO<T>(ErrorCodeEnum.SYSTEM_ERROR, data);
    }

    public static ResultVO wrap(ErrorCodeEnum errorCodeEnum){
        return new ResultVO(errorCodeEnum);
    }

    public static ResultVO wrap(BusinessException e){
        return new ResultVO(e.getErrCode(), e.getErrMsg());
    }

    public static <T> ResultVO<T> wrap(ErrorCodeEnum errorCodeEnum, T data){
        return new ResultVO<T>(errorCodeEnum, data);
    }

    public static <T> ResultVO<T> wrap(BusinessException e, T data){
        return new ResultVO<T>(e.getErrCode(), e.getErrMsg(), data);
    }

}
