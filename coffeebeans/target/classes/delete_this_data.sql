--INSERT INTO coffeeshops (id, name, address) VALUES (1, 'shop_name_1', '123 1st street');
--INSERT INTO coffeeshops (id, name, address) VALUES (1, 'shop_name_1', '123 1st street');
INSERT INTO users (id, name, birthday) VALUES (1, 'user_name_1', '2002-01-01');
CREATE TABLE IF NOT EXISTS users (id LONG PRIMARY KEY, name VARCHAR(50) NOT NULL, birthday date NOT NULL);


-- CREATE TABLE coffeeshops (
--      id LONG PRIMARY KEY,
--      name VARCHAR(50) NOT NULL,
--      address VARCHAR(100) NOT NULL
-- )

-- CREATE TABLE coffeeshops (
--      id LONG PRIMARY KEY,
--      name VARCHAR(50) NOT NULL,
--      address VARCHAR(100) NOT NULL
-- )