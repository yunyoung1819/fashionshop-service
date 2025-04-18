-- 애플리케이션 실행 시 테스트를 위한 초기 상품 데이터를 넣어주는 data.sql

INSERT INTO brand (name)
VALUES ('A'),
       ('B'),
       ('C'),
       ('D'),
       ('E'),
       ('F'),
       ('G'),
       ('H'),
       ('I');


INSERT INTO category (name)
VALUES ('상의'),
       ('아우터'),
       ('바지'),
       ('스니커즈'),
       ('가방'),
       ('모자'),
       ('양말'),
       ('액세서리');


-- A 브랜드 데이터
INSERT INTO product (brand_id, category_id, price)
VALUES (1, 1, 11200),
       (1, 2, 5500),
       (1, 3, 4200),
       (1, 4, 9000),
       (1, 5, 2000),
       (1, 6, 1700),
       (1, 7, 1800),
       (1, 8, 2300);

-- B 브랜드 데이터
INSERT INTO product (brand_id, category_id, price)
VALUES (2, 1, 10500),
       (2, 2, 5900),
       (2, 3, 3800),
       (2, 4, 9100),
       (2, 5, 2100),
       (2, 6, 2000),
       (2, 7, 2000),
       (2, 8, 2200);

-- C 브랜드 데이터
INSERT INTO product (brand_id, category_id, price)
VALUES (3, 1, 10000),
       (3, 2, 6200),
       (3, 3, 3300),
       (3, 4, 9200),
       (3, 5, 2200),
       (3, 6, 1900),
       (3, 7, 2200),
       (3, 8, 2100);

-- D 브랜드 데이터
INSERT INTO product (brand_id, category_id, price)
VALUES (4, 1, 10100),
       (4, 2, 5100),
       (4, 3, 3000),
       (4, 4, 9500),
       (4, 5, 2500),
       (4, 6, 1500),
       (4, 7, 2400),
       (4, 8, 2000);

-- E 브랜드 데이터
INSERT INTO product (brand_id, category_id, price)
VALUES (5, 1, 10700),
       (5, 2, 5000),
       (5, 3, 3800),
       (5, 4, 9900),
       (5, 5, 2300),
       (5, 6, 1800),
       (5, 7, 2100),
       (5, 8, 2100);

-- F 브랜드 데이터
INSERT INTO product (brand_id, category_id, price)
VALUES (6, 1, 11200),
       (6, 2, 7200),
       (6, 3, 4000),
       (6, 4, 9300),
       (6, 5, 2100),
       (6, 6, 1600),
       (6, 7, 2300),
       (6, 8, 1900);

-- G 브랜드 데이터
INSERT INTO product (brand_id, category_id, price)
VALUES (7, 1, 10500),
       (7, 2, 5800),
       (7, 3, 3900),
       (7, 4, 9000),
       (7, 5, 2200),
       (7, 6, 1700),
       (7, 7, 2100),
       (7, 8, 2000);

-- H 브랜드 데이터
INSERT INTO product (brand_id, category_id, price)
VALUES (8, 1, 10800),
       (8, 2, 6300),
       (8, 3, 3100),
       (8, 4, 9700),
       (8, 5, 2100),
       (8, 6, 1600),
       (8, 7, 2000),
       (8, 8, 2000);

-- I 브랜드 데이터
INSERT INTO product (brand_id, category_id, price)
VALUES (9, 1, 11400),
       (9, 2, 6700),
       (9, 3, 3200),
       (9, 4, 9500),
       (9, 5, 2400),
       (9, 6, 1700),
       (9, 7, 1700),
       (9, 8, 2400);