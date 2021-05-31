
---------------
-- 最小值函数 --
---------------
DROP FUNCTION IF EXISTS "public"."min_value"(int4[]);
CREATE OR REPLACE FUNCTION "public"."min_value"(arrays int4[])
  RETURNS int4
AS '

  -- 定义变量
DECLARE
  min_value int4 = 0x7fffffff;
  array_value int4;
BEGIN
  -- Routine body goes here...
  FOREACH array_value IN ARRAY arrays  LOOP
    IF min_value > array_value THEN
      min_value = array_value;
    END IF;
  END LOOP;
  RETURN min_value;
END
'
  LANGUAGE 'plpgsql' VOLATILE;

---------------
-- 最小值函数 --
---------------
DROP FUNCTION IF EXISTS "public"."min_value"(float8[]);
CREATE OR REPLACE FUNCTION "public"."min_value"(arrays float8[])
  RETURNS float8
AS '

  -- 定义变量
DECLARE
  min_value float8 = 1.79769313486231570E+308;
  array_value float8;
BEGIN
  -- Routine body goes here...
  FOREACH array_value IN ARRAY arrays  LOOP
    IF min_value > array_value THEN
      min_value = array_value;
    END IF;
  END LOOP;
  RETURN min_value;
END
'
  LANGUAGE 'plpgsql' VOLATILE;

---------------
-- 最大值函数 --
---------------
DROP FUNCTION IF EXISTS "public"."max_value"(int4[]);
CREATE OR REPLACE FUNCTION "public"."max_value"(arrays int4[])
  RETURNS int4
AS '

  -- 定义变量
DECLARE
  max_value int4 = 0x80000000;
  array_value int4;
BEGIN
  -- Routine body goes here...
  FOREACH array_value IN ARRAY arrays  LOOP
    IF max_value < array_value THEN
      max_value = array_value;
    END IF;
  END LOOP;
  RETURN max_value;
END
'
  LANGUAGE 'plpgsql' VOLATILE;


---------------
-- 最大值函数 --
---------------
DROP FUNCTION IF EXISTS "public"."max_value"(float8[]);
CREATE OR REPLACE FUNCTION "public"."max_value"(arrays float8[])
  RETURNS float8
AS '

  -- 定义变量
DECLARE
  max_value float8 = -1.79769313486231570E+308;
  array_value float8;
BEGIN
  -- Routine body goes here...
  FOREACH array_value IN ARRAY arrays  LOOP
    IF max_value < array_value THEN
      max_value = array_value;
    END IF;
  END LOOP;
  RETURN max_value;
END
'
  LANGUAGE 'plpgsql' VOLATILE;

---------------------
-- 随机2d点生成函数 --
---------------------
DROP FUNCTION IF EXISTS "public"."random_point"();
CREATE OR REPLACE FUNCTION "public"."random_point"()
RETURNS "public"."geometry"
AS '

BEGIN
-- Routine body goes here...
  RETURN st_makepoint(random()*180,random()*90);
END
'
  LANGUAGE 'plpgsql' VOLATILE;

-----------------------------
-- 限定范围 随机2d点生成函数 --
-----------------------------
DROP FUNCTION IF EXISTS "public"."random_point"(float8,float8,float8,float8);
CREATE OR REPLACE FUNCTION "public"."random_point"(min_x float8, max_x float8, min_y float8, max_y float8)
  RETURNS "public"."geometry"
AS '

  -- 定义变量
DECLARE
  x_point float8;
  y_point float8;
  x_min float8;
  x_max float8;
  y_min float8;
  y_max float8;

BEGIN
  -- Routine body goes here...
  x_min = min_value(ARRAY[abs(min_x),abs(max_x),180.0]);
  x_max = max_value(ARRAY[abs(min_x),abs(max_x)]);
  x_point = (random() * abs(x_max - x_min)) + x_min;
  y_min = min_value(ARRAY[abs(min_y),abs(max_y),90.0]);
  y_max = max_value(ARRAY[abs(min_y),abs(max_y)]);
  y_point = (random() * abs(y_max - y_min)) + y_min;
  RETURN st_makepoint(x_point,y_point);
END
'
  LANGUAGE 'plpgsql' VOLATILE;

----------------------------------
-- 限定box3d范围 随机2d点生成函数 --
----------------------------------

