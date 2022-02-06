package com.atguigu.crowd;

/**
 * @author shiyutao
 * @create 2022-01-11 17:36
 */
public class LoginAcctAlreadyInUserException extends RuntimeException {
    static final long serialVersionUID = 1L;
    public LoginAcctAlreadyInUserException() {
        super();
    }

    public LoginAcctAlreadyInUserException(String message) {
        super(message);
    }

    public LoginAcctAlreadyInUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyInUserException(Throwable cause) {
        super(cause);
    }

    protected LoginAcctAlreadyInUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
