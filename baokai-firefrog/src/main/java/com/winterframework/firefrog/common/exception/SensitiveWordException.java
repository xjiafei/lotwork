package com.winterframework.firefrog.common.exception;

/**
 * @author Lex
 * @ClassName: SensitiveWordException
 * @Description: 敏感词匹配异常
 * @date 2014/8/1 17:14
 */
public class SensitiveWordException extends RuntimeException{
    private static final String MSG = "包含敏感词";
    private static final Long CODE = 100320L;
    public SensitiveWordException() {
        super(MSG);
    }

    public SensitiveWordException(Throwable cause) {
        super(String.valueOf(CODE), cause);
    }
    public Long getStatus() {
        return CODE;
    }
}
