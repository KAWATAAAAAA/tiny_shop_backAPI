package cn.wuyuwei.tiny_shop.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import cn.wuyuwei.tiny_shop.common.ApiException;
import cn.wuyuwei.tiny_shop.common.ApiResultEnum;
import cn.wuyuwei.tiny_shop.common.Result;

import java.io.IOException;

/**
 * 配置全局异常捕获
 * @author wuyuwei
 * @since 2020-01-10
 * spring 3.2中，新增了@ControllerAdvice 注解，可以用于定义@ExceptionHandler，并应用到所有@RequestMapping中
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NullPointerException.class)
    public Result NullPointer(NullPointerException ex){
        System.out.println("捕获到了NullPointerException");
        logger.error(ex.getMessage(),ex);
        return Result.error(ApiResultEnum.ERROR_NULL);
    }

    @ExceptionHandler(ClassCastException.class)
    public Result ClassCastException(ClassCastException ex){
        System.out.println("捕获到了ClassCastException");
        logger.error(ex.getMessage(),ex);
        return Result.error(ApiResultEnum.ERROR_CLASS_CAST);
    }

    @ExceptionHandler(IOException.class)
    public Result IOException(IOException ex){
        System.out.println("捕获到了IOException");
        logger.error(ex.getMessage(),ex);
        return Result.error(ApiResultEnum.ERROR_IO);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex){
        System.out.println("捕获到了HttpRequestMethodNotSupportedException");
        logger.error(ex.getMessage(),ex);
        return Result.error(ApiResultEnum.ERROR_MOTHODNOTSUPPORT);
    }

    @ExceptionHandler(ApiException.class)
    public Result ApiException(ApiException ex) {
        System.out.println("捕获到了ApiException");
        logger.error(ex.getMessage(),ex);
        return Result.error(ex.getCode(),ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public Result RuntimeException(RuntimeException ex){
        System.out.println("捕获到了RuntimeException");
        logger.error(ex.getMessage(),ex);
        return Result.error(ApiResultEnum.ERROR_RUNTION.getCode(),ApiResultEnum.ERROR_RUNTION.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result exception(Exception ex){
        System.out.println("捕获到了Exception");
        logger.error(ex.getMessage(),ex);
        return Result.error(ApiResultEnum.ERROR);
    }

}