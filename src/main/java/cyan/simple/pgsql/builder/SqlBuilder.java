package cyan.simple.pgsql.builder;

import cyan.simple.pgsql.util.GeneralUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * <p>SqlBuilder</p>
 * @author Cyan (snow22314@outlook.com)
 * @version V.0.0.1
 * @group cyan.tool.kit
 * @date 14:33 2020/9/11
 */
public final class SqlBuilder implements Serializable, CharSequence {
    public static final String EMPTY = "";

    protected final StringBuilder sqlBuilder;

    public SqlBuilder() {
        this.sqlBuilder = new StringBuilder();
    }

    public SqlBuilder(StringBuilder sqlBuilder) {
        this.sqlBuilder = sqlBuilder;
    }

    public SqlBuilder(int capacity) {
        this.sqlBuilder = new StringBuilder(capacity);
    }

    public SqlBuilder(String str) {
        this.sqlBuilder = new StringBuilder(str);
    }

    public SqlBuilder(CharSequence seq) {
        this.sqlBuilder = new StringBuilder(seq);
    }

    public SqlBuilder append(Object obj) {
        return append(String.valueOf(obj));
    }

    public SqlBuilder append(String str) {
        sqlBuilder.append(str);
        return this;
    }

    public SqlBuilder append(StringBuffer sb) {
        sqlBuilder.append(sb);
        return this;
    }

    public SqlBuilder append(CharSequence s) {
        sqlBuilder.append(s);
        return this;
    }

    public SqlBuilder append(CharSequence s, int start, int end) {
        sqlBuilder.append(s, start, end);
        return this;
    }

    public SqlBuilder append(char[] str) {
        sqlBuilder.append(str);
        return this;
    }


    public SqlBuilder append(char[] str, int offset, int len) {
        sqlBuilder.append(str, offset, len);
        return this;
    }


    public SqlBuilder append(boolean b) {
        sqlBuilder.append(b);
        return this;
    }


    public SqlBuilder append(char c) {
        sqlBuilder.append(c);
        return this;
    }


    public SqlBuilder append(int i) {
        sqlBuilder.append(i);
        return this;
    }


    public SqlBuilder append(long lng) {
        sqlBuilder.append(lng);
        return this;
    }


    public SqlBuilder append(float f) {
        sqlBuilder.append(f);
        return this;
    }

    public SqlBuilder append(double d) {
        sqlBuilder.append(d);
        return this;
    }

    public SqlBuilder appendCodePoint(int codePoint) {
        sqlBuilder.appendCodePoint(codePoint);
        return this;
    }

    public SqlBuilder delete(int start, int end) {
        sqlBuilder.delete(start, end);
        return this;
    }

    public SqlBuilder deleteCharAt(int index) {
        sqlBuilder.deleteCharAt(index);
        return this;
    }

    public SqlBuilder replace(int start, int end, String str) {
        sqlBuilder.replace(start, end, str);
        return this;
    }

    public SqlBuilder insert(int index, char[] str, int offset,
                             int len) {
        sqlBuilder.insert(index, str, offset, len);
        return this;
    }

    public SqlBuilder insert(int offset, Object obj) {
        sqlBuilder.insert(offset, obj);
        return this;
    }

    public SqlBuilder insert(int offset, String str) {
        sqlBuilder.insert(offset, str);
        return this;
    }

    public SqlBuilder insert(int offset, char[] str) {
        sqlBuilder.insert(offset, str);
        return this;
    }

    public SqlBuilder insert(int dstOffset, CharSequence s) {
        sqlBuilder.insert(dstOffset, s);
        return this;
    }

    public SqlBuilder insert(int dstOffset, CharSequence s,
                             int start, int end) {
        sqlBuilder.insert(dstOffset, s, start, end);
        return this;
    }

    public SqlBuilder insert(int offset, boolean b) {
        sqlBuilder.insert(offset, b);
        return this;
    }

    public SqlBuilder insert(int offset, char c) {
        sqlBuilder.insert(offset, c);
        return this;
    }

    public SqlBuilder insert(int offset, int i) {
        sqlBuilder.insert(offset, i);
        return this;
    }

