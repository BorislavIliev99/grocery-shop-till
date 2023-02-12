INSERT INTO grocery_shop_till.admins (username, password) VALUES ('borislav', '$2a$10$kZEt3wR0XooYuZB8laBUYueHwOz2h653oXS6dkR9pbkLBADtj4ESW');

INSERT INTO grocery_shop_till.products (name, price) VALUES ('apple', 0.5);
INSERT INTO grocery_shop_till.products (name, price) VALUES ('banana', 0.4);
INSERT INTO grocery_shop_till.products (name, price) VALUES ('tomato', 0.3);
INSERT INTO grocery_shop_till.products (name, price) VALUES ('potato', 0.26);

INSERT INTO grocery_shop_till.deals (product_id, deal_type) VALUES (1, 'TWO_FOR_THREE');
INSERT INTO grocery_shop_till.deals (product_id, deal_type) VALUES (2, 'TWO_FOR_THREE');
INSERT INTO grocery_shop_till.deals (product_id, deal_type) VALUES (3, 'TWO_FOR_THREE');
INSERT INTO grocery_shop_till.deals (product_id, deal_type) VALUES (4, 'BUY_ONE_GET_ONE_HALF_PRICE');
