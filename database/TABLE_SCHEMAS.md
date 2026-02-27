# Database table schemas (like tbl_register)

Use these to create each table in your SQLite database (e.g. DB Browser, DBeaver).

---

## 1. tbl_register (users / login)

| Column     | Data type | Primary key | Notes        |
|------------|-----------|-------------|--------------|
| r_id       | INTEGER   | Yes         | Auto-increment ID |
| f_name     | TEXT      | —           | First name   |
| l_name     | TEXT      | —           | Last name    |
| email      | TEXT      | —           | Email        |
| username   | TEXT      | —           | Username     |
| password   | TEXT      | —           | Password     |
| user_type  | TEXT      | —           | `admin` or `user` |
| status     | TEXT      | —           | `active` or `pending` |

---

## 2. tbl_products

| Column         | Data type | Primary key | Notes        |
|----------------|-----------|-------------|--------------|
| p_id           | INTEGER   | Yes         | Auto-increment ID |
| product_name   | TEXT      | —           | Product name |
| price          | REAL      | —           | Unit price   |
| quantity       | INTEGER   | —           | Stock qty    |
| expiration_date| TEXT      | —           | Optional     |
| status         | TEXT      | —           | e.g. `available` |

---

## 3. tbl_orders

| Column    | Data type | Primary key | Notes        |
|-----------|-----------|-------------|--------------|
| order_id | INTEGER   | Yes         | Auto-increment ID |
| user_id  | INTEGER   | —           | From tbl_register.r_id |
| total    | REAL      | —           | Order total  |
| cash      | REAL      | —           | Cash tendered |
| change_amt | REAL    | —           | Change given |
| order_date | TEXT    | —           | Date/time    |

---

## 4. tbl_order_items

| Column       | Data type | Primary key | Notes        |
|--------------|-----------|-------------|--------------|
| id           | INTEGER   | Yes         | Auto-increment ID |
| order_id     | INTEGER   | —           | From tbl_orders.order_id |
| product_id   | INTEGER   | —           | From tbl_products.p_id |
| product_name | TEXT      | —           | Snapshot of name |
| qty          | INTEGER   | —           | Quantity ordered |
| price        | REAL      | —           | Unit price at order time |
| subtotal     | REAL      | —           | qty × price  |

---

## Quick reference

- **tbl_register** – users (login, admin/user, active/pending).
- **tbl_products** – products (Strawberries, Pineapple, Orange, Apple, etc.).
- **tbl_orders** – one row per checkout (total, cash, change, date).
- **tbl_order_items** – one row per product line in each order.

All primary key columns are INTEGER and should be set as **Primary Key** and **Autoincrement** in your database tool (like your `r_id` in tbl_register).
