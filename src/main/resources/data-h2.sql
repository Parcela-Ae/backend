insert into specialty (id, name) values (1, 'Acupuntura');
insert into specialty (id, name) values (2, 'Alergia e Imunologia');
insert into specialty (id, name) values (3, 'Anestesiologia');
insert into specialty (id, name) values (4, 'Angiologia');
insert into specialty (id, name) values (5, 'Cardiologia');
insert into specialty (id, name) values (6, 'Cirurgia Cardiovascular');
insert into specialty (id, name) values (7, 'Cirurgia da Mão');
insert into specialty (id, name) values (8, 'Cirurgia de Cabeça e Pescoço');
insert into specialty (id, name) values (9, 'Cirurgia do Aparelho Digestivo');
insert into specialty (id, name) values (10, 'Cirurgia Geral');
insert into specialty (id, name) values (11, 'Cirurgia Oncológica');
insert into specialty (id, name) values (12, 'Cirurgia Pediátrica');
insert into specialty (id, name) values (13, 'Cirurgia Plástica');
insert into specialty (id, name) values (14, 'Cirurgia Torácica');
insert into specialty (id, name) values (15, 'Cirurgia Vascular');
insert into specialty (id, name) values (16, 'Clínica Médica');
insert into specialty (id, name) values (17, 'Coloproctologia');
insert into specialty (id, name) values (18, 'Dermatologia');
insert into specialty (id, name) values (19, 'Endocrinologia e Metabologia');
insert into specialty (id, name) values (20, 'Endoscopia');
insert into specialty (id, name) values (21, 'Gastroenterologia');
insert into specialty (id, name) values (22, 'Genética Médica');
insert into specialty (id, name) values (23, 'Geriatria');
insert into specialty (id, name) values (24, 'Ginecologia e Obstetrícia');
insert into specialty (id, name) values (25, 'Hematologia e Hemoterapia');
insert into specialty (id, name) values (26, 'Homeopatia');
insert into specialty (id, name) values (27, 'Infectologia');
insert into specialty (id, name) values (28, 'Mastologia');
insert into specialty (id, name) values (29, 'Medicina de Emergência');
insert into specialty (id, name) values (30, 'Medicina de Família e Comunidade');
insert into specialty (id, name) values (31, 'Medicina do Trabalho');
insert into specialty (id, name) values (32, 'Medicina de Tráfego');
insert into specialty (id, name) values (33, 'Medicina Esportiva');
insert into specialty (id, name) values (34, 'Medicina Física e Reabilitação');
insert into specialty (id, name) values (35, 'Medicina Intensiva');
insert into specialty (id, name) values (36, 'Medicina Legal e Perícia Médica');
insert into specialty (id, name) values (37, 'Medicina Nuclear');
insert into specialty (id, name) values (38, 'Medicina Preventiva e Social');
insert into specialty (id, name) values (39, 'Nefrologia');
insert into specialty (id, name) values (40, 'Neurocirurgia');
insert into specialty (id, name) values (41, 'Neurologia');
insert into specialty (id, name) values (42, 'Nutrologia');
insert into specialty (id, name) values (43, 'Odontologia');
insert into specialty (id, name) values (44, 'Oftalmologia');
insert into specialty (id, name) values (45, 'Oncologia Clínica');
insert into specialty (id, name) values (46, 'Ortopedia e Traumatologia');
insert into specialty (id, name) values (47, 'Otorrinolaringologia');
insert into specialty (id, name) values (48, 'Patologia');
insert into specialty (id, name) values (49, 'Patologia Clínica/ Medicina Laboratorial');
insert into specialty (id, name) values (50, 'Pediatria');
insert into specialty (id, name) values (51, 'Pneumologia');
insert into specialty (id, name) values (52, 'Psiquiatria');
insert into specialty (id, name) values (53, 'Radiologia e Diagnóstico por Imagem');
insert into specialty (id, name) values (54, 'Radioterapia');
insert into specialty (id, name) values (55, 'Reumatologia');
insert into specialty (id, name) values (56, 'Urologia');

