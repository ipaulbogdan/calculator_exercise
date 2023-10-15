CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE articles (
    id SERIAL PRIMARY KEY,
    public_id VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE customers (
    id SERIAL PRIMARY KEY,
    public_id VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE prices (
     id SERIAL PRIMARY KEY,
     article_id INT NOT NULL,
     customer_id INT,
     price NUMERIC,

     CONSTRAINT fk_article FOREIGN KEY(article_id) REFERENCES articles(id),
     CONSTRAINT fk_customer FOREIGN KEY(customer_id) REFERENCES customers(id),
     CONSTRAINT article_customer_constraint  UNIQUE (article_id, customer_id)
);


