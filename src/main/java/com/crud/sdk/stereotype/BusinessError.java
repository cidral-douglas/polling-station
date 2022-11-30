package com.crud.sdk.stereotype;

public abstract class BusinessError extends RuntimeException {

    private static final long serialVersionUID = 4012067823922039088L;

    public BusinessError(String message) {
        super(message, null, true, false);
    }

    public BusinessError(Throwable cause) {
        super(cause);
    }

}
