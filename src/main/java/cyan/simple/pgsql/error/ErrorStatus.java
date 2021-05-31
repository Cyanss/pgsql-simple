package cyan.simple.pgsql.error;

/**
 * <p>ErrorStatus</p>
 * @author Cyan (snow22314@outlook.com)
 * @version V.0.0.1
 * @group cyan.tool.kit
 * @date 9:50 2021/5/10
 */
public enum ErrorStatus {
    SUCCESS(200, "成功"),
    FAILED(400, "失败"),
    /** timeout */
    UNKNOWN_ERROR(9999,"未知错误"),
    TIME_OUT(10000, "访问超时"),
    /** base */
    PARAM_ERROR(10010, "参数错误"),
    PARAM_INVALID(10012, "参数无效"),
    PARAM_MISSING(10011, "参数缺失"),
    /** name */
    NAME_ERROR(10510, "名称错误"),
    NAME_IS_NULL(10511, "名称为空"),
    NAME_REPEATED(10512, "名称重复"),
    /** data */
    DATA_ERROR(10600, "数据错误"),
    DATA_NOT_EXIST_ERROR(10601, "数据不存在"),
    DATA_SAVE_FAILED(10603, "数据保存失败"),
    DATA_DELETE_FAILED(10604, "数据删除失败"),
    DATA_QUERY_FAILED(10605, "数据查询失败"),

    /** data all */
    DATA_ALL_ERROR(10610, "数据批量处理错误"),
    DATA_SAVE_ALL_FAILED(10613, "数据批量保存失败"),
    ;
    private final Integer status;
    private final String message;

    ErrorStatus(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }}