DROP FUNCTION IF EXISTS "public"."random_point"(box3d);
CREATE OR REPLACE FUNCTION "public"."random_point"(box_3d box3d)
  RETURNS "public"."geometry"
AS '

BEGIN
  -- Routine body goes here...
  RETURN random_point(st_xmin(box_3d),st_xmax(box_3d),st_ymin(box_3d),st_ymax(box_3d));
END
'
  LANGUAGE 'plpgsql' VOLATILE;

----------------------------------
-- 限定box2d范围 随机2d点生成函数 --
----------------------------------
DROP FUNCTION IF EXISTS "public"."random_point"(box2d);
CREATE OR REPLACE FUNCTION "public"."random_point"(box_2d box2d)
  RETURNS "public"."geometry"
AS '

BEGIN
  -- Routine body goes here...
  RETURN random_point(box3d(box_2d));
END
'
  LANGUAGE 'plpgsql' VOLATILE;

----------------------------------
-- 限定对角点范围 随机2d点生成函数 --
----------------------------------
DROP FUNCTION IF EXISTS "public"."random_point"(geometry,geometry);
CREATE OR REPLACE FUNCTION "public"."random_point"(point_start geometry, point_end geometry)
  RETURNS "public"."geometry"
AS '

BEGIN
  -- Routine body goes here...
  RETURN random_point(st_makebox2d(point_start,point_end));
END
'
  LANGUAGE 'plpgsql' VOLATILE;

--------------------------
-- 随机box2d范围生成函数 --
--------------------------
DROP FUNCTION IF EXISTS "public"."random_box"();
CREATE OR REPLACE FUNCTION "public"."random_box"()
  RETURNS "public"."box2d"
AS '

BEGIN
  -- Routine body goes here...
  RETURN st_makebox2d(random_point(),random_point());
END
'
  LANGUAGE 'plpgsql' VOLATILE;

--------------------------
-- 随机box2d范围生成函数 --
--------------------------

-- distance = 0.01 生成数据比例尺大概 约等 1：100m - 1：500m
-- distance = 0.1 生成数据比例尺大概 约等 1：500m - 1：1km
-- distance = 1.0 生成数据比例尺大概 约等 1：1km - 1：10km
-- distance = 10.0 生成数据比例尺大概 约等 1：10km - 1：100km
DROP FUNCTION IF EXISTS "public"."random_box"(float8);
CREATE OR REPLACE FUNCTION "public"."random_box"(distance float8)
  RETURNS "public"."box2d"
AS '

  -- 定义变量
DECLARE
  x_start float8;
  y_start float8;
  angle float8;
  x_offset float8;
  y_offset float8;
  x_end float8 default 0.0;
  y_end float8 default 0.0;
  count int4 default 0;

BEGIN
  -- Routine body goes here...
  angle = random() * 360;

  x_offset = cosd(angle) * distance;
  y_offset = sind(angle) * distance;

  WHILE x_end <= 0.0 OR x_end >= 180.0 OR y_end <= 0.0  OR y_end >= 90.0 LOOP
    x_start = random() * 180;
    y_start = random() * 90;
    x_end = x_start + x_offset;
    y_end = y_start + y_offset;
    count = count + 1;
    IF count > 10 THEN
      RAISE EXCEPTION ''the value of distance is too large!'';
    END IF;
  END LOOP;

  RETURN st_makebox2d(st_makepoint(x_start,y_start),st_makepoint(x_end,y_end));
END
'
  LANGUAGE 'plpgsql' VOLATILE;

--------------------------------
-- 限定box2d范围 随机点生成函数 --
--------------------------------
DROP FUNCTION IF EXISTS "public"."random_points"(box2d);
CREATE OR REPLACE FUNCTION "public"."random_points"(box_2d box2d)
  RETURNS "public"."geometry"[]
AS '

  -- 定义变量
DECLARE
  geometry_array "geometry"[];
  random_value float8;
  size int4;
BEGIN
  -- Routine body goes here...
  random_value = random() * 10;
  IF random_value < 3 THEN
    size = 3;
  ELSE
    size = round(random_value)::int4;
  END IF;
  FOR idx IN 1..size LOOP
    -- can do some processing here
    geometry_array[idx] = random_point(box_2d);
  END LOOP;
  RETURN geometry_array;
END
'
  LANGUAGE 'plpgsql' VOLATILE;

