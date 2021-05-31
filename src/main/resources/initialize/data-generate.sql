

DROP FUNCTION IF EXISTS "public"."generate_simulated_data"();
CREATE OR REPLACE FUNCTION "public"."generate_simulated_data"()
RETURNS VOID
AS '

  -- 定义变量
  DECLARE
    -- 全局数据产生时间
    _now TIMESTAMPTZ = now();

    -- 延后 30 second 的历史位置产生时间
    _now_thing TIMESTAMPTZ = _now - interval ''30 second'';

  BEGIN
    -- Routine body goes here...

    WITH "thing_simulated_data"
           AS
           (SELECT
              EXTRACT(epoch FROM generate_series(_now_thing - interval ''24 day'', _now_thing, interval ''5 minute'')) AS "id",
              SUBSTRING(md5(random()::VARCHAR),2,8) AS "name",
              SUBSTRING(md5(random()::VARCHAR),2,20) AS "description",
              random_jsonb() AS "properties",
              SUBSTRING(md5(random()::VARCHAR),2,4) AS "type",
              random_geometry() AS "location",
              generate_series(_now_thing - interval ''24 day'', _now_thing, interval ''5 minute'') AS "time",
              SUBSTRING(md5(random()::VARCHAR),2,50) AS "data",
              generate_series(_now_thing - interval ''24 day'', _now_thing, interval ''5 minute'') AS "create_time",
              generate_series(_now_thing - interval ''24 day'', _now_thing, interval ''5 minute'') AS "update_time"
           )
    INSERT INTO "public"."thing"(
      "id",
      "name",
      "description",
      "properties",
      "type",
      "location",
      "time",
      "data",
      "create_time",
      "update_time"
    ) SELECT "id",
             "name",
             "description",
             "properties",
             "type",
             "location",
             "time",
             "data",
             "create_time",
             "update_time"
    FROM "thing_simulated_data";

  RETURN;
END
'
  LANGUAGE 'plpgsql' VOLATILE;

SELECT generate_simulated_data();