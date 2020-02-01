package cn.wuyuwei.tiny_shop.common;


import lombok.Data;

/**
 * 自定义的api异常
 * @author wuyuwei
 *
 */
@Data
public class ApiException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private int code;

    private String message;
    private Object data;
    private Exception exception;
    public ApiException() {
        super();
    }
    public ApiException(int code, String message, Object data, Exception exception) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.exception = exception;
    }
    public ApiException(ApiResultEnum apiResultEnum) {
        this(apiResultEnum.getCode(),apiResultEnum.getMessage(),null,null);
    }
    public ApiException(ApiResultEnum apiResultEnum, Object data) {
        this(apiResultEnum.getCode(),apiResultEnum.getMessage(),data,null);
    }
    public ApiException(ApiResultEnum apiResultEnum, Object data, Exception exception) {
        this(apiResultEnum.getCode(),apiResultEnum.getMessage(),data,exception);
    }


}