DROP TABLE IF EXISTS products CASCADE;
DROP INDEX IF EXISTS product_index;

CREATE TABLE IF NOT EXISTS products
(
    product_id  TEXT          NOT NULL UNIQUE,
    category_id BIGINT          NOT NULL,
    name        VARCHAR(200)  NOT NULL UNIQUE,
    description VARCHAR(1000) NOT NULL UNIQUE,
    count       INTEGER        NOT NULL DEFAULT 0,
    price       DOUBLE PRECISION,
    hide        BOOLEAN       NOT NULL DEFAULT true,
    CONSTRAINT products_pkey PRIMARY KEY (product_id)
);


CREATE INDEX product_index ON products (product_id);