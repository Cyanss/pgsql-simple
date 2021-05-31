package cyan.simple.pgsql.error;

import cyan.simple.pgsql.result.RestResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>ExceptionAdvice</p>
 * @author Cyan (snow22314@outlook.com)
 * @version V.0.0.1
 * @group cyan.tool.kit
 * @date 9:57 2021/5/10
 */
@CrossOrigin
@ControllerAdvice
@RestControllerAdvice
public class ExceptionAdvice {
    @ResponseBody
    @ExceptionHandler({Exception.class})
    public ResponseEntity exceptionHandle(Exception exception) {
        if (exception instanceof PgsqlException) {
            PgsqlException pgsqlException = (PgsqlException) exception;
            return ResponseEntity.ok(pgsqlException.buildResult());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(RestResult.error(ErrorStatus.UNKNOWN_ERROR,exception));
        }
    }
}
