package cyan.simple.pgsql.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import cyan.simple.pgsql.builder.SqlBuilders;
import cyan.simple.pgsql.model.RestSort;
import cyan.simple.pgsql.util.GeneralUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

/**
 * <p>TimeFilter</p>
 * @author Cyan (snow22314@outlook.com)
 * @version V.0.0.1
 * @group cyan.tool.kit
 * @date 16:25 2020/9/10
 */
public class TimeFilter<I> extends IdFilter<I> {

    /** default like '2020-01-01 00:00:00' */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:sss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss:sss", timezone = "GMT+8")
    protected Date startTime;
    /** default like '2020-01-02 00:00:00' */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:sss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss:sss", timezone = "GMT+8")
    protected Date endTime;

    public TimeFilter() {
    }

    @SuppressWarnings(value = "unchecked")
    public TimeFilter(I... ids) {
        super(ids);
    }

    public TimeFilter(TimeFilter.Builder<I> builder) {
        super(builder);
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public TimeFilter<I> toTimeSql(@NonNull String alias) {
        if (GeneralUtils.isNotEmpty(this.startTime) && GeneralUtils.isNotEmpty(this.endTime) && this.startTime == this.endTime ) {
            SqlBuilders.equal(SQL_BUILDER, alias, this.startTime);
        } else {
            SqlBuilders.range(SQL_BUILDER, alias, this.startTime, this.endTime);
        }
        return this;
    }

    @Override
    public TimeFilter<I> toIdSql(@NonNull String alias) {
        super.toIdSql(alias);
        return this;
    }

    @Override
    public String toKey() {
        String nameKey = super.toKey();
        StringBuilder keyBuilder = new StringBuilder();
        if (GeneralUtils.isNotEmpty(this.startTime)) {
            keyBuilder.append(this.startTime).append(PageFilter.PAGE_REGEX);
        }
        if (GeneralUtils.isNotEmpty(this.endTime)) {
            keyBuilder.append(this.endTime).append(PageFilter.PAGE_REGEX);
        }
        keyBuilder.append(nameKey);
        return keyBuilder.toString();
    }

    public static class Builder<I> extends IdFilter.Builder<I> {
        protected Date startTime;
        protected Date endTime;

        public Builder() {
        }

        public TimeFilter.Builder<I> startTime(Date startTime) {
            this.startTime = startTime;
            return this;
        }

        public TimeFilter.Builder<I> endTime(Date endTime) {
            this.endTime = endTime;
            return this;
        }

        public TimeFilter.Builder<I> ids(@NonNull Collection<I> ids) {
            this.ids = new HashSet<>(ids);
            return this;
        }

        @SuppressWarnings(value = "unchecked")
        public TimeFilter.Builder<I> ids(@NonNull I... ids) {
            this.ids = new HashSet<>(Arrays.asList(ids));
            return this;
        }

        public TimeFilter.Builder<I> sorts(@NonNull Collection<RestSort> sorts) {
            this.sorts = new HashSet<>(sorts);
            return this;
        }

        public TimeFilter.Builder<I> sorts(@NonNull RestSort... sorts) {
            this.sorts = new HashSet<>(Arrays.asList(sorts));
            return this;
        }

        public TimeFilter.Builder<I> pageNum(Integer pageNum) {
            this.pageNum = pageNum;
            return this;
        }

        public TimeFilter.Builder<I> pageSize(Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public TimeFilter<I> build() {
            return new TimeFilter<>(this);
        }
    }
}
