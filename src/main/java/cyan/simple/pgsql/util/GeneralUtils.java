package cyan.simple.pgsql.util;

import java.util.*;

/**
 * <p>GeneralUtils</p>
 * @author Cyan (snow22314@outlook.com)
 * @version V.0.0.1
 * @group cyan.tool.kit
 * @date 10:49 2021/4/6
 */
public class GeneralUtils {
    public static boolean isNotEmpty(Object object) {
        if (object == null) {
            return false;
        } else if (object instanceof Integer) {
            return Integer.valueOf(object.toString()) > 0;
        } else if (object instanceof Long) {
            return Long.valueOf(object.toString()) > 0L;
        } else if (object instanceof String) {
            return ((String)object).trim().length() > 0;
        } else if (object instanceof StringBuffer) {
            return ((StringBuffer)object).toString().trim().length() > 0;
        } else if (object instanceof Boolean) {
            return true;
        } else if (object instanceof List) {
            return ((List)object).size() > 0;
        } else if (object instanceof Set) {
            return ((Set)object).size() > 0;
        } else if (object instanceof Map) {
            return ((Map)object).size() > 0;
        } else if (object instanceof Iterator) {
            return ((Iterator)object).hasNext();
        } else if (object.getClass().isArray()) {
            return Arrays.asList(object).size() > 0;
        } else {
            return true;
        }
    }

    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof Integer) {
            return Integer.valueOf(object.toString()) == 0;
        } else if (object instanceof Long) {
            return Long.valueOf(object.toString()) == 0L;
        } else if (object instanceof String) {
            return ((String)object).trim().length() == 0;
        } else if (object instanceof StringBuffer) {
            return ((StringBuffer)object).toString().trim().length() == 0;
        } else if (object instanceof Boolean) {
            return false;
        } else if (object instanceof List) {
            return ((List)object).size() == 0;
        } else if (object instanceof Set) {
            return ((Set)object).size() == 0;
        } else if (object instanceof Map) {
            return ((Map)object).size() == 0;
        } else if (object instanceof Iterator) {
            return !((Iterator)object).hasNext();
        } else if (object.getClass().isArray()) {
            return Arrays.asList(object).size() == 0;
        } else {
            return false;
        }
    }
}
