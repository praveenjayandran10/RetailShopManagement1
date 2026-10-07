USE retail_shop;

SELECT * FROM sales ORDER BY sale_id DESC;
SELECT * FROM sale_items ORDER BY sale_item_id DESC;
SELECT * FROM payments ORDER BY payment_id DESC;

SELECT product_id, name, stock_qty
FROM products;