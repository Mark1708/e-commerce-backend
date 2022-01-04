DROP TABLE IF EXISTS categories CASCADE;
DROP INDEX IF EXISTS category_index;

CREATE TABLE IF NOT EXISTS categories
(
    category_id text NOT NULL UNIQUE,
    name text NOT NULL UNIQUE,
    CONSTRAINT categories_pkey PRIMARY KEY (category_id)
);

CREATE INDEX category_index ON categories(category_id);