    public SqlBuilder insert(int offset, long l) {
        sqlBuilder.insert(offset, l);
        return this;
    }

    public SqlBuilder insert(int offset, float f) {
        sqlBuilder.insert(offset, f);
        return this;
    }

    public SqlBuilder insert(int offset, double d) {
        sqlBuilder.insert(offset, d);
        return this;
    }


    public int indexOf(String str) {
        return sqlBuilder.indexOf(str);
    }


    public int indexOf(String str, int fromIndex) {
        return sqlBuilder.indexOf(str, fromIndex);
    }


    public int lastIndexOf(String str) {
        return sqlBuilder.lastIndexOf(str);
    }


    public int lastIndexOf(String str, int fromIndex) {
        return sqlBuilder.lastIndexOf(str, fromIndex);
    }


    public SqlBuilder reverse() {
        sqlBuilder.reverse();
        return this;
    }

    @Override
    public int length() {
        return sqlBuilder.length();
    }

    @Override
    public char charAt(int index) {
        return sqlBuilder.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return sqlBuilder.subSequence(start,end);
    }

    @Override
    public String toString() {
        if (GeneralUtils.isEmpty(sqlBuilder)) {
            return "";
        }
        return sqlBuilder.toString();
    }

    public StringBuilder getSqlBuilder() {
        return sqlBuilder;
    }

    public SqlBuilder eq(String target, Object value, Boolean andOfOr) {
        if (GeneralUtils.isNotEmpty(value)) {
            this.andOfOr(andOfOr);
            this.append(target).append(" = ");
            this.value(value);
        }
        return this;
    }

    public SqlBuilder neq(String target, Object value, Boolean andOfOr) {
        if (GeneralUtils.isNotEmpty(value)) {
            this.andOfOr(andOfOr);
            this.append(target).append(" != ");
            this.value(value);
        }
        return this;
    }

    public SqlBuilder lk(Collection<String> targets, String value, Boolean andOfOr) {
        if (GeneralUtils.isNotEmpty(value)) {
            this.andOfOr(andOfOr);
            targets.forEach(target -> this.append(target).append(" LIKE CONCAT('%','").append(value).append("','%')").append(" OR "));
            this.delete(this.length() - 4, this.length());
            this.append(" )");
        }
        return this;
    }

    public SqlBuilder lk(String target, String value, Boolean andOfOr) {
        if (GeneralUtils.isNotEmpty(value)) {
            this.andOfOr(andOfOr);
            this.append(target).append(" LIKE CONCAT('%','").append(value).append("','%')");
        }
        return this;
    }

    public SqlBuilder ain(String target, Collection<?> values) {
        return in(target,values,true);
    }

    public SqlBuilder oin(String target, Collection<?> values) {
        return in(target,values,false);
    }

    public SqlBuilder in(String target, Collection<?> values, Boolean andOfOr) {
        if (GeneralUtils.isNotEmpty(values)) {
            if (values.size() == 1) {
                Object value = values.stream().findFirst().get();
                this.eq(target,value,andOfOr);
            } else {
                this.andOfOr(andOfOr);
                this.append(target).append(" IN (");
                values.forEach(value -> this.value(value,true));
                this.deleteCharAt(this.length() - 2);
                this.append(")");
            }
        }
        return this;
    }

    public SqlBuilder rb(String target,  Object beginValue, Object endValue) {
        if (GeneralUtils.isNotEmpty(beginValue) && GeneralUtils.isNotEmpty(endValue)) {
            this.gt(target,endValue,true);
            this.lt(target,beginValue,true);
        }
        return this;
    }

    public SqlBuilder reb(String target,  Object beginValue, Object endValue) {
        if (GeneralUtils.isNotEmpty(beginValue) && GeneralUtils.isNotEmpty(endValue)) {
            this.gte(target,endValue,true);
            this.lte(target,beginValue,true);
        }
        return this;
    }

    public SqlBuilder reo(String target,  Object beginValue, Object endValue, Boolean andOfOr) {
        this.andOfOr(andOfOr);
        this.append("( ");
        this.gte(target,endValue,null);
        this.lte(target,beginValue,false);
        this.append(" )");
        return this;
    }

