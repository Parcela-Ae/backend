insert into state (id, uf, name) values (26, 'PE', 'Pernambuco');
insert into state (id, uf, name) values (35, 'SP', 'São Paulo');

insert into city (id, name, state_id) values (2611606, 'Recife', 26);
insert into city (id, name, state_id) values (2609600, 'Olinda', 26);
insert into city (id, name, state_id) values (3550308, 'São Paulo', 35);

insert into specialty (id, name) values (1, 'Clinica Geral');
insert into specialty (id, name) values (2, 'Dermatologia');
insert into specialty (id, name) values (3, 'Odontologia');

insert into user (id, email, name, password) values (1, 'ok@doutor.com', 'Ok Doutor', '$2a$10$VdEtcZfZQbgLX9E3wykq/OX6ENEj83s2nPsQuUznBVhkJQaYiTFIW');
insert into clinic (cnpj, id) values ('12345678910', 1);
insert into address (id, city_id, complement, latitude, longitude, neighborhood, number, public_area, user_id, zip_code) values (1, 2611606, 'Apto 303', null, null, 'Espinheiro', '300', 'Av. Agamenon Magalhães', 1, '38220834');
insert into address (id, city_id, complement, latitude, longitude, neighborhood, number, public_area, user_id, zip_code) values (2, 2609600, null, null, null, 'Centro', '105', 'Presidente Kennedy', 1, 38777012);
insert into phones (user_id, phones) values (1, '34353637');
insert into phones (user_id, phones) values (1, '81994020345');
insert into profiles (user_id, profiles) values (1, 1);
insert into profiles (user_id, profiles) values (1, 3);
insert into clinic_specialties (clinic_id, specialties_id) values (1, 2);
insert into clinic_specialties (clinic_id, specialties_id) values (1, 3);

insert into user (id, email, name, password) values (2, 'sirio@libanes.com', 'Sirio Libanes', '$2a$10$/ZcXT4sPz7qiLzc2qJlpbep1EF7ZCXPmUrEtKLcE3lmxLjG6f9AY6');
insert into clinic (cnpj, id) values ('10987654321', 2);
insert into address (id, city_id, complement, latitude, longitude, neighborhood, number, public_area, user_id, zip_code) values (3, 3550308, null, null, null, 'Centro', '2106', 'Avenida Paulista', 2, 39867024);
insert into phones (user_id, phones) values (2, '11994020345');
insert into phones (user_id, phones) values (2, '32353937');
insert into profiles (user_id, profiles) values (2, 3);
insert into clinic_specialties (clinic_id, specialties_id) values (2, 1);
insert into clinic_specialties (clinic_id, specialties_id) values (2, 2);
insert into clinic_specialties (clinic_id, specialties_id) values (2, 3);