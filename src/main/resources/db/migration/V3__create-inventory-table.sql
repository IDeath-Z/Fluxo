CREATE TABLE supplier (
    id SERIAL PRIMARY KEY,
    supplier_name TEXT NOT NULL
);

CREATE TABLE lot_info (
    id SERIAL PRIMARY KEY,
    product_id INTEGER NOT NULL REFERENCES product_info(id) ON DELETE CASCADE,
    supplier_id INTEGER REFERENCES supplier(id),
    lot_code TEXT NOT NULL,
    expiry_date DATE NOT NULL,
    remaining_quantity INTEGER NOT NULL,
    lot_location TEXT
);

CREATE TABLE lot_operation (
    id SERIAL PRIMARY KEY, 
    lot_id INTEGER NOT NULL REFERENCES lot_info(id) ON DELETE CASCADE,
    movement_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    movement_amount INTEGER NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    notes TEXT,
    movement_type VARCHAR(20) NOT NULL
);

CREATE TABLE reserves (
    id SERIAL PRIMARY KEY,
    reserve_movement_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    reserve_observations TEXT
);