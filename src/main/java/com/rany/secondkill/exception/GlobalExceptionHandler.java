package com.rany.secondkill.exception;

import com.rany.secondkill.vo.RespBean;
import com.rany.secondkill.vo.RespBeanEnum;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public RespBean ExceptionHandler(Exception exception) {
        if (exception instanceof GlobalException) {
            GlobalException ex = (GlobalException) exception;
            return RespBean.error(ex.getRespBeanEnum());
        } else if (exception instanceof BindException) {
            BindException ex = (BindException) exception;
            RespBean respBean = RespBean.error(RespBeanEnum.BIND_ERROR);
            respBean.setMessage("参数校验异常" + ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return respBean;
        }
        return RespBean.error(RespBeanEnum.ERROR);
    }

}
