# Database Setup

## Option 1: Create database from command line (SQLite3)

1. Open a terminal in the **project root** (where your `src` folder is, e.g. `SystemGui`).
2. Run:

```bash
sqlite3 user.db < database/schema.sql
```

This creates `user.db` in the project root. The app looks for `jdbc:sqlite:user.db` (see `config.config`), so `user.db` must be in the **current working directory** when you run the app (usually the project root).

## Option 2: Using a SQLite GUI (DB Browser, DBeaver, etc.)

1. Create a new SQLite database and save it as `user.db` in your project root.
2. Open the **Execute SQL** / **Run SQL** tab.
3. Paste in the contents of `schema.sql` and run it.

## Tables created

| Table            | Purpose                          |
|-----------------|-----------------------------------|
| tbl_register    | Users (login, admin, approval)     |
| tbl_products    | Products (name, price, quantity)  |
| tbl_orders      | Orders (total, cash, change, date)|
| tbl_order_items | Order line items                 |

## Sample data included

- **Admin login:** email `admin@admin.com`, password `admin`
- **Products:** Strawberries (50), Pineapple (80), Orange (30), Apple (40), each quantity 100

After creating `user.db`, run your application from the project root so it finds `user.db`.
