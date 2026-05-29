-- CREATE DATABASE DCS;
CREATE SCHEMA IF NOT EXISTS dbo;

CREATE TABLE IF NOT EXISTS dbo.user(
	user_id SERIAL PRIMARY KEY,
	user_name VARCHAR(100),
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
)