--Clinicas--
insert into tb_user (id, email, name, password) values (1, 'ok@doutor.com', 'Ok Doutor', '$2a$10$VdEtcZfZQbgLX9E3wykq/OX6ENEj83s2nPsQuUznBVhkJQaYiTFIW');
insert into clinic (cnpj, id) values ('52684284000186', 1);
insert into address (id, city, state, complement, latitude, longitude, neighborhood, number, public_area, user_id, zip_code) values (1, 'Recife', 'PE', 'Apto 303', null, null, 'Espinheiro', '300', 'Av. Agamenon Magalhães', 1, '38220834');
insert into address (id, city, state, complement, latitude, longitude, neighborhood, number, public_area, user_id, zip_code) values (2, 'Olinda', 'PE', null, null, null, 'Centro', '105', 'Presidente Kennedy', 1, 38777012);
insert into phones (user_id, phones) values (1, '34353637');
insert into phones (user_id, phones) values (1, '81994020345');
insert into profiles (user_id, profile_id) values (1, 1);
insert into profiles (user_id, profile_id) values (1, 3);
insert into clinic_specialties (clinic_id, specialties_id) values (1, 18);
insert into clinic_specialties (clinic_id, specialties_id) values (1, 43);
insert into credit (id, balance, user_id) values (10205, 0.0, 1);

insert into tb_user (id, email, name, password) values (2, 'clinica@sim.com', 'Clinica SIM', '$2a$10$VdEtcZfZQbgLX9E3wykq/OX6ENEj83s2nPsQuUznBVhkJQaYiTFIW');
insert into clinic (cnpj, id) values ('31048585000143', 2);
insert into address (id, city, state, complement, latitude, longitude, neighborhood, number, public_area, user_id, zip_code) values (3, 'Paulista', 'PE', null, null, null, 'Centro', '105', 'Presidente Kennedy', 2, 38777012);
insert into phones (user_id, phones) values (2, '34353637');
insert into phones (user_id, phones) values (2, '81994020345');
insert into profiles (user_id, profile_id) values (2, 1);
insert into profiles (user_id, profile_id) values (2, 3);
insert into clinic_specialties (clinic_id, specialties_id) values (2, 18);
insert into clinic_specialties (clinic_id, specialties_id) values (2, 43);
insert into credit (id, balance, user_id) values (23467, 0.0, 2);

insert into tb_user (id, email, name, password) values (3, 'sirio@libanes.com', 'Sirio Libanes', '$2a$10$/ZcXT4sPz7qiLzc2qJlpbep1EF7ZCXPmUrEtKLcE3lmxLjG6f9AY6');
insert into clinic (cnpj, id) values ('96083005000102', 3);
insert into address (id, city, state, complement, latitude, longitude, neighborhood, number, public_area, user_id, zip_code) values (4, 'São Paulo', 'SP', null, null, null, 'Centro', '2106', 'Avenida Paulista', 3, 39867024);
insert into phones (user_id, phones) values (3, '11994020345');
insert into phones (user_id, phones) values (3, '32353937');
insert into profiles (user_id, profile_id) values (3, 3);
insert into clinic_specialties (clinic_id, specialties_id) values (3, 16);
insert into clinic_specialties (clinic_id, specialties_id) values (3, 18);
insert into clinic_specialties (clinic_id, specialties_id) values (3, 43);
insert into credit (id, balance, user_id) values (38749, 0.0, 3);

--Clientes--
insert into tb_user (id, email, name, password) values (4, 'john@wick.com', 'John Wick', '$2a$10$VdEtcZfZQbgLX9E3wykq/OX6ENEj83s2nPsQuUznBVhkJQaYiTFIW');
insert into customer (cpf, id) values ('12117341020', 4);
insert into address (id, city, state, complement, latitude, longitude, neighborhood, number, public_area, user_id, zip_code) values (5, 'São Paulo', 'SP', 'Apto 901', null, null, 'Centro', '300', 'Av. Paulista', 4, '38220834');
insert into phones (user_id, phones) values (4, '11987506591');
insert into profiles (user_id, profile_id) values (4, 1);
insert into profiles (user_id, profile_id) values (4, 2);
insert into credit (id, balance, user_id) values (41628, 0.0, 4);

insert into tb_user (id, email, name, password) values (5, 'lara@croft.com', 'Lara Croft', '$2a$10$VdEtcZfZQbgLX9E3wykq/OX6ENEj83s2nPsQuUznBVhkJQaYiTFIW');
insert into customer (cpf, id) values ('81597383074', 5);
insert into address (id, city, state, complement, latitude, longitude, neighborhood, number, public_area, user_id, zip_code) values (6, 'Rio de Janeiro', 'RJ', null, null, null, 'Centro', '300', 'Av. Brasil', 5, '38220834');
insert into phones (user_id, phones) values (5, '21985742124');
insert into profiles (user_id, profile_id) values (5, 2);
insert into credit (id, balance, user_id) values (53216, 0.0, 5);

