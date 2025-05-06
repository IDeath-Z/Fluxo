CREATE TABLE product_info (
    id SERIAL PRIMARY KEY, 
    product_name TEXT NOT NULL,
    product_description TEXT NOT NULL,
    product_SKU TEXT NOT NULL UNIQUE,
    product_category TEXT NOT NULL,
    product_brand TEXT,
    product_model TEXT
);

CREATE TABLE price_info (
    id SERIAL PRIMARY KEY,
    product_id INTEGER NOT NULL REFERENCES product_info(id) ON DELETE CASCADE,
    product_price DECIMAL(10,2) NOT NULL,
    promotional_price DECIMAL(10,2)
);

CREATE TABLE technical_info (
    id SERIAL PRIMARY KEY,
    product_id INTEGER NOT NULL REFERENCES product_info(id) ON DELETE CASCADE,
    product_weight DOUBLE PRECISION,
    product_length DECIMAL(10,2),
    product_width DECIMAL(10,2),
    product_height DECIMAL(10,2)
);