-----------------------------
-- 限定点数量 随机点生成函数 --
-----------------------------
DROP FUNCTION IF EXISTS "public"."random_points"(int4);
CREATE OR REPLACE FUNCTION "public"."random_points"(size int4 default 3)
  RETURNS "public"."geometry"[]
AS '

  -- 定义变量
DECLARE
  geometry_array "geometry"[];

BEGIN
  -- Routine body goes here...
  FOR idx IN 1..size LOOP
      -- can do some processing here
      geometry_array[idx] = random_point();
  END LOOP;
  RETURN geometry_array;
END
'
  LANGUAGE 'plpgsql' VOLATILE;

---------------------------------------
-- 限定点数量、box2d范围 随机点生成函数 --
---------------------------------------
DROP FUNCTION IF EXISTS "public"."random_points"(int4,box2d);
CREATE OR REPLACE FUNCTION "public"."random_points"(size int4,box_2d box2d)
  RETURNS "public"."geometry"[]
AS '

  -- 定义变量
DECLARE
  geometry_array "geometry"[];

BEGIN
  -- Routine body goes here...
  FOR idx IN 1..size LOOP
    -- can do some processing here
    geometry_array[idx] = random_point(box_2d);
  END LOOP;
  RETURN geometry_array;
END
'
  LANGUAGE 'plpgsql' VOLATILE;

-------------
-- 数组排序 --
-------------
DROP FUNCTION IF EXISTS "public"."sort_arrays"(float8[],text);
CREATE OR REPLACE FUNCTION "public"."sort_arrays"(arrays float8[],sort text default 'desc')
  RETURNS float8[]
AS '
  -- 定义变量
DECLARE
  temp float8;
  result float8[];
BEGIN
  -- Routine body goes here...
  IF lower(sort) = ''desc'' THEN
    FOR temp IN SELECT * FROM unnest(arrays) AS a ORDER BY a DESC LOOP
      result = array_append(result,temp::float8);
    END LOOP;
    RETURN result;
  ELSIF lower(sort) = ''asc'' THEN
    FOR temp IN SELECT * FROM unnest(arrays) AS a ORDER BY a ASC LOOP
      result = array_append(result,temp::float8);
    END LOOP;
    RETURN result;
  ELSE
    RAISE EXCEPTION ''sort must be asc or desc!'';
  END IF;

END
'
  LANGUAGE 'plpgsql' VOLATILE;

---------------
-- 随机点排序 --
---------------
DROP FUNCTION IF EXISTS "public"."sort_line_points"(geometry[]);
CREATE OR REPLACE FUNCTION "public"."sort_line_points"(geometries geometry[])
  RETURNS "public"."geometry"[]
AS '
  -- 定义变量
DECLARE
  array_size int4 = array_length(geometries,1);
  point point;
  point_xs float8[];
  point_ys float8[];
  result geometry[];
BEGIN
  -- Routine body goes here...

  FOR idx IN 1..array_size LOOP
    point = point(geometries[idx]);
    point_xs[idx] = point[0];
    point_ys[idx] = point[1];
  END LOOP;
  point_xs = sort_arrays(point_xs,''desc'');
  point_ys = sort_arrays(point_ys,''desc'');
  FOR idx IN 1..array_size LOOP
    result[idx] = st_makepoint(point_xs[idx],point_ys[idx]);
  END LOOP;
  return result;
END
'
  LANGUAGE 'plpgsql' VOLATILE;

---------------
-- 随机点排序 --
---------------
DROP FUNCTION IF EXISTS "public"."sort_polygon_points"(geometry[]);
CREATE OR REPLACE FUNCTION "public"."sort_polygon_points"(geometries geometry[])
  RETURNS "public"."geometry"[]
AS '
  -- 定义变量
DECLARE
  array_size int4 = array_length(geometries,1);
  point point;
  point3d geometry;
  points point[];
  point3ds_left geometry[];
  point3ds_right geometry[];
  point3ds_top geometry[];
  point3ds_bottom geometry[];
  point_xs float8[];
  point_ys float8[];
  max_x float8;
  min_x float8;
  max_y float8;
  min_y float8;
  center_x float8;
  center_y float8;
  angle float8;
  result geometry[];
BEGIN
  -- Routine body goes here...

  FOR idx IN 1..array_size LOOP
    point = point(geometries[idx]);
    points[idx] = point;
    point_xs[idx] = point[0];
    point_ys[idx] = point[1];
  END LOOP;
