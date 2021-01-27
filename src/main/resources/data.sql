-- Users
insert into users (id, password, username, role) values (1, '$2a$10$NkMvVgxEAKqqDrjrQbnTWudZgHRTgcir.22diV1jDdmx1OZG0O2F2', 'user', 'ROLE_USER');
insert into users (id, password, username, role) values (2, '$2a$10$QX0QE0Yz.pfOt2m3Tu2lze7YMpOe.YCSyCfAPS0qZzlknH7q6wvuO', 'admin', 'ROLE_ADMIN');
-- Categories
insert into categories (id, name, parent_id) values (1, 'Cloth', null);
insert into categories (id, name, parent_id) values (2, 'Electronics', null);
-- Subcategories for 'Cloth'
insert into categories (id, name, parent_id) values (3, 'Shoes', 1);
insert into categories (id, name, parent_id) values (4, 'Hats', 1);
-- Subcategories for 'Electronics'
insert into categories (id, name, parent_id) values (5, 'PCs', 2);
insert into categories (id, name, parent_id) values (6, 'Game Consoles', 2);
insert into categories (id, name, parent_id) values (7, 'Headsets', 2);
insert into categories (id, name, parent_id) values (8, 'Phones', 2);
-- Subcategories for 'Shoes'
insert into categories (id, name, parent_id) values (9, 'Man shoes', 3);
insert into categories (id, name, parent_id) values (10, 'Women shoes', 3);
insert into categories (id, name, parent_id) values (11, 'Kids shoes', 3);