DROP PROCEDURE IF EXISTS insert_product;

DELIMITER $$

CREATE PROCEDURE insert_product(
	p_name VARCHAR(255),
	p_description VARCHAR(500),
	p_price DECIMAL(15, 2)
)
BEGIN
	DECLARE uuid BINARY(16) DEFAULT (UUID_TO_BIN(UUID()));
	DECLARE record_was_created INT DEFAULT 0;

	INSERT INTO products (id, name, description, price)
	VALUES (uuid, p_name, p_description, p_price);

	SELECT count(*) INTO record_was_created FROM products WHERE id = uuid;

    IF record_was_created = 1 THEN
        SELECT BIN_TO_UUID(uuid) id
               , name
               , description
               , price
               , is_deleted
               , created_at
               , updated_at
        FROM products
        WHERE id = uuid;
    END IF;
END $$

DELIMITER ;