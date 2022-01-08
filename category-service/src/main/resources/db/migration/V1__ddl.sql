DROP TABLE IF EXISTS categories CASCADE;
DROP INDEX IF EXISTS category_index;

CREATE TABLE IF NOT EXISTS categories
(
    category_id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE
);

CREATE INDEX category_index ON categories(category_id);