--   排序找出最大坐标及最小坐标
  point_xs = sort_arrays(point_xs,''desc'');
  point_ys = sort_arrays(point_ys,''desc'');
  max_x = point_xs[1];
  min_x = point_xs[array_size];
  max_y = point_ys[1];
  min_y = point_ys[array_size];
--   计算出中心点
  center_x = abs(max_x - min_x) / 2 + min_x;
  center_y = abs(max_y - min_y) / 2 + min_y;

  FOREACH point IN ARRAY points LOOP
    angle = atand((point[1] - center_y) / (point[0] - center_x));

    IF point[0] > center_x THEN
      point3ds_right = array_append(point3ds_right,st_makepoint(point[0],point[1],angle));
    ELSEIF point[0] < center_x THEN
      point3ds_left = array_append(point3ds_left,st_makepoint(point[0],point[1],angle));
    ELSEIF point[0] = center_x AND point[1] > center_y THEN
      point3ds_top = array_append(point3ds_top,st_makepoint(point[0],point[1],angle));
    ELSEIF point[0] = center_x AND point[1] < center_y THEN
      point3ds_bottom = array_append(point3ds_bottom,st_makepoint(point[0],point[1],angle));
    ELSE
      point3ds_bottom = array_append(point3ds_bottom,st_makepoint(point[0],point[1],angle));
    END IF;
  END LOOP;

  IF point3ds_top IS NOT NULL THEN
    FOR point3d IN SELECT * FROM unnest(point3ds_top) AS geometry ORDER BY st_z(geometry) DESC LOOP
      result = array_append(result,st_makepoint(st_x(point3d),st_y(point3d)));
    END LOOP;
  END IF;

  IF point3ds_right IS NOT NULL THEN
    FOR point3d IN SELECT * FROM unnest(point3ds_right) AS geometry ORDER BY st_z(geometry) DESC LOOP
      result = array_append(result,st_makepoint(st_x(point3d),st_y(point3d)));
    END LOOP;
  END IF;

  IF point3ds_bottom IS NOT NULL THEN
    FOR point3d IN SELECT * FROM unnest(point3ds_bottom) AS geometry ORDER BY st_z(geometry) DESC LOOP
      result = array_append(result,st_makepoint(st_x(point3d),st_y(point3d)));
    END LOOP;
  END IF;

  IF point3ds_left IS NOT NULL THEN
    FOR point3d IN SELECT * FROM unnest(point3ds_left) AS geometry ORDER BY st_z(geometry) DESC LOOP
      result = array_append(result,st_makepoint(st_x(point3d),st_y(point3d)));
    END LOOP;
  END IF;

  RETURN result;
END
'
  LANGUAGE 'plpgsql' VOLATILE;

-------------------
-- 随机线生成函数 --
-------------------
DROP FUNCTION IF EXISTS "public"."random_line"(bool);
CREATE OR REPLACE FUNCTION "public"."random_line"(straight bool default false)
  RETURNS "public"."geometry"
AS '

BEGIN
  -- Routine body goes here...
  IF straight THEN
    RETURN st_makeline(sort_line_points(random_points(round(random() * 10 + 2)::int4)));
  ELSE
    RETURN st_makeline(sort_polygon_points(random_points(round(random() * 10 + 2)::int4)));
  END IF;

END
'
  LANGUAGE 'plpgsql' VOLATILE;

-----------------------------
-- 限定点数量 随机线生成函数 --
-----------------------------
DROP FUNCTION IF EXISTS "public"."random_line"(int4,bool);
CREATE OR REPLACE FUNCTION "public"."random_line"(point_size int4, straight bool default false)
  RETURNS "public"."geometry"
AS '

BEGIN
  -- Routine body goes here...
  IF straight THEN
    RETURN st_makeline(sort_line_points(random_points(point_size)));
  ELSE
    RETURN st_makeline(sort_polygon_points(random_points(point_size)));
  END IF;
END
'
  LANGUAGE 'plpgsql' VOLATILE;

--------------------------------
-- 限定box2x范围 随机线生成函数 --
--------------------------------
DROP FUNCTION IF EXISTS "public"."random_line"(box2d,bool);
CREATE OR REPLACE FUNCTION "public"."random_line"(box_2d box2d, straight bool default false)
  RETURNS "public"."geometry"
AS '

