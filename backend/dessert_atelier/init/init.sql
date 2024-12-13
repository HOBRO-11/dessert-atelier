INSERT INTO member (email, name, phone, member_origin, member_role, member_status, created_at, updated_at)
VALUES
    ('user1@example.com', 'Alice', '01012345678', 'WEB', 'USER', 'ACTIVE', NOW(), NOW()),
    ('user2@example.com', 'Bob', '01023456789', 'APP', 'USER', 'INACTIVE', NOW(), NOW()),
    ('user3@example.com', 'Charlie', '01034567890', 'WEB', 'ADMIN', 'ACTIVE', NOW(), NOW()),
    ('user4@example.com', 'Daisy', NULL, 'APP', 'USER', 'ACTIVE', NOW(), NOW()),
    ('user5@example.com', 'Eve', '01045678901', 'WEB', 'USER', 'BANNED', NOW(), NOW());

INSERT INTO address (naming, member_id, post_code, detail_address, receiver, phone, is_default)
VALUES
    ('Home', 1, '12345', '123 Main St.', 'Alice', '01012345678', TRUE),
    ('Office', 1, '67890', '456 Office Rd.', 'Alice', '01098765432', FALSE),
    ('Home', 2, '54321', '789 Elm St.', 'Bob', '01023456789', TRUE),
    ('Vacation', 3, NULL, 'Unknown St.', 'Charlie', NULL, FALSE),
    ('Home', 4, '11111', '555 Maple Ave.', 'Daisy', '01076543210', TRUE);
    
INSERT INTO product (name, price, quantity, thumb, created_at, updated_at)
VALUES
    ('Product1', 1000, 50, '/images/product1.jpg', NOW(), NOW()),
    ('Product2', 2000, 30, '/images/product2.jpg', NOW(), NOW()),
    ('Product3', 1500, 20, '/images/product3.jpg', NOW(), NOW()),
    ('Product4', 2500, 10, '/images/product4.jpg', NOW(), NOW()),
    ('Product5', 3000, 5, '/images/product5.jpg', NOW(), NOW());
    
INSERT INTO display_product (title, thumb, option_layer, description, display_product_status, images, created_at, updated_at)
VALUES
    ('Display1', '/images/display1.jpg', 2, 'Description1', 'ACTIVE', '[{"url":"/images/1.jpg"}]', NOW(), NOW()),
    ('Display2', '/images/display2.jpg', 3, 'Description2', 'INACTIVE', '[{"url":"/images/2.jpg"}]', NOW(), NOW()),
    ('Display3', '/images/display3.jpg', 1, 'Description3', 'ACTIVE', '[{"url":"/images/3.jpg"}]', NOW(), NOW()),
    ('Display4', '/images/display4.jpg', 4, 'Description4', 'ACTIVE', '[{"url":"/images/4.jpg"}]', NOW(), NOW()),
    ('Display5', '/images/display5.jpg', 2, 'Description5', 'INACTIVE', '[{"url":"/images/5.jpg"}]', NOW(), NOW());
    
INSERT INTO preset_table (display_product_id, numbering, created_at, updated_at)
VALUES
    (1, 1, NOW(), NOW()),
    (1, 2, NOW(), NOW()),
    (2, 1, NOW(), NOW()),
    (3, 1, NOW(), NOW()),
    (4, 1, NOW(), NOW());
    
INSERT INTO option (display_product_id, option_layer, option_status, description, price)
VALUES
    (1, 1, 'ACTIVE', 'Option1 for Display1', 500),
    (1, 2, 'INACTIVE', 'Option2 for Display1', 1000),
    (2, 1, 'ACTIVE', 'Option1 for Display2', 1500),
    (3, 1, 'ACTIVE', 'Option1 for Display3', 2000),
    (4, 1, 'ACTIVE', 'Option1 for Display4', 2500);
    
INSERT INTO product_quantity (product_id, option_id, quantity)
VALUES
    (1, 1, 10),
    (1, 2, 5),
    (2, 3, 20),
    (3, 4, 15),
    (4, 5, 10);
    
INSERT INTO basket (member_id, properties)
VALUES
    (1, '{"item": "item1", "quantity": 2}'),
    (2, '{"item": "item2", "quantity": 1}'),
    (3, '{"item": "item3", "quantity": 3}'),
    (4, '{"item": "item4", "quantity": 5}'),
    (5, '{"item": "item5", "quantity": 2}');

INSERT INTO orders (order_code, member_id, guest_phone, post_code, detail_address, receiver, phone, order_status, delivery_fee, total_price, ordered_options, created_at, updated_at)
VALUES
    (100001, 1, NULL, '12345', '123 Main St.', 'Alice', '01012345678', 'DELIVERED', 3000, 10000, '[{"optionId": 1, "quantity": 2}]', NOW(), NOW()),
    (100002, 2, NULL, '67890', '456 Office Rd.', 'Bob', '01023456789', 'PENDING', 3000, 20000, '[{"optionId": 2, "quantity": 1}]', NOW(), NOW()),
    (100003, 3, NULL, '54321', '789 Elm St.', 'Charlie', '01034567890', 'CANCELLED', 3000, 15000, '[{"optionId": 3, "quantity": 3}]', NOW(), NOW()),
    (100004, 4, NULL, '11111', '555 Maple Ave.', 'Daisy', '01076543210', 'SHIPPED', 3000, 25000, '[{"optionId": 4, "quantity": 1}]', NOW(), NOW()),
    (100005, 5, NULL, '22222', '789 Oak St.', 'Eve', '01045678901', 'DELIVERED', 3000, 30000, '[{"optionId": 5, "quantity": 2}]', NOW(), NOW());