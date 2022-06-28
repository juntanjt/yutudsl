package com.juntanjt.yutudsl.dsl.exception;

import java.util.StringJoiner;

/**
 * @author Jun Tan
 */
public enum ErrorCode {

    /**
     *
     */
    SYSTEM_ERROR("100", "system error"),

    DSL_COMPILE_ERROR("1001", "dsl compile error"),

    DSL_EXECUTE_ERROR("1001", "dsl execute error"),

    PARAM_ERROR("200", "param error"),

    ;

    /**
     *
     */
   private String errorCode;
    /**
     *
     */
   private String message;

    ErrorCode(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ErrorCode.class.getSimpleName() + "[", "]")
                .add("errorCode='" + errorCode + "'")
                .add("message='" + message + "'")
                .toString();
    }
}
