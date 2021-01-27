CREATE DATABASE 'demoappdb';
CREATE TABLE 'categories' (
  'id' bigint NOT NULL,
  'name' varchar(255) DEFAULT NULL,
  'parent_id' bigint DEFAULT NULL,
  PRIMARY KEY ('id'),
  FOREIGN KEY ('parent_id') REFERENCES 'categories' ('id')
);

CREATE TABLE 'product_categories' (
  'product_id' bigint NOT NULL,
  'category_id' bigint NOT NULL,
  PRIMARY KEY ('product_id','category_id'),
  FOREIGN KEY ('category_id') REFERENCES 'categories' ('id'),
  FOREIGN KEY ('product_id') REFERENCES 'products' ('id')
);

CREATE TABLE 'products' (
  'id' bigint NOT NULL,
  'name' varchar(255) DEFAULT NULL,
  'currency' varchar(255) DEFAULT NULL,
  'price' double DEFAULT NULL,
  'quantity' int DEFAULT NULL,
  PRIMARY KEY ('id')
);

CREATE TABLE 'roles' (
  'id' int NOT NULL AUTO_INCREMENT,
  'name' varchar(20) DEFAULT NULL,
  PRIMARY KEY ('id')
);

CREATE TABLE 'user_roles' (
  'user_id' bigint NOT NULL,
  'role_id' int NOT NULL,
  PRIMARY KEY ('user_id','role_id'),
  FOREIGN KEY ('role_id') REFERENCES 'roles' ('id'),
  FOREIGN KEY ('user_id') REFERENCES 'users' ('id')
);

CREATE TABLE 'users' (
  'id' bigint NOT NULL AUTO_INCREMENT,
  'password' varchar(255) DEFAULT NULL,
  'username' varchar(255) DEFAULT NULL,
  PRIMARY KEY ('id')
);

create sequence if not exists hibernate_sequence start with 100;