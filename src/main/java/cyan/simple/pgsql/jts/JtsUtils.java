package cyan.simple.pgsql.jts;

import cyan.simple.pgsql.jts.error.JtsBoxInvalidException;
import cyan.simple.pgsql.jts.error.JtsParseException;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Geometry;

/**
 * <p>JtsUtils</p>
 * @author Cyan (snow22314@outlook.com)
 * @version V.0.0.1
 * @group cyan.tool.kit
 * @date 16:52 2020/9/18
 */
@Slf4j
public class JtsUtils {

    public static String parserWkt(JtsBox box) {
        try {
            return JtsHelper.parserWkt(box);
        } catch (JtsBoxInvalidException exception) {
            exception.printStackTrace();
            log.error(exception.getMessage());
        }
        return null;
    }

    public static String parserGeojson(JtsBox box) {
        try {
            return JtsHelper.parserGeojson(box);
        } catch (JtsParseException exception) {
            exception.printStackTrace();
            log.error(exception.getMessage());
        }
        return null;
    }

    public static JtsBox parserBox(String WktString) {
        try {
            return JtsHelper.parserBox(WktString);
        } catch (JtsParseException exception) {
            exception.printStackTrace();
            log.error(exception.getMessage());
        }
        return null;
    }

    public static JtsBox parserBox(Geometry geometry) {
        try {
            return JtsHelper.parserBox(geometry);
        } catch (JtsParseException exception) {
            exception.printStackTrace();
            log.error(exception.getMessage());
        }
        return null;
    }

    public static String parserGeojson(Geometry geometry) {
        try {
            return JtsHelper.parserGeojson(geometry);
        } catch (JtsParseException exception) {
            exception.printStackTrace();
            log.error(exception.getMessage());
        }
        return null;
    }

    public static Geometry parserGeometry(byte[] WkbBytes) {
        try {
            return JtsHelper.parserGeometry(WkbBytes);
        } catch (JtsParseException exception) {
            exception.printStackTrace();
            log.error(exception.getMessage());
        }
        return null;
    }

    public static String parserWkt(byte[] WkbBytes) {
        try {
            return JtsHelper.parserWkt(WkbBytes);
        } catch (JtsParseException exception) {
            exception.printStackTrace();
            log.error(exception.getMessage());
        }
        return null;
    }

    public static byte[] parserWkb(Geometry geometry) {
        try {
            return JtsHelper.parserWkb(geometry);
        } catch (JtsParseException exception) {
            exception.printStackTrace();
            log.error(exception.getMessage());
        }
        return null;
    }

    public static String parserWkt(Geometry geometry) {
        try {
            return JtsHelper.parserWkt(geometry);
        } catch (JtsParseException exception) {
            exception.printStackTrace();
            log.error(exception.getMessage());
        }
        return null;
    }

    public static Geometry parserGeometry(String WktString) {
        try {
            return JtsHelper.parserGeometry(WktString);
        } catch (JtsParseException exception) {
            exception.printStackTrace();
            log.error(exception.getMessage());
        }
        return null;
    }

    public static byte[] parserWkb(String WktString) {
        try {
            return JtsHelper.parserWkb(WktString);
        } catch (JtsParseException exception) {
            exception.printStackTrace();
            log.error(exception.getMessage());
        }
        return null;
    }

    public static Geometry parserGeojson(String geojson) {
        try {
            return JtsHelper.parserGeojson(geojson);
        } catch (JtsParseException exception) {
            exception.printStackTrace();
            log.error(exception.getMessage());
        }
        return null;
    }
}
