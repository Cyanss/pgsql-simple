package cyan.simple.pgsql.jts.error;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * <p>JtsParseException</p>
 * @author Cyan (snow22314@outlook.com)
 * @version V.0.0.1
 * @group cyan.tool.kit
 * @date 10:25 2020/9/22
 */
public class JtsParseException extends JsonProcessingException {
    public static final Integer ERROR_STATUS = 10700;
    public static final String ERROR_MESSAGE = "Jts相关数据解析错误";

    protected Integer status;

    public JtsParseException() {
        super(ERROR_MESSAGE);
        this.status = ERROR_STATUS;
    }

    public JtsParseException(String message) {
        super(message);
        this.status = ERROR_STATUS;
    }

    public JtsParseException(Integer status, String message) {
        super(message);
        this.status = ERROR_STATUS;
    }

    public JtsParseException(String message, Throwable cause) {
        super(message,cause);
        this.status = ERROR_STATUS;
    }

    public JtsParseException(Integer status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public JtsParseException get() {
        return new JtsParseException();
    }

    public String name() {
        return "jts parse exception";
    }
}
