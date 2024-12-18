# Fashion Shop Service

## 프로젝트 개요
**Fashion Shop Service**는 패션 상품 구매를 위한 서비스를 제공하는 웹 애플리케이션입니다. 고객과 관리자를 위한 다양한 기능을 제공합니다.

### 사용 기술
- Java: JDK 17 (Amazon Corretto)
- Spring Boot
- Datasource
  - H2 Database
- Cache
  - Redis

### 프로젝트 구조
이 프로젝트는 `core`, `customer-service`, `backoffice-service`세 개의 모듈로 구성되어 있으며, 각 모듈의 역할은 아래와 같습니다.
- **Core**: 공통적으로 사용되는 엔티티와 로직을 포함합니다.
- **Customer Service**: 유저(고객)이 사용하는 API를 제공하는 서비스로 최저가 상품의 가격 조회 등의 기능을 제공합니다.
- **Backoffice Service**: 관리자가 사용하는 API를 제공하는 서비스로 상품 등록, 수정, 삭제와 같은 관리 기능을 제공합니다.

```mermaid
graph TD;
    core[Core Module] --> customer[customer-service]
    core[Core Module] --> backoffice[backoffice-service]
    subgraph "Main Modules"
        customer[customer-service] 
        backoffice[backoffice-service] 
    end
```

---

## 주요 기능
1. **카테고리 별 최저 가격 조회 API**
   - 고객이 각 카테고리의 최저 가격 상품과 브랜드를 확인할 수 있습니다.
   - 총액 계산 기능도 포함되어 있습니다.
2. **단일 브랜드의 최저 가격 조회 API**
   - 고객이 특정 브랜드에서 모든 카테고리 상품을 구매할 때 최저 가격을 확인할 수 있습니다.
3. **최고/최저 가격 브랜드 확인 기능**
   - 특정 카테고리에서 최고 및 최저 가격의 브랜드와 가격을 확인할 수 있습니다.
4. **상품 관리 API**
   - 관리자는 상품을 등록, 수정, 삭제할 수 있습니다.

## 빌드 및 실행 방법
### 1. 환경 설정
- **Java**: JDK 17 이상이 필요합니다.
- **Gradle**: Gradle Wrapper를 사용하여 빌드가 가능합니다.
- **H2 Database**: 애플리케이션 실행 시 자동으로 내장형 H2 데이터베이스를 사용합니다.

### 2. 프로젝트 Clone
```bash
git clone https://github.com/yunyoung1819/fashionshop-service.git
```


### 3. 빌드 및 실행
전체 프로젝트 빌드
```text
./gradlew build
```

Customer Service 실행

```text
./gradlew :customer-service:bootRun
```

Backoffice Service 실행
```text
./gradlew :backoffice-service:bootRun
```

### 4. 서비스 접속 및 확인
#### 4-1. Customer Service
- URL: http://localhost:8081/v1/api/products
- 기능: /lowest-price/category로 요청하여 카테고리별 최저 가격 상품을 조회할 수 있습니다.
#### 4-2. Backoffice Service
- URL: http://localhost:8082/v1/backoffice/products
- 기능: 상품 등록, 수정, 삭제 등
#### 4-3. H2 Console 접속
- URL: http://localhost:8081/h2-console
- JDBC URL: jdbc:h2:mem:testdb


#### 4-4. 샘플 데이터 추가 쿼리

``````sql
INSERT INTO brand (name) VALUES
('A'), ('B'), ('C'), ('D'), ('E'), ('F'), ('G'), ('H'), ('I');


INSERT INTO category (name) VALUES
('상의'), ('아우터'), ('바지'), ('스니커즈'), ('가방'), ('모자'), ('양말'), ('액세서리');



-- A 브랜드 데이터
INSERT INTO product (brand_id, category_id, price) VALUES
(1, 1, 11200), (1, 2, 5500), (1, 3, 4200), (1, 4, 9000), (1, 5, 2000), (1, 6, 1700), (1, 7, 1800), (1, 8, 2300);

