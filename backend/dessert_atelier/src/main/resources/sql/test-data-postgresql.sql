DROP TABLE IF EXISTS todo;
DROP TABLE IF EXISTS qna;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS review_image;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS delivery;
DROP TABLE IF EXISTS delivery_company;
DROP TABLE IF EXISTS order_cart;
DROP TABLE IF EXISTS basket;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS product_quantity;
DROP TABLE IF EXISTS option;
DROP TABLE IF EXISTS display_product_preset;
DROP TABLE IF EXISTS display_product_preset_image;
DROP TABLE IF EXISTS display_product;
DROP TABLE IF EXISTS recipe;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS component;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS store_admin;

CREATE TABLE member (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(20) NOT NULL,
    name VARCHAR(10) NOT NULL,
    phone VARCHAR(11) NOT NULL,
    member_origin VARCHAR(20) NOT NULL,
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

CREATE TABLE component (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    unit VARCHAR(10) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE product (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE,
    price INT NOT NULL,
    thumb VARCHAR(100) NOT NULL,
    product_status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE recipe (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    component_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (component_id) REFERENCES component (id)
);

CREATE TABLE display_product (
    id BIGSERIAL PRIMARY KEY,
    naming VARCHAR(20) NOT NULL UNIQUE,
    thumb VARCHAR(100) NOT NULL,
    description VARCHAR(100) NOT NULL,
    sale_status VARCHAR(20),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE display_product_preset_image (
    id BIGSERIAL PRIMARY KEY,
    image_urls JSONB
);

CREATE TABLE display_product_preset (
    id BIGSERIAL PRIMARY KEY,
    display_product_id BIGINT NOT NULL,
    naming VARCHAR(20) NOT NULL,
    thumb VARCHAR(100) NOT NULL,
    title VARCHAR(100) NOT NULL,
    price INT NOT NULL,
    option_layer INT,
    content TEXT NOT NULL,
    display_product_preset_image_id BIGINT,
    percent_discount INT,
    is_default BOOLEAN NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    start_date_time TIMESTAMP,
    end_date_time TIMESTAMP,
    FOREIGN KEY (display_product_id) REFERENCES display_product (id),
    FOREIGN KEY (
        display_product_preset_image_id
    ) REFERENCES display_product_preset_image (id)
);

CREATE TABLE option (
    id BIGSERIAL PRIMARY KEY,
    display_product_preset_id BIGINT NOT NULL,
    total_quantity INT,
    option_layer INT,
    option_status VARCHAR(20),
    description VARCHAR(100) NOT NULL,
    price INT NOT NULL,
    FOREIGN KEY (display_product_preset_id) REFERENCES display_product_preset (id)
);

CREATE TABLE product_quantity (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    option_id BIGINT NOT NULL,
    quantity INT,
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (option_id) REFERENCES option (id)
);

CREATE TABLE cart (
    id BIGSERIAL PRIMARY KEY,
    option_ids JSONB,
    quantity INT NOT NULL,
    status VARCHAR(20)
);

CREATE TABLE basket (
    id BIGSERIAL PRIMARY KEY,
    member_id BIGINT,
    cart_ids JSONB,
    FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE CASCADE
);

CREATE TABLE order_cart (
    id BIGSERIAL PRIMARY KEY,
    cart_ids JSONB
);

CREATE TABLE delivery_company (
    id BIGSERIAL PRIMARY KEY,
    company_name VARCHAR(20) NOT NULL,
    phone VARCHAR(11) NOT NULL
);

CREATE TABLE delivery (
    id BIGSERIAL PRIMARY KEY,
    delivery_code VARCHAR(20) NOT NULL,
    delivery_company_id BIGINT NOT NULL,
    delivery_status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP,
    FOREIGN KEY (delivery_company_id) REFERENCES delivery_company (id)
);

CREATE TABLE orders (
    order_code VARCHAR(20) NOT NULL UNIQUE PRIMARY KEY,
    member_id BIGINT,
    password VARCHAR(20),
    post_code VARCHAR(10),
    detail_address VARCHAR(50),
    receiver VARCHAR(20),
    phone VARCHAR(11),
    delivery_id BIGINT,
    order_cart_id BIGINT NOT NULL,
    order_status VARCHAR(20) NOT NULL,
    total_price BIGINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE SET NULL,
    FOREIGN KEY (delivery_id) REFERENCES delivery (id),
    FOREIGN KEY (order_cart_id) REFERENCES order_cart (id)
);

CREATE TABLE review_image (
    id BIGSERIAL PRIMARY KEY,
    image_urls JSONB
);

CREATE TABLE review (
    id BIGSERIAL PRIMARY KEY,
    display_product_id BIGINT NOT NULL,
    member_id BIGINT,
    review_image_id BIGINT,
    option_ids JSONB, -- 옵션 테이블의 아이디를 참고할 것인지 아닌지 생각해보자.
    rate INT,
    comment VARCHAR(250) NOT NULL,
    origin VARCHAR(20),
    review_status VARCHAR(20),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (display_product_id) REFERENCES display_product (id),
    FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE SET NULL,
    FOREIGN KEY (review_image_id) REFERENCES review_image (id)
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

CREATE TABLE todo (
    id BIGSERIAL PRIMARY KEY,
    order_code VARCHAR(20) NOT NULL,
    todo_status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP,
    complete_at TIMESTAMP,
    FOREIGN KEY (order_code) REFERENCES orders (order_code)
);

CREATE TABLE store_admin (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(20) NOT NULL,
    phone VARCHAR(11),
    admin_status VARCHAR(20) NOT NULL,
    admin_role VARCHAR(20) NOT NULL
);