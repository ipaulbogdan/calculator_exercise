INSERT INTO articles(name, public_id)
VALUES ('cartof', uuid_generate_v4()),
       ('somon', uuid_generate_v4()),
       ('crap', uuid_generate_v4()),
       ('ceapa', uuid_generate_v4()),
       ('castraveti', uuid_generate_v4()),
       ('rib-eye', uuid_generate_v4()),
       ('sirloin', uuid_generate_v4()),
       ('inghetata', uuid_generate_v4());

INSERT INTO customers(name, public_id)
VALUES ('Paul Idorasi', uuid_generate_v4()),
       ('Popescu Ion', uuid_generate_v4()),
       ('Ciolacu Vasile', uuid_generate_v4());

INSERT INTO prices(article_id, customer_id, price)
VALUES ((SELECT id FROM articles WHERE name='cartof'), (SELECT id FROM customers WHERE name='Paul Idorasi'), 2.20),
       ((SELECT id FROM articles WHERE name='cartof'), null, 2.50),
       ((SELECT id FROM articles WHERE name='somon'), null, 12.5),
       ((SELECT id FROM articles WHERE name='somon'), (SELECT id FROM customers WHERE name='Paul Idorasi'), 10.0),
       ((SELECT id FROM articles WHERE name='crap'), null, 8.23),
       ((SELECT id FROM articles WHERE name='ceapa'), null,  1.2),
       ((SELECT id FROM articles WHERE name='castraveti'), null, 1.5),
       ((SELECT id FROM articles WHERE name='rib-eye'), null, 23.50),
       ((SELECT id FROM articles WHERE name='sirloin'), null, 20.2),
       ((SELECT id FROM articles WHERE name='rib-eye'), (SELECT id FROM customers WHERE name='Paul Idorasi'), 20),
       ((SELECT id FROM articles WHERE name='inghetata'), (SELECT id FROM customers WHERE name='Popescu Ion'), 0.89),
       ((SELECT id FROM articles WHERE name='inghetata'), null, 1.25);

