package cyan.simple.pgsql.error;

import cyan.simple.pgsql.result.RestResult;

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * <p>PgsqlException</p>
 * @author Cyan (snow22314@outlook.com)
 * @version V.0.0.1
 * @group cyan.tool.kit
 * @date 9:39 2021/5/10
 */
public class PgsqlException extends RuntimeException implements Supplier, Serializable {
    private Integer status;

    public PgsqlException() {
    }

    public PgsqlException(ErrorStatus status) {
        super(status.getMessage());
        this.status = status.getStatus();
    }

    public PgsqlException(ErrorStatus status, String message) {
        super(status.getMessage().concat(", ").concat(message));
        this.status = status.getStatus();
    }

    public PgsqlException(ErrorStatus status, Throwable cause) {
        super(status.getMessage(), cause);
        this.status = status.getStatus();
    }

    public PgsqlException(ErrorStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status.getStatus();
    }

    public PgsqlException(String message) {
        super(message);
    }

    public PgsqlException(String message, Throwable cause) {
        super(message, cause);
    }

    public PgsqlException(Throwable cause) {
        super(cause);
    }

    public PgsqlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PgsqlException(Integer status) {
        this.status = status;
    }

    public PgsqlException(Integer status, String message) {
        super(message);
        this.status = status;
    }

    public PgsqlException(Integer status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public PgsqlException(Integer status, Throwable cause ) {
        super(cause);
        this.status = status;
    }

    public PgsqlException(Integer status, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public final RestResult buildResult() {
        return RestResult.builder().status(this.status).message(getMessage()).build();
    }


    @Override
    public PgsqlException get() {
        return new PgsqlException();
    }
}
