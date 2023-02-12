create schema if not exists grocery_shop_till;

use grocery_shop_till;

CREATE TABLE products
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(255) NOT NULL UNIQUE,
    price   DOUBLE NOT NULL
);

CREATE TABLE deals
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    product_id  INT NOT NULL,
    deal_type   VARCHAR(255) NOT NULL,

    CONSTRAINT deals_products
        FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE

);

CREATE TABLE admins
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(255) NOT NULL,
    password    VARCHAR(60) NOT NULL
);