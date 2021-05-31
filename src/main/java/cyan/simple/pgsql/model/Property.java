package cyan.simple.pgsql.model;

import cyan.simple.pgsql.util.GeneralUtils;
import cyan.simple.pgsql.util.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>Property</p>
 * @author Cyan (snow22314@outlook.com)
 * @version V.0.0.1
 * @group cyan.tool.kit
 * @date 8:45 2021/5/8
 */
@Builder
@AllArgsConstructor
public class Property implements Serializable {
    public static final String ID = "id";
    public static final String VALUE = "value";

    protected Long id;
    protected String name;
    protected Object value;

    public Property() {
    }

    public static List<Property> toPropertiesList(String properties) {
        if (GeneralUtils.isNotEmpty(properties)) {
            Map<String, Map<String, Object>> propertiesMap = JsonUtils.parserMapMap(properties, String.class, String.class, Object.class);
            return propertiesMap.entrySet().stream().map(entry -> Property.builder().name(entry.getKey())
                    .id(Long.parseLong(entry.getValue().get(Property.ID).toString()))
                    .value(entry.getValue().get(Property.VALUE))
                    .build()).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    public static String toPropertiesJson(List<Property> properties) {
        if (GeneralUtils.isNotEmpty(properties)) {
            Map<String, Map<String, Object>> propertiesMap = properties.stream().filter(Objects::nonNull)
                    .collect(Collectors.toMap(Property::getName,
                            property -> {
                                Map<String, Object> container = new LinkedHashMap<>();
                                if (GeneralUtils.isNotEmpty(property.getId())) {
                                    container.put(Property.ID, property.getId());
                                } else {
                                    container.put(Property.ID, -1);
                                }
                                container.put(Property.VALUE, property.getValue());
                                return container;
                            }, (oldValue, newValue) -> newValue, LinkedHashMap::new));
            return JsonUtils.parserJson(propertiesMap);
        } else {
            return JsonUtils.parserJson(Collections.emptyList());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return Objects.equals(name, property.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
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

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }


}
