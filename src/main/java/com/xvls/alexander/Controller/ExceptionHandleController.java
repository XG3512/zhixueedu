package com.xvls.alexander.Controller;

import com.xvls.alexander.utils.SystemResponse;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * shiro注解的异常抛出处理
 */
@CrossOrigin
@RestController
@ControllerAdvice
public class ExceptionHandleController {

    /**
     * 未经授权
     * @param ex
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    public Object handleShiroException(Exception ex){
        return SystemResponse.unauthz();
    }

    /**
     * 授权异常
     * @param ex
     * @return
     */
    @ExceptionHandler(AuthorizationException.class)
    public Object AuthorizationException(Exception ex){
        return SystemResponse.authorizationException();
    }
}
