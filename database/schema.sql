-- =============================================
-- SystemGui Database Schema (SQLite)
-- Run this file to create the database and tables.
-- Place user.db in your project root (same folder as the .jar or where the app runs).
-- =============================================

-- Users / Registration (login, admin, approval)
CREATE TABLE IF NOT EXISTS tbl_register (
    r_id       INTEGER PRIMARY KEY AUTOINCREMENT,
    f_name     TEXT NOT NULL,
    l_name     TEXT NOT NULL,
    email      TEXT NOT NULL,
    username   TEXT NOT NULL,
    password   TEXT NOT NULL,
    user_type  TEXT NOT NULL,  -- 'admin' or 'user'
    status     TEXT NOT NULL    -- 'active' or 'pending'
);

-- Products
CREATE TABLE IF NOT EXISTS tbl_products (
    p_id            INTEGER PRIMARY KEY AUTOINCREMENT,
    product_name    TEXT NOT NULL,
    price           REAL,
    quantity        INTEGER,
    expiration_date TEXT,
    status          TEXT
);

-- Orders (per transaction)
CREATE TABLE IF NOT EXISTS tbl_orders (
    order_id   INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id    INTEGER,
    total      REAL,
    cash       REAL,
    change_amt REAL,
    order_date TEXT
);

-- Order line items
CREATE TABLE IF NOT EXISTS tbl_order_items (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    order_id    INTEGER,
    product_id  INTEGER,
    product_name TEXT,
    qty         INTEGER,
    price       REAL,
    subtotal    REAL
);

-- =============================================
-- Sample data
-- =============================================

-- Default admin (email: admin@admin.com, password: admin)
INSERT OR IGNORE INTO tbl_register (r_id, f_name, l_name, email, username, password, user_type, status)
VALUES (1, 'Admin', 'User', 'admin@admin.com', 'admin', 'admin', 'admin', 'active');

-- Default products (Strawberries, Pineapple, Orange, Apple)
INSERT OR IGNORE INTO tbl_products (product_name, price, quantity, expiration_date, status) VALUES
('Strawberries', 50.00, 100, NULL, 'available'),
('Pineapple',     80.00, 100, NULL, 'available'),
('Orange',        30.00, 100, NULL, 'available'),
('Apple',         40.00, 100, NULL, 'available');