insert into tb_user (id, email, name, password) values (6, 'ghost@sparta.com', 'Kratos War', '$2a$10$VdEtcZfZQbgLX9E3wykq/OX6ENEj83s2nPsQuUznBVhkJQaYiTFIW');
insert into customer (cpf, id) values ('03689605016', 6);
insert into address (id, city, state, complement, latitude, longitude, neighborhood, number, public_area, user_id, zip_code) values (7, 'Recife', 'PE', null, null, null, 'Espinheiro', '115', 'Rua 48', 6, '38220834');
insert into phones (user_id, phones) values (6, '11992637589');
insert into profiles (user_id, profile_id) values (6, 2);
insert into credit (id, balance, user_id) values (67569, 0.0, 6);

--Transactions--
--John Wick--
insert into balance_movement (id, operation_date, type, value, destination_id, origin_id, status) values (1, {ts '2021-09-17 18:47:52.69'}, 'RECHARGE', 150, 41628, 41628, 'APPROVED');
insert into balance_movement (id, operation_date, type, value, destination_id, origin_id, status) values (2, {ts '2021-09-18 09:30:35.69'}, 'PAYMENT', 80, 38749, 41628, 'APPROVED');
insert into balance_movement (id, operation_date, type, value, destination_id, origin_id, status) values (3, {ts '2021-10-01 14:47:24.69'}, 'TRANSFER', 40, 53216, 41628, 'APPROVED');
update credit set balance = 90 where credit.id = 4;
--Lara Croft--
insert into balance_movement (id, operation_date, type, value, destination_id, origin_id, status) values (4, {ts '2021-09-25 18:47:52.69'}, 'RECHARGE', 200, 53216, 53216, 'APPROVED');
insert into balance_movement (id, operation_date, type, value, destination_id, origin_id, status) values (5, {ts '2021-09-30 10:23:06.69'}, 'PAYMENT', 80, 23467, 53216, 'APPROVED');
insert into balance_movement (id, operation_date, type, value, destination_id, origin_id, status) values (6, {ts '2021-10-05 08:12:53.69'}, 'TRANSFER', 35, 67569, 53216, 'APPROVED');
update credit set balance = 125 where credit.id = 5;
--Kratos War--
insert into balance_movement (id, operation_date, type, value, destination_id, origin_id, status) values (7, {ts '2021-09-27 18:47:52.69'}, 'RECHARGE', 300, 67569, 67569, 'APPROVED');
insert into balance_movement (id, operation_date, type, value, destination_id, origin_id, status) values (8, {ts '2021-10-02 14:55:16.69'}, 'PAYMENT', 50, 10205, 67569, 'APPROVED');
insert into balance_movement (id, operation_date, type, value, destination_id, origin_id, status) values (9, {ts '2021-10-05 19:36:24.69'}, 'TRANSFER', 60, 41628, 67569, 'APPROVED');
update credit set balance = 225 where credit.id = 6;

--Feedbacks--
insert into feedback (id, message, rating, clinic_id, customer_id) values (1, 'Atendimento mais ou menos', 5.0, 1, 4);
insert into feedback (id, message, rating, clinic_id, customer_id) values (2, 'Não volto mais', 1.0, 1, 5);
insert into feedback (id, message, rating, clinic_id, customer_id) values (3, 'Demorado demais', 1.0, 1, 6);
insert into feedback (id, message, rating, clinic_id, customer_id) values (4, 'Ótimos atendentes e atendimento rápido', 5.0, 2, 4);
insert into feedback (id, message, rating, clinic_id, customer_id) values (5, 'Só precisa trocar os atendentes', 4.0, 2, 5);
insert into feedback (id, message, rating, clinic_id, customer_id) values (6, 'Atendimento OK', 3.0, 2, 6);
insert into feedback (id, message, rating, clinic_id, customer_id) values (7, 'Ambiente organizado e atendimento excelente', 5.0, 3, 4);
insert into feedback (id, message, rating, clinic_id, customer_id) values (8, 'Caríssimo, atendimento péssimo', 1.0, 3, 5);
insert into feedback (id, message, rating, clinic_id, customer_id) values (9, 'Atendentes educados, mas o atendimento foi demorado', 3.0, 3, 6);