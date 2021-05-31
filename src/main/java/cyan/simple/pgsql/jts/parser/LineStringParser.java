package cyan.simple.pgsql.jts.parser;

import com.fasterxml.jackson.databind.JsonNode;
import cyan.simple.pgsql.jts.JtsParser;
import cyan.simple.pgsql.jts.error.JtsParseException;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;

import static cyan.simple.pgsql.jts.JtsGeojson.COORDINATES;

/**
 * <p>LineStringParser</p>
 * @author Cyan (snow22314@outlook.com)
 * @version V.0.0.1
 * @group cyan.tool.kit
 * @date 11:25 2020/9/22
 */
public class LineStringParser extends JtsParser<LineString> {

    public LineStringParser(GeometryFactory geometryFactory) {
        super(geometryFactory);
    }

    public LineString parseLineString(JsonNode root) {
        Coordinate[] coordinates = PointParser.parseCoordinates(root.get(COORDINATES));
        return geometryFactory.createLineString(coordinates);
    }

    @Override
    public LineString parse(JsonNode node) throws JtsParseException {
        return parseLineString(node);
    }
}
