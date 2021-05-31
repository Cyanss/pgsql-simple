package cyan.simple.pgsql.jts.parser;

import com.fasterxml.jackson.databind.JsonNode;
import cyan.simple.pgsql.jts.JtsParser;
import cyan.simple.pgsql.jts.error.JtsParseException;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.MultiPoint;

import static cyan.simple.pgsql.jts.JtsGeojson.COORDINATES;

/**
 * <p>MultiPointParser</p>
 * @author Cyan (snow22314@outlook.com)
 * @version V.0.0.1
 * @group cyan.tool.kit
 * @date 14:41 2020/9/22
 */
public class MultiPointParser extends JtsParser<MultiPoint> {

    public MultiPointParser(GeometryFactory geometryFactory) {
        super(geometryFactory);
    }

    public MultiPoint parseMultiPoint(JsonNode root) {
        Coordinate[] coordinates = PointParser.parseCoordinates(root.get(COORDINATES));
        return geometryFactory.createMultiPointFromCoords(coordinates);
    }

    @Override
    public MultiPoint parse(JsonNode node) throws JtsParseException {
        return parseMultiPoint(node);
    }
}
