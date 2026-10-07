USE retail_shop;

CREATE TABLE stock_entries (
    stock_entry_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    employee_id INT NOT NULL,
    quantity INT NOT NULL,
    entry_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    notes VARCHAR(255),

    FOREIGN KEY (product_id)
        REFERENCES products(product_id),

    FOREIGN KEY (employee_id)
        REFERENCES employees(employee_id),

    CHECK (quantity > 0)
);