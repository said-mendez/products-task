DROP TABLE IF EXISTS products;

CREATE TABLE products (
	id BINARY(16) NOT NULL PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
	name VARCHAR(255) NOT NULL UNIQUE,
	description VARCHAR(500),
	price DECIMAL(15, 2) NOT NULL,
	is_deleted BOOLEAN DEFAULT 0,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

);

DROP PROCEDURE IF EXISTS insert_product;

DELIMITER $$

CREATE PROCEDURE insert_product(
	p_name VARCHAR(255),
	p_description VARCHAR(500),
	p_price DECIMAL(15, 2)
)
BEGIN
	DECLARE uuid BINARY(16) DEFAULT (UUID_TO_BIN(UUID()));
	DECLARE EXIT HANDLER FOR 1062 SELECT 'Duplicate keys error' Message;
	DECLARE EXIT HANDLER FOR 1108 SELECT 'Incorrect number of arguments' Message;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION SELECT 'SQLException encountered' Message;
	DECLARE EXIT HANDLER FOR SQLWARNING SELECT 'SQL Warning!' Message;
	DECLARE EXIT HANDLER FOR SQLSTATE '42000' SELECT 'Incorrect number of arguments' Message;
	DECLARE EXIT HANDLER FOR SQLSTATE 'HY000' SELECT 'Unknown error!' Message;

	INSERT INTO products (id, name, description, price)
	VALUES (uuid, p_name, p_description, p_price);

	SELECT BIN_TO_UUID(uuid) id
		   , name
		   , description
		   , price
		   , is_deleted
		   , created_at
		   , updated_at
	FROM products
	WHERE id = uuid;
END $$

DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS read_products $$
CREATE PROCEDURE read_products()
BEGIN
	SELECT BIN_TO_UUID(id) as id
		   , name
		   , description
		   , price
		   , is_deleted
		   , created_at
		   , updated_at
	FROM products
    ;
END $$
DELIMITER ;