BEGIN
  -- Routine body goes here...
  IF straight THEN
    RETURN st_makeline(sort_line_points(random_points(round(random() * 10 + 2)::int4,box_2d)));
  ELSE
    RETURN st_makeline(sort_polygon_points(random_points(round(random() * 10 + 2)::int4,box_2d)));
  END IF;
END
'
  LANGUAGE 'plpgsql' VOLATILE;

---------------------------------------
-- 限定点数量、box2x范围 随机线生成函数 --
---------------------------------------
DROP FUNCTION IF EXISTS "public"."random_line"(int4,box2d,bool);
CREATE OR REPLACE FUNCTION "public"."random_line"(point_size int4,box_2d box2d, straight bool default false)
  RETURNS "public"."geometry"
AS '

BEGIN
  -- Routine body goes here...
  IF straight THEN
    RETURN st_makeline(sort_line_points(random_points(point_size,box_2d)));
  ELSE
    RETURN st_makeline(sort_polygon_points(random_points(point_size,box_2d)));
  END IF;
END
'
  LANGUAGE 'plpgsql' VOLATILE;

---------------------------------------
-- 限定点数量、box2x范围 随机线生成函数 --
---------------------------------------
DROP FUNCTION IF EXISTS "public"."random_closed_line"();
CREATE OR REPLACE FUNCTION "public"."random_closed_line"()
  RETURNS "public"."geometry"
AS '

DECLARE
  points geometry[];
  array_size int4;
BEGIN
  -- Routine body goes here...
  points = sort_polygon_points(random_points());
  array_size = array_length(points,1);
  points[array_size + 1] = points[1];
  RETURN st_makeline(points);
END
'
  LANGUAGE 'plpgsql' VOLATILE;

---------------------------------------
-- 限定点数量、box2x范围 随机线生成函数 --
---------------------------------------
DROP FUNCTION IF EXISTS "public"."random_closed_line"(int4,box2d);
CREATE OR REPLACE FUNCTION "public"."random_closed_line"(point_size int4,box_2d box2d)
  RETURNS "public"."geometry"
AS '

DECLARE
  points geometry[];
  array_size int4;
BEGIN
  -- Routine body goes here...
  points = sort_polygon_points(random_points(point_size,box_2d));
  array_size = array_length(points,1);
  points[array_size + 1] = points[1];
  RETURN st_makeline(points);
END
'
  LANGUAGE 'plpgsql' VOLATILE;

-----------------------------
-- 限定点数量 随机线生成函数 --
-----------------------------
DROP FUNCTION IF EXISTS "public"."random_closed_line"(int4);
CREATE OR REPLACE FUNCTION "public"."random_closed_line"(point_size int4)
  RETURNS "public"."geometry"
AS '

DECLARE
  points geometry[];
  array_size int4;
BEGIN
  -- Routine body goes here...
  points = sort_polygon_points(random_points(point_size));
  array_size = array_length(points,1);
  points[array_size + 1] = points[1];
  RETURN st_makeline(points);
END
'
  LANGUAGE 'plpgsql' VOLATILE;

--------------------------------
-- 限定box2x范围 随机线生成函数 --
--------------------------------
DROP FUNCTION IF EXISTS "public"."random_closed_line"(box2d);
CREATE OR REPLACE FUNCTION "public"."random_closed_line"(box_2d box2d)
  RETURNS "public"."geometry"
AS '

DECLARE
  points geometry[];
  array_size int4;
BEGIN
  -- Routine body goes here...
  points = sort_polygon_points(random_points(box_2d));
  array_size = array_length(points,1);
  points[array_size + 1] = points[1];
  RETURN st_makeline(points);
END
'
  LANGUAGE 'plpgsql' VOLATILE;

---------------------------------------
-- 限定点数量、box2x范围 随机线生成函数 --
---------------------------------------
DROP FUNCTION IF EXISTS "public"."random_polygon"();
CREATE OR REPLACE FUNCTION "public"."random_polygon"()
  RETURNS "public"."geometry"
AS '

BEGIN
  -- Routine body goes here...
  RETURN st_makepolygon(random_closed_line());
END
'
  LANGUAGE 'plpgsql' VOLATILE;

---------------------------------------
-- 限定点数量、box2x范围 随机线生成函数 --
---------------------------------------
DROP FUNCTION IF EXISTS "public"."random_polygon"(box2d);
CREATE OR REPLACE FUNCTION "public"."random_polygon"(box_2d box2d)
  RETURNS "public"."geometry"
