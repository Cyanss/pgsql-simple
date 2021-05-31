package cyan.simple.pgsql.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cyan.simple.pgsql.filter.PageFilter;
import cyan.simple.pgsql.jsonb.ContainRule;
import cyan.simple.pgsql.jts.JtsUtils;
import cyan.simple.pgsql.jts.serialization.GeometryDeserializer;
import cyan.simple.pgsql.jts.serialization.GeometrySerializer;
import cyan.simple.pgsql.util.DateUtils;
import cyan.simple.pgsql.util.GeneralUtils;
import cyan.simple.pgsql.util.JsonUtils;
import org.locationtech.jts.geom.Geometry;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.*;

/**
 * <p>Thing</p>
 * @author Cyan (snow22314@outlook.com)
 * @version V.0.0.1
 * @group cyan.tool.kit
 * @date 14:28 2021/5/6
 */
public class Thing implements Serializable {
    private Long id;
    /** 名称 */
    private String name;
    /** 描述 */
    private String description;
    /** 属性 */
    private Set<Property> properties;
    /** 类型 */
    private String type;
    /** 位置 */
    @JsonDeserialize(using = GeometryDeserializer.class)
    @JsonSerialize(using = GeometrySerializer.class)
    private Geometry location;
    /** 时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:sss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss:sss")
    private Date time;
    /** 数据 */
    private String data;

    public Thing() {
    }

    public Thing(Long id) {
        this.id = id;
    }

    public Thing(Thing.Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.properties = builder.properties;
        this.type = builder.type;
        this.location = builder.location;
        this.time = builder.time;
        this.data = builder.data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Thing thing = (Thing) o;
        return Objects.equals(id, thing.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return JsonUtils.parserJson(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Property> getProperties() {
        if (GeneralUtils.isNotEmpty(properties)) {
            return new ArrayList<>(properties);
        }
        return null;
    }

    public void setProperties(Property... properties) {
        this.properties = Optional.ofNullable(properties).map(propertyList -> new HashSet<>(Arrays.asList(propertyList))).orElse(null);
    }

    @JsonSetter
    public void setProperties(Collection<Property> properties) {
        this.properties = Optional.ofNullable(properties).map(HashSet::new).orElse(null);
    }

    public void addProperties(Property... properties) {
        if (GeneralUtils.isEmpty(this.properties)) {
            this.properties = Optional.ofNullable(properties).map(propertyList -> new HashSet<>(Arrays.asList(propertyList))).orElse(null);
        } else {
            Optional.ofNullable(properties).ifPresent(propertyList -> this.properties.addAll(Arrays.asList(propertyList)));
        }
    }

    public void addProperties(Collection<Property> properties) {
        if (GeneralUtils.isEmpty(this.properties)) {
            this.properties = Optional.ofNullable(properties).map(HashSet::new).orElse(null);
        } else {
            Optional.ofNullable(properties).ifPresent(this.properties::addAll);
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Geometry getLocation() {
        return location;
    }

    public void setLocation(Geometry location) {
        this.location = location;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static Thing.Builder builder() {
        return new Thing.Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private Set<Property> properties;
        private String type;
        private Geometry location;
        private Date time;
        private String data;

        public Builder() {
        }

        public Thing.Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Thing.Builder name(String name) {
            this.name = name;
            return this;
        }

        public Thing.Builder description(String description) {
            this.description = description;
            return this;
        }

        public Thing.Builder properties(Collection<Property> properties) {
            this.properties = Optional.ofNullable(properties).map(HashSet::new).orElse(null);
            return this;
        }

        public Thing.Builder properties(@NonNull Property... properties) {
            this.properties = Optional.ofNullable(properties).map(propertyList -> new HashSet<>(Arrays.asList(propertyList))).orElse(null);
            return this;
        }

        public Thing.Builder type(String type) {
            this.type = type;
            return this;
        }

        public Thing.Builder location(byte[] location) {
            this.location = JtsUtils.parserGeometry(location);
            return this;
        }

        public Thing.Builder location(Geometry location) {
            this.location = location;
            return this;
        }

        public Thing.Builder time(Date time) {
            this.time = time;
            return this;
        }

        public Thing.Builder time(String time) {
            this.time = DateUtils.parseDate(time);
            return this;
        }

        public Thing.Builder data(String data) {
            this.data = data;
            return this;
        }

        public Thing build() {
            return new Thing(this);
        }
    }
}
