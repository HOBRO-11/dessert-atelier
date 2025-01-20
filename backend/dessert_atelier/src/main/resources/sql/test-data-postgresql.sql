DROP TABLE IF EXISTS total_sale_option;

DROP TABLE IF EXISTS total_sale_product;

DROP TABLE IF EXISTS product_qna;

DROP TABLE IF EXISTS product_review;

DROP TABLE IF EXISTS refund;

DROP TABLE IF EXISTS order_detail;

DROP TABLE IF EXISTS cart_option;

DROP TABLE IF EXISTS product_quantity;

DROP TABLE IF EXISTS sale_option;

DROP TABLE IF EXISTS sale_option_header;

DROP TABLE IF EXISTS shop_page;

DROP TABLE IF EXISTS sale_page;

DROP TABLE IF EXISTS member_address;

DROP TABLE IF EXISTS refresh_token;

DROP TABLE IF EXISTS product_react;

DROP TABLE IF EXISTS product;

DROP TABLE IF EXISTS member;

CREATE TABLE member (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(30) NOT NULL UNIQUE,
    name VARCHAR(20) NOT NULL,
    phone VARCHAR(11),
    member_origin VARCHAR(20) NOT NULL,
    member_role VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE member_address (
    id BIGSERIAL PRIMARY KEY,
    naming VARCHAR(20) NOT NULL,
    member_id BIGINT NOT NULL,
    post_code VARCHAR(100) NOT NULL,
    detail_address VARCHAR(50) NOT NULL,
    receiver VARCHAR(20) NOT NULL,
    phone VARCHAR(11) NOT NULL,
    is_default BOOLEAN NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE CASCADE
);

CREATE TABLE product (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE,
    thumbnail VARCHAR(100) NOT NULL,
    price INT NOT NULL,
    stock_quantity INT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE product_react (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE sale_page (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL UNIQUE,
    subtitles JSON,
    thumbnail JSONB,
    content JSONB,
    product_react_id BIGINT,
    option_header_ids JSONB,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE INDEX idx_sale_page_product_react_id ON sale_page (product_react_id);

CREATE TABLE shop_page (
    id BIGSERIAL PRIMARY KEY,
    naming VARCHAR(20) NOT NULL,
    sale_page_ids JSONB,
    is_default BOOLEAN NOT NULL,
    started_date_at TIMESTAMP,
    ended_date_at TIMESTAMP,
    is_delete_at_end_of_event BOOLEAN NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE sale_option_header (
    id BIGSERIAL PRIMARY KEY,
    sale_page_id BIGINT NOT NULL,
    header VARCHAR(200) NOT NULL,
    header_type VARCHAR(20) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (sale_page_id) REFERENCES sale_page (id),
    UNIQUE (sale_page_id, header) -- 유니크 제약 조건 추가
);

CREATE TABLE sale_option (
    id BIGSERIAL PRIMARY KEY,
    sale_option_header_id BIGINT NOT NULL,
    explanation VARCHAR(200) NOT NULL,
    price INT NOT NULL,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (sale_option_header_id) REFERENCES sale_option_header (id)
);

CREATE TABLE product_quantity (
    id BIGSERIAL PRIMARY KEY,
    sale_option_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (sale_option_id) REFERENCES sale_option (id)
);

CREATE TABLE cart_option (
    id BIGSERIAL PRIMARY KEY,
    member_id BIGINT NOT NULL,
    properties JSONB
);

CREATE INDEX idx_cart_option_member_id ON cart_option (member_id);

CREATE TABLE order_detail (
    id BIGINT NOT NULL UNIQUE PRIMARY KEY,
    member_id BIGINT NOT NULL,
    guest_email VARCHAR(11),
    quest_phone VARCHAR(11),
    delivery_code VARCHAR(20),
    payment_id VARCHAR(20),
    post_code VARCHAR(100) NOT NULL,
    detail_address VARCHAR(50) NOT NULL,
    receiver VARCHAR(20) NOT NULL,
    phone VARCHAR(11) NOT NULL,
    property JSON,
    total_price BIGINT NOT NULL,
    delivery_fee BIGINT NOT NULL,
    check_sum INT NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE INDEX idx_order_detail_member_id ON order_detail (member_id);

CREATE TABLE refund (
    id BIGSERIAL PRIMARY KEY,
    order_detail_id BIGINT NOT NULL,
    reason VARCHAR(250) NOT NULL,
    property JSON,
    is_contain_delivery_fee BOOLEAN NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (order_detail_id) REFERENCES order_detail (id) ON DELETE CASCADE
);

CREATE TABLE product_review (
    id BIGSERIAL PRIMARY KEY,
    member_id BIGINT,
    product_react_id BIGINT NOT NULL,
    images JSONB,
    rate INT NOT NULL,
    comment VARCHAR(250) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE INDEX idx_product_review_member_id ON product_review (member_id);

CREATE INDEX idx_product_review_product_react_id ON product_review (product_react_id);

CREATE TABLE product_qna (
    id BIGSERIAL PRIMARY KEY,
    member_id BIGINT,
    password VARCHAR(20),
    product_react_id BIGINT NOT NULL,
    question VARCHAR(250) NOT NULL,
    answer VARCHAR(250),
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE INDEX idx_product_qna_member_id ON product_qna (member_id);

CREATE INDEX idx_product_qna_product_react_id ON product_qna (product_react_id);

CREATE TABLE total_sale_option (
    id BIGSERIAL PRIMARY KEY,
    sale_option_id BIGINT NOT NULL,
    sale_amount INT NOT NULL,
    created_at DATE NOT NULL,
    CONSTRAINT unique_created_at_option UNIQUE (created_at, sale_option_id)
);

CREATE INDEX idx_total_sale_option_sale_option_id ON total_sale_option (sale_option_id);

CREATE TABLE total_sale_product (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    sale_amount INT NOT NULL,
    created_at DATE NOT NULL,
    CONSTRAINT unique_created_at_product UNIQUE (created_at, product_id)
);

CREATE INDEX idx_total_sale_product_product_id ON total_sale_product (product_id);

CREATE TABLE refresh_token (
    id BIGSERIAL PRIMARY KEY,
    member_id BIGINT NOT NULL,
    refresh_token_signature VARCHAR(100) NOT NULL,
    expired_date TIMESTAMP
);

CREATE INDEX idx_refresh_token_member_id ON refresh_token (member_id);