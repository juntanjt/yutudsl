package com.juntanjt.yutudsl.dsl.exception;

/**
 * engine exception
 *
 * @author Jun Tan
 */
public class EngineException extends RuntimeException {

    /**
     *
     */
    private ErrorCode errorCode;

    public EngineException(ErrorCode errorCode) {
        super(errorCode.toString());
        this.errorCode = errorCode;
    }

    public EngineException(ErrorCode errorCode, String message) {
        super(errorCode.toString() + message);
        this.errorCode = errorCode;
    }

    public EngineException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.toString(), cause);
        this.errorCode = errorCode;
    }

    public EngineException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode.toString() + message, cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
