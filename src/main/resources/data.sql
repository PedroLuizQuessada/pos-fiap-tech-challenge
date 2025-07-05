INSERT IGNORE INTO addresses (address_state, city, street, address_number, zip_code, aditional_info) VALUES ("estado usuario 1", "cidade usuario 1", "rua usuario 1", "número usuario 1", "cep usuario 1", "complemento usuario 1");
INSERT IGNORE INTO user_types (type_name) VALUES ("ADMIN");
INSERT IGNORE INTO user_types (type_name) VALUES ("OWNER");
INSERT IGNORE INTO user_types (type_name) VALUES ("CUSTOMER");
INSERT IGNORE INTO users (user_name, email, login, password, user_type, address) VALUES ("nome usuario 1", "email1@gmail.com", "LoginUsuario1", "$2a$10$UqmSCvzIURnuZj3a5m/VXu1fma2hWZJVsYYJZVxmsypJZGqVWKcKC", 1, 1);