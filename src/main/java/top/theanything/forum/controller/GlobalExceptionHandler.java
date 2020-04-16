package top.theanything.forum.controller;

import java.util.HashMap;
import java.util.Map;


import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import top.theanything.forum.error.BusinessException;
import top.theanything.forum.error.EmBusinessException;
import top.theanything.forum.response.CommonReturnType;
/**
 * @title
 * @description
 * @author  ZB
 * @updateTime 2020/4/16 16:56
 * @throws
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler{

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonReturnType doError(Exception ex) {

        Map<String,Object> responseData = new HashMap<>();
        if( ex instanceof BusinessException) {
            BusinessException businessException = (BusinessException) ex;
            responseData.put("errCode", businessException.getErrCode());
            responseData.put("errMsg", businessException.getErrMsg());
        }else if(ex instanceof NoHandlerFoundException) {
            responseData.put("errCode", EmBusinessException.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg", "没有找到对应的访问路径");
        }else if(ex instanceof BindException){  //Validated 参数不对
            responseData.put("errCode", EmBusinessException.PARAMETER_VALIDATION_ERROR.getErrCode());
            responseData.put("errMsg", "想来搞破坏?");
        }else{
            responseData.put("errCode", EmBusinessException.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg",EmBusinessException.UNKNOWN_ERROR.getErrMsg());
        }
        return CommonReturnType.create(responseData,"fail");
    }
}
