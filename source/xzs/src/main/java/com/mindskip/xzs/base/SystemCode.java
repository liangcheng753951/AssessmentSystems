package com.mindskip.xzs.base;

public enum SystemCode {
    /**
     * OK
     */
    OK(1, "Success"),
    /**
     * AccessTokenError
     */
    AccessTokenError(400, "Invalid user login token"),
    /**
     * UNAUTHORIZED
     */
    UNAUTHORIZED(401, "User not logged in"),
    /**
     * UNAUTHORIZED
     */
    AuthError(402, "Incorrect username or password"),
    /**
     * InnerError
     */
    InnerError(500, "Internal system errors"),
    /**
     * ParameterValidError
     */
    ParameterValidError(501, "Parameter validation error"),

    /**
     * AccessDenied
     */
    AccessDenied(502, "Users do not have permission to access");

    /**
     * The Code.
     */
    int code;
    /**
     * The Message.
     */
    String message;

    SystemCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }
}
