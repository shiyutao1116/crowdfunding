package com.atguigu.crowd;

/**
 * @author shiyutao
 * @create 2022-01-11 17:36
 */
public class LoginAcctAlreadyInUserException2 extends RuntimeException {
    static final long serialVersionUID = 1L;
    public LoginAcctAlreadyInUserException2() {
        super();
    }

    public LoginAcctAlreadyInUserException2(String message) {
        super(message);
    }

    public LoginAcctAlreadyInUserException2(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyInUserException2(Throwable cause) {
        super(cause);
    }

    protected LoginAcctAlreadyInUserException2(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
