package cyan.simple.pgsql.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import cyan.simple.pgsql.error.ErrorStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>RequestResult</p>
 * @author Cyan (snow22314@outlook.com)
 * @version V.0.0.1
 * @group cyan.tool.kit
 * @date 10:01 2021/5/10
 */
public class RestResult<T> implements Serializable {
    private Integer status;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date time;

    public RestResult() {
    }

    public RestResult(Integer status, String message) {
        this.status = status;
        this.message = message;
        this.time = new Date();
    }
    public RestResult(RestResult.Builder<T> builder) {
        this.status = builder.status;
        this.message = builder.message;
        this.data = builder.data;
        this.time = builder.time;
    }

    public RestResult(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.time = new Date();
    }

    public static <T> ResponseEntity<RestResult<T>> ok() {
        return ResponseEntity.ok((new RestResult.Builder<T>()).status(ErrorStatus.SUCCESS.getStatus()).build());
    }

    public static <T> ResponseEntity<RestResult<T>> ok(T data) {
        return ResponseEntity.ok((new RestResult.Builder<T>()).status(ErrorStatus.SUCCESS.getStatus()).data(data).build());
    }

    public static <T> ResponseEntity<RestResult<T>> ok(String message) {
        return ResponseEntity.ok((new RestResult.Builder<T>()).status(ErrorStatus.SUCCESS.getStatus()).message(message).build());
    }

    public static <T> ResponseEntity<RestResult<T>> ok(String message, T data) {
        return ResponseEntity.ok((new RestResult.Builder<T>()).status(ErrorStatus.SUCCESS.getStatus()).message(message).data(data).build());
    }

    public static <T> ResponseEntity<RestResult<T>> error(String message) {
        return ResponseEntity.ok((new RestResult.Builder<T>()).status(ErrorStatus.FAILED.getStatus()).message(message).build());
    }

    public static <T> ResponseEntity<RestResult<T>> error(String message, T data) {
        return ResponseEntity.ok((new RestResult.Builder<T>()).status(ErrorStatus.FAILED.getStatus()).message(message).data(data).build());
    }

    public static <T> ResponseEntity<RestResult<T>> error(ErrorStatus status) {
        return ResponseEntity.ok((new RestResult.Builder<T>()).status(status.getStatus()).message(status.getMessage()).build());
    }

    public static <T> ResponseEntity<RestResult<T>> error(ErrorStatus status, T data) {
        return ResponseEntity.ok((new RestResult.Builder<T>()).status(status.getStatus()).message(status.getMessage()).data(data).build());
    }

    public static <T> ResponseEntity<RestResult<T>> error(Integer status, String message, T data) {
        return ResponseEntity.ok((new RestResult.Builder<T>()).status(status).message(message).data(data).build());
    }

    public static <T> ResponseEntity<RestResult<T>> error(Integer status, String message) {
        return ResponseEntity.ok((new RestResult.Builder<T>()).status(status).message(message).build());
    }

    public static <T> RestResult.Builder<T> builder() {
        return new RestResult.Builder<>();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public static class Builder<T> {
        protected Integer status;
        protected String message;
        protected T data;
        protected Date time;

        public Builder() {
            this.time = new Date();
        }

        public RestResult.Builder<T> status(Integer status) {
            this.status = status;
            return this;
        }

        public RestResult.Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public RestResult.Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public RestResult.Builder<T> time(Date time) {
            this.time = time;
            return this;
        }

        public RestResult.Builder<T> time(Long time) {
            this.time = new Date(time);
            return this;
        }

        public RestResult<T> build() {
            return new RestResult<>(this);
        }
    }
}
