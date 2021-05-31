package cyan.simple.pgsql.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.NonNull;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>SortEnum</p>
 * @author Cyan (snow22314@outlook.com)
 * @version V.0.0.1
 * @group cyan.tool.kit
 * @date 17:44 2020/9/8
 */
public enum SortType {
    ASC(1,"ASC"),
    DESC(2,"DESC")
    ;

    private final Integer key;
    private final String value;

    SortType(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return this.key;
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }
    
    public static SortType parserKey(@NonNull Integer key) {
        Map<Integer, SortType> sortTypeEnums = Stream.of(SortType.values()).collect(Collectors.toMap(SortType::getKey, Function.identity()));
        return Optional.ofNullable(sortTypeEnums.get(key)).orElse(SortType.DESC);
    }
    
    @JsonCreator
    public static SortType parserValue(@NonNull String value) {
        Map<String, SortType> sortTypeEnums = Stream.of(SortType.values()).collect(Collectors.toMap(SortType::getValue, Function.identity()));
        return Optional.ofNullable(sortTypeEnums.get(value)).orElse(SortType.DESC);
    }


}