-- B 브랜드 데이터
INSERT INTO product (brand_id, category_id, price) VALUES
(2, 1, 10500), (2, 2, 5900), (2, 3, 3800), (2, 4, 9100), (2, 5, 2100), (2, 6, 2000), (2, 7, 2000), (2, 8, 2200);

-- C 브랜드 데이터
INSERT INTO product (brand_id, category_id, price) VALUES
(3, 1, 10000), (3, 2, 6200), (3, 3, 3300), (3, 4, 9200), (3, 5, 2200), (3, 6, 1900), (3, 7, 2200), (3, 8, 2100);

-- D 브랜드 데이터
INSERT INTO product (brand_id, category_id, price) VALUES
(4, 1, 10100), (4, 2, 5100), (4, 3, 3000), (4, 4, 9500), (4, 5, 2500), (4, 6, 1500), (4, 7, 2400), (4, 8, 2000);

-- E 브랜드 데이터
INSERT INTO product (brand_id, category_id, price) VALUES
(5, 1, 10700), (5, 2, 5000), (5, 3, 3800), (5, 4, 9900), (5, 5, 2300), (5, 6, 1800), (5, 7, 2100), (5, 8, 2100);

-- F 브랜드 데이터
INSERT INTO product (brand_id, category_id, price) VALUES
(6, 1, 11200), (6, 2, 7200), (6, 3, 4000), (6, 4, 9300), (6, 5, 2100), (6, 6, 1600), (6, 7, 2300), (6, 8, 1900);

-- G 브랜드 데이터
INSERT INTO product (brand_id, category_id, price) VALUES
(7, 1, 10500), (7, 2, 5800), (7, 3, 3900), (7, 4, 9000), (7, 5, 2200), (7, 6, 1700), (7, 7, 2100), (7, 8, 2000);

-- H 브랜드 데이터
INSERT INTO product (brand_id, category_id, price) VALUES
(8, 1, 10800), (8, 2, 6300), (8, 3, 3100), (8, 4, 9700), (8, 5, 2100), (8, 6, 1600), (8, 7, 2000), (8, 8, 2000);

-- I 브랜드 데이터
INSERT INTO product (brand_id, category_id, price) VALUES
(9, 1, 11400), (9, 2, 6700), (9, 3, 3200), (9, 4, 9500), (9, 5, 2400), (9, 6, 1700), (9, 7, 1700), (9, 8, 2400);
``````


## API 문서
### Customer Service API
- Swagger: http://localhost:8081/v1/customer/swagger-ui.html
- 주요 API
  - i. 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API : GET /lowest-price/category
  - ii. 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품 가격, 총액을 조회하는 API: GET /lowest-price/brand/{brandId}
  - iii. 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API: GET /highest-lowest-price/brand


### Backoffice Service API
- Swagger: http://localhost:8082/v1/backoffice/swagger-ui.html
- 주요 API
  - 상품 등록: POST /products
  - 상품 수정: PATCH /products/{productId}
  - 상품 삭제: DELETE /products/{productId}
  - 브랜드 등록: POST /brands
  - 브랜드 수정: PATCH /brands/{brandId}
  - 브랜드 삭제: DELETE /brands/{brandId}
  - 카테고리 등록: POST /categories
  - 카테고리 수정: PATCH /categories/{categoryId}
  - 카테고리 삭제: DELETE /categories/{categoryId}

#### 4-4. Product View 화면
- URL: [http://localhost:8081/v1/customer/products/view](http://localhost:8081/v1/customer/products/view)
- 이 화면에서는 `카테고리 별 최저 가격 조회`, `브랜드 별 최저 가격 조회`, `카테고리 가격 범위 조회`를 테이블 형식으로 확인할 수 있습니다.
- **참고**: 화면상에서 정상적인 데이터를 확인하기 위해서는 반드시 데이터를 추가해야 합니다. 
- Swagger API는 정상 동작하지만 화면까지는 구현이 완료되지 않은 상태로 일부 오류가 있을 수 있습니다.

![Product View Screen](./view_page.png)



