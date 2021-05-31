

DROP FUNCTION IF EXISTS "public"."random_jsonb"(text[],int4[]);
CREATE OR REPLACE FUNCTION "public"."random_jsonb"(name_array text[],id_array int4[])
  RETURNS jsonb
AS $BODY$

DECLARE
  array_size int4 = array_length(name_array,1);
  code_item int4;
  name_item text;
  level_item int4;
  desc_item text;
  price_item float8;
  other_item text;
  value_array text[];
  result jsonb;
BEGIN
  -- Routine body goes here...
  -- ARRAY['320100','风扇','3','新型摆头电风扇','31.35']
  -- 固定长度的数组下标从0开始，随机数组的下标从1开始，Postgres本身bug
  FOR idx IN 0..(array_size - 1) LOOP
    IF idx = 0 THEN
      code_item = ceil(random()*(10000-1)+1) AS num FROM generate_series(1,1);
      value_array = array_append(value_array,code_item::TEXT);
    ELSEIF idx = 1 THEN
      name_item = SUBSTRING(md5(random()::VARCHAR),2,8);
      value_array = array_append(value_array,name_item::TEXT);
    ELSEIF idx = 2 THEN
      level_item = round(random()*(5-1)+1)::INT4;
      value_array = array_append(value_array,level_item::TEXT);
    ELSEIF idx = 3 THEN
      desc_item = substring(md5(random()::varchar),2,20);
      value_array = array_append(value_array,desc_item::text);
    ELSEIF idx = 4 THEN
      price_item = CAST(random()*(1000-1)+1 AS DECIMAL(18,2));
      value_array = array_append(value_array,price_item::text);
    ELSE
      other_item = substring(md5(random()::varchar),2,20);
      value_array = array_append(value_array,other_item::text);
    END IF;
  END LOOP;

  result = json_object_agg(n."name",
                json_build_object('id',i."id",'value',v."value")
          ) FROM (
            SELECT row_number() OVER() AS idx_name, * FROM unnest(name_array) AS "name"
          ) AS n
          LEFT JOIN (
            SELECT row_number() OVER() AS idx_id, *  FROM unnest(id_array) AS "id"
          ) AS i
          ON idx_name = idx_id
          LEFT JOIN (
            SELECT row_number() over() as idx_value, *  FROM unnest(value_array) AS "value"
          ) AS v
          ON idx_name = idx_value;
  RETURN result;
END
$BODY$
  LANGUAGE 'plpgsql' VOLATILE;


----------------------------------
-- 随机生成特定格式的jsonb数据函数 --
----------------------------------
-- {
--   "code": {
--     "id": "1",
--     "value": "320100"
--   },
--   "name": {
--     "id": "2",
--     "value": "风扇"
--   },
--   "level": {
--     "id": "3",
--     "value": "3"
--   },
--   "desc": {
--     "id": "4",
--     "value": "新型摆头电风扇"
--   },
--   "price": {
--     "id": "5",
--     "value": "31.35"
--   }
-- }
DROP FUNCTION IF EXISTS "public"."random_jsonb"();
CREATE OR REPLACE FUNCTION "public"."random_jsonb"()
  RETURNS jsonb
AS $BODY$

DECLARE
  name_array text[] = ARRAY['code','name','level','desc','price'];
  id_array int4[] = ARRAY[1,2,3,4,5];
BEGIN
  RETURN random_jsonb(name_array,id_array);
END
$BODY$
  LANGUAGE 'plpgsql' VOLATILE;