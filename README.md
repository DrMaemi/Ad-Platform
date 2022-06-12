# 셀러 광고플랫폼 구축
- 커머스 도메인에서 셀러의 업체 입점부터 광고입찰 생성, FRONT 전시, CPC 방식의 클릭과금, 과금데이터의 배치성 정산집계까지 일련의 프로세스를 수행할 수 있는 서버 개발
- 상품 리스트가 등록되면 상품에 셋팅된 공급업체의 업체 Master 데이터 생성부터 프로세스 시작
- E-commerce의 경우 업체등록/계약 후 상품생성이 되어야 하나 본 프로젝트에서는 위의 프로세스로 진행

## 개발 환경
- Java 18(OpenJDK)
- Spring Boot 2.7.0
- IntelliJ Community Edition 2021.3.3
- Gradle
- H2 Database
- JPA(Java Persistence API)
- Spring Security, Validation, REST Docs, Batch
- Thymeleaf

## 개발 기간
2022.05.31 ~ 2022.06.12

## 프로젝트 전체 구조

### 동작 구조

<p align="center">

![](https://drive.google.com/uc?export=view&id=1TEGMLaj66tqxG4V9a1YGwPrrQL6E6Fn6)

</p>

### 전체 패키지

<p align="center">

![](https://drive.google.com/uc?export=view&id=1DdS6O4iYenq9CdBkTqH2lA9kxmBj93_z)

</p>

### config 패키지

<p align="center">

![](https://drive.google.com/uc?export=view&id=1niSMjdupGWikuutIzPEXQDDzbaRRuB2i)

</p>

- validator
    - 유효성 검사 커스텀 어노테이션 및 마커 인터페이스(유효성 그룹)
- advice
    - ExceptionHandler 오버라이딩, 유효성 검사 시 데이터 핸들링
- batch
    - 광고 과금 정산 배치 처리를 위한 구성 클래스 및 스케쥴러
- response
    - RESTful API에 사용되는 Response 객체 구조를 담고 있는 클래스
- embeddable
    - JPA 복합키 구현을 위한 클래스

## DB(H2 Database) 테이블 구조(ERD)
![](https://drive.google.com/uc?export=view&id=1GWYBDmWgtgG8FAGoiDF_Tqi4K8mTRA6t)

## 테이블 스키마
### 업체(COMPANY)
```sql
CREATE TABLE COMPANY(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1e9+1),
    name VARCHAR(30) UNIQUE NOT NULL,
    business_registration_number VARCHAR(20) UNIQUE,
    phone_number VARCHAR(20),
    address VARCHAR(50),
    created_date TIMESTAMP NOT NULL,
    last_modified_date TIMESTAMP,
    PRIMARY KEY (id)
);
```

### 상품(PRODUCT)
```sql
CREATE TABLE PRODUCT(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1e9+1),
    company_name VARCHAR(30) NOT NULL,
    product_name VARCHAR(50) NOT NULL,
    price BIGINT NOT NULL,
    stock BIGINT NOT NULL,
    created_date TIMESTAMP NOT NULL,
    last_modified_date TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (company_name) REFERENCES COMPANY(name)
);
```

### 업체 계약(CONTRACT)
```sql
CREATE TABLE CONTRACT(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY,
    company_id BIGINT UNIQUE NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    created_date TIMESTAMP NOT NULL,
    last_modified_date TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (company_id) REFERENCES COMPANY
);
```

### 광고 입찰(ADVERTISEMENT_BID)
```sql
CREATE TABLE ADVERTISEMENT_BID(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1e9+1),
    company_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    bid_price BIGINT NOT NULL,
    created_date TIMESTAMP NOT NULL,
    last_modified_date TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (company_id) REFERENCES CONTRACT(company_id),
    FOREIGN KEY (product_id) REFERENCES PRODUCT,
    CONSTRAINT check_price CHECK (bid_price <= 1e9)
);
```

### 광고 전시(ADVERTISEMENT_DISPLAY)
```sql
CREATE OR REPLACE VIEW ADVERTISEMENT_DISPLAY AS
SELECT AB.ID AS BID_ID, AB.COMPANY_ID, AB.PRODUCT_ID, AB.BID_PRICE
FROM ADVERTISEMENT_BID AS AB, PRODUCT AS P
WHERE AB.PRODUCT_ID = P.ID AND P.STOCK != 0
ORDER BY AB.BID_PRICE DESC
LIMIT 3;
```

### 광고 과금(ADVERTISEMENT_CHARGE)
```sql
CREATE TABLE ADVERTISEMENT_CHARGE(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1e9+1),
    bid_id BIGINT NOT NULL,
    clicked_date TIMESTAMP NOT NULL,
    bid_price BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (bid_id) REFERENCES ADVERTISEMENT_BID
);
```

### 광고 과금 정산(ADVERTISEMENT_CHARGE_CAL)
```sql
CREATE TABLE ADVERTISEMENT_CHARGE_CAL(
    clicked_date DATE,
    bid_id BIGINT,
    company_id BIGINT NOT NULL,
    company_name VARCHAR(30) NOT NULL,
    product_id BIGINT NOT NULL,
    product_name VARCHAR(50) NOT NULL,
    cnt_clicked BIGINT NOT NULL,
    total_charge BIGINT NOT NULL,
    PRIMARY KEY (clicked_date, bid_id)
);
```

## 프로젝트 산출물
### Spring REST Docs - API Documentation
전체 내용은 [API Documentation](https://drmaemi.github.io/Ad-Platform/REST_DOCS)에서 확인

TOC 캡처

![](https://drive.google.com/uc?export=view&id=1166m7-QXivT7LkOHWPWfs5C_Q5qvcq8B)

임의 API request 캡처

![](https://drive.google.com/uc?export=view&id=15QNFnyAinUYmc4bIHY9LiszkpPdxN2-q)

임의 API response 캡처

![](https://drive.google.com/uc?export=view&id=1vNkxujjpZLNfo7Cz7wlBozTgJuRWzTxd)

### 광고전시 리스트 페이지
![](https://drive.google.com/uc?export=view&id=1k1-Dh79K2ZjF9xOF0LnEngyx7r3mHsNH)