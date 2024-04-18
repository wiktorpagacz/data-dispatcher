create sequence IF NOT EXISTS FLATOFFERIDSEQUENCE START 1 INCREMENT 1;

CREATE TABLE IF NOT EXISTS flat_offer(
    "id" bigint NOT NULL DEFAULT nextval('FLATOFFERIDSEQUENCE') PRIMARY KEY,
    "title" varchar(255),
    "source" varchar(255),
    "link" varchar(255),
    "comment" varchar(255),
    "price" integer,
    "original_price" integer,
    "space" double precision,
    "address" varchar(255),
    "insert_date" timestamp,
    "last_modified_time" timestamp,
    "write_to_docs" char(1),
    "send_by_email" char(1),
    "write_to_docs_time" timestamp,
    "send_by_email_time" timestamp,
    "kafka_send" char(1)
    );

create index IF NOT EXISTS idx_offer_link
ON flat_offer(link);
