-- CREATE DATABASE DCS;
CREATE SCHEMA IF NOT EXISTS dbo;

CREATE TABLE IF NOT EXISTS dbo.user(
	user_id SERIAL PRIMARY KEY,
	username VARCHAR(100),
	email VARCHAR(100) UNIQUE NOT NULL,
	point BIGINT,
	date_of_birth DATE
);

CREATE TABLE IF NOT EXISTS dbo.customer(
	customer_id SERIAL PRIMARY KEY,
	customer_name VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS dbo.transaction_history(
	transaction_id SERIAL PRIMARY KEY,
	user_id INT,
	customer_id INT,
	point BIGINT,
	action VARCHAR(50),
	note TEXT,
	created_date_time TIMESTAMP,
	FOREIGN KEY (user_id) REFERENCES dbo.user(user_id),
	FOREIGN KEY (customer_id) REFERENCES dbo.customer(customer_id)
);

CREATE OR REPLACE PROCEDURE dbo.insert_transaction(
    IN p_user_id INT,
    IN p_customer_id INT,
    IN p_point BIGINT,
    IN p_action VARCHAR(50),
    IN p_note TEXT,
    IN p_created_date_time TIMESTAMP,
	OUT p_transaction_id INT
)
LANGUAGE plpgsql
AS $$
BEGIN

    INSERT INTO dbo.transaction_history(
        user_id,
        customer_id,
        point,
        action,
        note,
        created_date_time
    )
    VALUES (
        p_user_id,
        p_customer_id,
        p_point,
        p_action,
        p_note,
        p_created_date_time
    )
	RETURNING transaction_id
	INTO p_transaction_id;

    IF p_action = 'IN' THEN

        UPDATE dbo."user"
        SET point = point + p_point
        WHERE user_id = p_user_id;

    ELSIF p_action = 'OUT' THEN

        UPDATE dbo."user"
        SET point = point - p_point
        WHERE user_id = p_user_id
          AND point >= p_point;

        IF NOT FOUND THEN
            RAISE EXCEPTION 'Not enough points';
        END IF;

    ELSE
        RAISE EXCEPTION 'Invalid action';
    END IF;

END;
$$;