    public SqlBuilder ro(String target,  Object beginValue, Object endValue, Boolean andOfOr) {
        this.andOfOr(andOfOr);
        this.append("( ");
        this.gt(target,endValue,null);
        this.lt(target,beginValue,false);
        this.append(" )");
        return this;
    }

    public SqlBuilder ra(String target,  Object beginValue, Object endValue, Boolean andOfOr) {
        this.andOfOr(andOfOr);
        this.append("( ");
        this.gt(target,beginValue,null);
        this.lt(target,endValue,true);
        this.append(" )");
        return this;
    }

    public SqlBuilder rea(String target,  Object beginValue, Object endValue, Boolean andOfOr) {
        this.andOfOr(andOfOr);
        this.append("( ");
        this.gte(target,beginValue,null);
        this.lte(target,endValue,true);
        this.append(" )");
        return this;
    }

    public SqlBuilder r(String target,  Object beginValue, Object endValue, Boolean andOfOr) {
        this.gt(target, beginValue, andOfOr);
        this.lt(target, endValue, andOfOr);
        return this;
    }

    public SqlBuilder re(String target,  Object beginValue, Object endValue, Boolean andOfOr) {
        this.gte(target,beginValue, andOfOr);
        this.lte(target,endValue, andOfOr);
        return this;
    }

    public SqlBuilder rs(String target,  Object beginValue, Object endValue, Boolean andOfOr, Boolean beginOfEnd) {
        if (beginOfEnd) {
            this.gte(target, beginValue, andOfOr);
            this.lt(target, endValue, andOfOr);
        } else {
            this.gt(target, beginValue, andOfOr);
            this.lte(target, endValue, andOfOr);
        }
        return this;
    }

    public SqlBuilder gt(String target, Object value, Boolean andOfOr) {
        if (GeneralUtils.isNotEmpty(value)) {
            this.andOfOr(andOfOr);
            this.append(target).append(" > ");
            this.value(value);
        }
        return this;
    }

    public SqlBuilder lt(String target, Object value, Boolean andOfOr) {
        if (GeneralUtils.isNotEmpty(value)) {
            this.andOfOr(andOfOr);
            this.append(target).append(" < ");
            this.value(value);
        }
        return this;
    }


    public SqlBuilder gte(String target, Object value, Boolean andOfOr) {
        if (GeneralUtils.isNotEmpty(value)) {
            this.andOfOr(andOfOr);
            this.append(target).append(" >= ");
            this.value(value);
        }
        return this;
    }

    public SqlBuilder lte(String target, Object value, Boolean andOfOr) {
        if (GeneralUtils.isNotEmpty(value)) {
            this.andOfOr(andOfOr);
            this.append(target).append(" <= ");
            this.value(value);
        }
        return this;
    }

    public SqlBuilder value(Object value) {
        this.value(value,false);
        return this;
    }

    public SqlBuilder value(Object value, Boolean commaOfNone) {
        if (value instanceof String) {
            // the value is like '2020-09-11 00:00:00'
            this.append("'").append(value).append("'");
            if (commaOfNone) {
                this.append(", ");
            } else {
                this.append(" ");
            }
        } else {
            this.append(value);
            if (commaOfNone) {
                this.append(", ");
            } else {
                this.append(" ");
            }
        }
        return this;
    }

    public SqlBuilder value(Object value, String symbol) {
        if (value instanceof String) {
            // the value is like '2020-09-11 00:00:00'
            this.append("'").append(value).append("'").append(symbol).append(" ");
        } else {
            this.append(value).append(symbol).append(" ");
        }
        return this;
    }

    public SqlBuilder andOfOr(Boolean andOfOr) {
        if (GeneralUtils.isNotEmpty(andOfOr)) {
            if (andOfOr) {
                this.and();
            } else {
                this.or();
            }
        }
        return this;
    }

    public SqlBuilder and() {
        this.append(" AND ");
        return this;
    }

    public SqlBuilder or() {
        this.append(" OR ");
        return this;
    }
}
