package cyan.simple.pgsql.dao.entity;

import cyan.simple.pgsql.jts.JtsUtils;
import cyan.simple.pgsql.model.Property;
import cyan.simple.pgsql.model.Thing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * <p>ThingEntity</p>
 * @author Cyan (snow22314@outlook.com)
 * @version V.0.0.1
 * @group cyan.tool.kit
 * @date 14:27 2021/5/6
 */
@Builder
@Table(name = "thing")
@AllArgsConstructor
public class ThingEntity implements Serializable {
    /** you can use the ability of mybatis common mapper component */
    @Id
    private Long id;
    /** 名称 */
    private String name;
    /** 描述 */
    private String description;
    /** 属性 */
    private String properties;
    /** 类型 */
    private String type;
    /** byte[] 加快写入效率 */
    private byte[] location;
    /** 时间 */
    private Date time;
    /** 数据 */
    private String data;
    /** 数据创建时间 */
    @Column(name = "create_time")
    private Date createTime;
    /** 数据更新时间 */
    @Column(name = "update_time")
    private Date updateTime;

    public ThingEntity() {
    }

    public ThingEntity(Long id) {
        this.id = id;
    }

    /** 多模块得情况下 model和entity通常是分开的 因此 toEntity 一般写在下层的Entity中*/

    public static ThingEntity toEntity(@NonNull Thing thing) {
        return ThingEntity.builder()
                .id(thing.getId())
                .name(thing.getName())
                .description(thing.getDescription())
                .properties(Property.toPropertiesJson(thing.getProperties()))
                .type(thing.getType())
                .location(JtsUtils.parserWkb(thing.getLocation()))
                .time(thing.getTime())
                .data(thing.getData())
                .createTime(new Date())
                .updateTime(new Date())
                .build();
    }

    public Thing toModel() {
        return Thing.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .properties(Property.toPropertiesList(this.properties))
                .type(this.type)
                .location(JtsUtils.parserGeometry(this.location))
                .time(this.time)
                .data(this.data)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThingEntity that = (ThingEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getLocation() {
        return location;
    }

    public void setLocation(byte[] location) {
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
