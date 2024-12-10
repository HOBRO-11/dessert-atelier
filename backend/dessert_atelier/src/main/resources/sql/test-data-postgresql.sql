DROP TABLE IF EXISTS total_sale_option;

DROP TABLE IF EXISTS total_sale_product;

DROP TABLE IF EXISTS qna;

DROP TABLE IF EXISTS review;

DROP TABLE IF EXISTS delivery;

DROP TABLE IF EXISTS orders;

DROP TABLE IF EXISTS basket;

DROP TABLE IF EXISTS product_quantity;

DROP TABLE IF EXISTS option;

DROP TABLE IF EXISTS preset_table;

DROP TABLE IF EXISTS display_product;

DROP TABLE IF EXISTS product;

DROP TABLE IF EXISTS address;

DROP TABLE IF EXISTS refresh_token;

DROP TABLE IF EXISTS member;

CREATE TABLE member (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(30) NOT NULL UNIQUE,
    name VARCHAR(10) NOT NULL,
    phone VARCHAR(11),
    member_origin VARCHAR(20) NOT NULL,
    member_role VARCHAR(20) NOT NULL,
    member_status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE address (
    id BIGSERIAL PRIMARY KEY,
    naming VARCHAR(20) NOT NULL,
    member_id BIGINT NOT NULL,
    post_code VARCHAR(10),
    detail_address VARCHAR(50),
    receiver VARCHAR(20),
    phone VARCHAR(11),
    is_default BOOLEAN NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE CASCADE
);

CREATE TABLE product (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE,
    price INT NOT NULL,
    thumb VARCHAR(100) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE display_product (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(30) NOT NULL UNIQUE,
    thumb VARCHAR(100) NOT NULL,
    description VARCHAR(100) NOT NULL,
    display_product_status VARCHAR(20),
    images JSONB,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE preset_table (
    id BIGSERIAL PRIMARY KEY,
    display_product_id BIGINT,
    numbering INT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (display_product_id) REFERENCES display_product (id) ON DELETE CASCADE
);

CREATE TABLE option (
    id BIGSERIAL PRIMARY KEY,
    display_product_id BIGINT NOT NULL,
    total_quantity INT,
    option_layer INT,
    option_status VARCHAR(20),
    description VARCHAR(50) NOT NULL,
    price INT NOT NULL,
    FOREIGN KEY (display_product_id) REFERENCES display_product (id)
);

CREATE TABLE product_quantity (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    option_id BIGINT NOT NULL,
    quantity INT,
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (option_id) REFERENCES option (id)
);

CREATE TABLE basket (
    id BIGSERIAL PRIMARY KEY,
    member_id BIGINT,
    properties JSONB,
    FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE CASCADE
);

CREATE TABLE orders (
    order_code BIGINT NOT NULL UNIQUE PRIMARY KEY,
    member_id BIGINT,
    guest_phone VARCHAR(11),
    post_code VARCHAR(10),
    detail_address VARCHAR(50),
    receiver VARCHAR(20),
    phone VARCHAR(11),
    order_status VARCHAR(20) NOT NULL,
    delivery_fee BIGINT,
    total_price BIGINT,
    ordered_options JSONB,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE SET NULL
);

CREATE TABLE delivery (
    delivery_code VARCHAR(20) PRIMARY KEY,
    order_code BIGINT NOT NULL,
    delivery_status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP,
    FOREIGN KEY (order_code) REFERENCES orders (order_code)
);

CREATE TABLE review (
    id BIGSERIAL PRIMARY KEY,
    display_product_id BIGINT NOT NULL,
    member_id BIGINT,
    images JSONB,
    review_status VARCHAR(20),
    rate INT,
    comment VARCHAR(250) NOT NULL,
    origin VARCHAR(20),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (display_product_id) REFERENCES display_product (id),
    FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE SET NULL
);

CREATE TABLE qna (
    id BIGSERIAL PRIMARY KEY,
    display_product_id BIGINT NOT NULL,
    member_id BIGINT,
    password VARCHAR(20),
    comment VARCHAR(250) NOT NULL,
    comment_created_at TIMESTAMP,
    comment_updated_at TIMESTAMP,
    qna_status VARCHAR(20) NOT NULL,
    answer VARCHAR(250),
    answer_created_at TIMESTAMP,
    answer_updated_at TIMESTAMP,
    FOREIGN KEY (display_product_id) REFERENCES display_product (id),
    FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE SET NULL
);

CREATE TABLE total_sale_option (
    id BIGSERIAL PRIMARY KEY,
    option_id BIGINT,
    sale_amount INT,
    created_at DATE,
    FOREIGN KEY (option_id) REFERENCES option (id),
    CONSTRAINT unique_created_at_option UNIQUE (created_at, option_id)
);

CREATE TABLE total_sale_product (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT,
    sale_amount INT,
    created_at DATE,
    FOREIGN KEY (product_id) REFERENCES product (id),
    CONSTRAINT unique_created_at_product UNIQUE (created_at, product_id)
);

CREATE TABLE refresh_token (
    id BIGSERIAL PRIMARY KEY,
    member_id BIGINT,
    refresh_token_signature VARCHAR(100) NOT NULL,
    expired_date TIMESTAMP,
    FOREIGN KEY (member_id) REFERENCES member (id)
);