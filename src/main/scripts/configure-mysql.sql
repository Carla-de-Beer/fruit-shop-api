## Use to run mysql db docker image, optional if you're not using a local mysqldb
# docker run --name mysqldb -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql

# connect to mysql and run as root user
#Create Databases
CREATE DATABASE fruit_shop_dev;

#Create database service accounts
CREATE USER 'fruit_shop_dev_user'@'localhost' IDENTIFIED BY 'cadebe';
CREATE USER 'fruit_shop_dev_user'@'%' IDENTIFIED BY 'cadebe';

#Database grants
GRANT SELECT ON fruit_shop_dev.* to 'fruit_shop_dev_user'@'localhost';
GRANT INSERT ON fruit_shop_dev.* to 'fruit_shop_dev_user'@'localhost';
GRANT DELETE ON fruit_shop_dev.* to 'fruit_shop_dev_user'@'localhost';
GRANT UPDATE ON fruit_shop_dev.* to 'fruit_shop_dev_user'@'localhost';

GRANT SELECT ON fruit_shop_dev.* to 'fruit_shop_dev_user'@'%';
GRANT INSERT ON fruit_shop_dev.* to 'fruit_shop_dev_user'@'%';
GRANT DELETE ON fruit_shop_dev.* to 'fruit_shop_dev_user'@'%';
GRANT UPDATE ON fruit_shop_dev.* to 'fruit_shop_dev_user'@'%';

#Create and populate databases

DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS vendors;

CREATE TABLE categories (id BINARY(16) not null, name varchar(255), primary key (id)) engine=InnoDB;
CREATE TABLE vendors (id BINARY(16) not null, name varchar(255), primary key (id)) engine=InnoDB;

INSERT INTO categories values(unhex(replace(uuid(),'-','')), 'Fruits');
INSERT INTO categories values(unhex(replace(uuid(),'-','')), 'Fresh');
INSERT INTO categories values(unhex(replace(uuid(),'-','')), 'Dried');
INSERT INTO categories values(unhex(replace(uuid(),'-','')), 'Nuts');
INSERT INTO categories values(unhex(replace(uuid(),'-','')), 'Exotics');

INSERT INTO vendors values(unhex(replace(uuid(),'-','')), 'Alfreds Futterkiste');
INSERT INTO vendors values(unhex(replace(uuid(),'-','')), 'Ginos Gelato');

SELECT * FROM categories;