AS '

BEGIN
  -- Routine body goes here...
  RETURN st_makepolygon(random_closed_line(box_2d));
END
'
  LANGUAGE 'plpgsql' VOLATILE;


-----------------------------
-- 限定点数量 随机面生成函数 --
-----------------------------
DROP FUNCTION IF EXISTS "public"."random_polygon"(int4);
CREATE OR REPLACE FUNCTION "public"."random_polygon"(point_size int4)
  RETURNS "public"."geometry"
AS '

BEGIN
  -- Routine body goes here...
  RETURN st_makepolygon(random_closed_line(point_size));
END
'
  LANGUAGE 'plpgsql' VOLATILE;

---------------------------------------
-- 限定点数量、box2x范围 随机线生成函数 --
---------------------------------------
DROP FUNCTION IF EXISTS "public"."random_polygon"(int4,box2d);
CREATE OR REPLACE FUNCTION "public"."random_polygon"(point_size int4,box_2d box2d)
  RETURNS "public"."geometry"
AS '

BEGIN
  -- Routine body goes here...
  RETURN st_makepolygon(random_closed_line(point_size,box_2d));
END
'
  LANGUAGE 'plpgsql' VOLATILE;

----------------------------
-- 限定点数量随机线生成函数 --
----------------------------
DROP FUNCTION IF EXISTS "public"."random_geometry"();
CREATE OR REPLACE FUNCTION "public"."random_geometry"()
  RETURNS "public"."geometry"
AS '

DECLARE
  random_value float8;

BEGIN
  -- Routine body goes here...
  random_value = random() * 3;
  IF random_value >= 0 AND random_value <=1 THEN
    RETURN random_point();
  ELSEIF  random_value > 1 AND random_value <=2 THEN
    RETURN random_line();
  ELSE
    RETURN random_polygon();
  END IF;
END
'
  LANGUAGE 'plpgsql' VOLATILE;

----------------------------
-- 限定点数量随机线生成函数 --
----------------------------
DROP FUNCTION IF EXISTS "public"."random_geometry"(int4);
CREATE OR REPLACE FUNCTION "public"."random_geometry"(point_size int4)
  RETURNS "public"."geometry"
AS '

DECLARE
  random_value float8;

BEGIN
  -- Routine body goes here...
  random_value = random() * 3;
  IF random_value >= 0 AND random_value <=1 THEN
    RETURN random_point();
  ELSEIF  random_value > 1 AND random_value <=2 THEN
    RETURN random_line(point_size);
  ELSE
    RETURN random_polygon(point_size);
  END IF;
END
'
  LANGUAGE 'plpgsql' VOLATILE;

--------------------------------
-- 限定box2x范围 随机线生成函数 --
--------------------------------
DROP FUNCTION IF EXISTS "public"."random_geometry"(box2d);
CREATE OR REPLACE FUNCTION "public"."random_geometry"(box_2d box2d)
  RETURNS "public"."geometry"
AS '

DECLARE
  random_value float8;

BEGIN
  -- Routine body goes here...
  random_value = random() * 3;
  IF random_value >= 0 AND random_value <=1 THEN
    RETURN random_point(box_2d);
  ELSEIF  random_value > 1 AND random_value <=2 THEN
    RETURN random_line(box_2d);
  ELSE
    RETURN random_polygon(box_2d);
  END IF;
END
'
  LANGUAGE 'plpgsql' VOLATILE;

---------------------------------------
-- 限定点数量、box2x范围 随机线生成函数 --
---------------------------------------
DROP FUNCTION IF EXISTS "public"."random_geometry"(int4,box2d);
CREATE OR REPLACE FUNCTION "public"."random_geometry"(point_size int4,box_2d box2d)
  RETURNS "public"."geometry"
AS '

DECLARE
  random_value float8;

BEGIN
  -- Routine body goes here...
  random_value = random() * 3;
  IF random_value >= 0 AND random_value <=1 THEN
    RETURN random_point(box_2d);
  ELSEIF  random_value > 1 AND random_value <=2 THEN
    RETURN random_line(point_size,box_2d);
  ELSE
    RETURN random_polygon(point_size,box_2d);
  END IF;
END
'
  LANGUAGE 'plpgsql' VOLATILE;