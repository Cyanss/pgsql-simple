
CREATE EXTENSION IF NOT EXISTS "postgis" CASCADE;

DROP TABLE IF EXISTS "public"."thing";
CREATE TABLE "public"."thing"(
   "id" int8 NOT NULL ,
   "name" VARCHAR(64) COLLATE "default",
   "description" VARCHAR(512) COLLATE "default",
   "properties" jsonb,
   "type" VARCHAR(50) COLLATE "default",
   "location" GEOMETRY,
   "time" TIMESTAMPTZ,
   "data" TEXT,
   "create_time" TIMESTAMPTZ,
   "update_time" TIMESTAMPTZ,
   PRIMARY KEY ("id")
);
COMMENT ON COLUMN "public"."thing"."id" IS 'id主键';
COMMENT ON COLUMN "public"."thing"."name" IS '名称';
COMMENT ON COLUMN "public"."thing"."description" IS '描述';
COMMENT ON COLUMN "public"."thing"."properties" IS '属性';
COMMENT ON COLUMN "public"."thing"."type" IS '类型 数据';
COMMENT ON COLUMN "public"."thing"."location" IS '位置';
COMMENT ON COLUMN "public"."thing"."time" IS '时间';
COMMENT ON COLUMN "public"."thing"."data" IS '数据';
COMMENT ON COLUMN "public"."thing"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."thing"."update_time" IS '更新时间';

CREATE INDEX "IDX_THING_NAME" ON "public"."thing" USING btree (
  "name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops"
);

CREATE INDEX "IDX_THING_PROPERTIES" ON "public"."thing" USING gin (
  (properties -> 'value'::text) "pg_catalog"."jsonb_ops"
);
