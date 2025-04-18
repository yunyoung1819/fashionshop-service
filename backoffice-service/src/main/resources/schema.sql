-- src/main/resources/schema.sql

CREATE TABLE brand
(
    brand_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(255) NOT NULL
);

CREATE TABLE category
(
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL
);

CREATE TABLE product
(
    product_id  BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand_id    BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    price       INT,
    FOREIGN KEY (brand_id) REFERENCES brand (brand_id),
    FOREIGN KEY (category_id) REFERENCES category (category_id)
);
