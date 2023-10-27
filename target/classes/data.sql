--Users
INSERT INTO APP_USERS (NAME, SUBNAME, EMAIL, PASSWORD) VALUES
('ARTHUR', 'MORGAN', 'EMAIL1@GMAIL.COM', '1234')
ON CONFLICT (email)
DO NOTHING;

INSERT INTO APP_USERS (NAME, SUBNAME, EMAIL, PASSWORD) VALUES
('Lerois', 'Jenkis', 'EMAIL2@GMAIL.COM', '1234')
ON CONFLICT (email)
DO NOTHING;

--Categories
INSERT INTO CATEGORIES (NAME) VALUES
('Category1')
ON CONFLICT (name)
DO NOTHING;
INSERT INTO CATEGORIES (NAME) VALUES
('Category2')
ON CONFLICT (name)
DO NOTHING;
INSERT INTO CATEGORIES (NAME) VALUES
('Category3')
ON CONFLICT (name)
DO NOTHING;

--Products
INSERT INTO PRODUCTS (TITLE, PRICE, BRAND, COLOR, WEIGHT, VOLUME, STOCK, CATEGORY_ID) VALUES
('Product1', 10, 'Adidas', 'Black',184, '732x179x494 mm', 10, 1)
ON CONFLICT (TITLE)
DO NOTHING;

INSERT INTO PRODUCTS (TITLE, PRICE, BRAND, COLOR, WEIGHT, VOLUME, STOCK, CATEGORY_ID) VALUES
('Product2', 1, 'Nike', 'White',184, '732x179x494 mm', 10, 1)
ON CONFLICT (TITLE)
DO NOTHING;

INSERT INTO PRODUCTS (TITLE, PRICE, BRAND, COLOR, WEIGHT, VOLUME, STOCK, CATEGORY_ID) VALUES
('Product3', 1, 'Nike', 'White',184, '732x179x494 mm', 10, 2)
ON CONFLICT (TITLE)
DO NOTHING;

INSERT INTO PRODUCTS (TITLE, PRICE, BRAND, COLOR, WEIGHT, VOLUME, STOCK, CATEGORY_ID) VALUES
('Product4', 1, 'Nike', 'White',184, '732x179x494 mm', 10, 3)
ON CONFLICT (TITLE)
DO NOTHING;