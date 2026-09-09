CREATE DATABASE IF NOT EXISTS wishstore_db
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE wishstore_db;

CREATE TABLE IF NOT EXISTS products (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(150)   NOT NULL,
    description VARCHAR(500)   NOT NULL,
    price       DECIMAL(10,2)  NOT NULL,
    stock       INT            NOT NULL,
    image       VARCHAR(500)
);

CREATE TABLE IF NOT EXISTS wishlists (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id  BIGINT         NOT NULL,
    quantity    INT            NOT NULL,
    created_at  DATETIME       NOT NULL,
    CONSTRAINT fk_wishlist_product FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE IF NOT EXISTS wishlist_history (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    wishlist_id BIGINT         NOT NULL,
    product_id  BIGINT         NOT NULL,
    action      VARCHAR(20)    NOT NULL,
    description VARCHAR(500)   NOT NULL,
    created_at  DATETIME       NOT NULL
);

INSERT INTO products (name, description, price, stock, image) VALUES
('Teclado mecánico RGB', 'Teclado mecánico switches rojos, retroiluminado', 189900.00, 15, 'https://picsum.photos/seed/keyboard/300'),
('Mouse inalámbrico', 'Mouse ergonómico 2.4GHz, 6 botones', 79900.00, 0, 'https://picsum.photos/seed/mouse/300'),
('Monitor 27" 144Hz', 'Panel IPS, 2K, HDR', 1299900.00, 8, 'https://picsum.photos/seed/monitor/300'),
('Audífonos Bluetooth', 'Cancelación de ruido activa', 349900.00, 20, 'https://picsum.photos/seed/headphones/300');