DROP ALL OBJECTS;

create table address (id serial primary key, complement varchar(255), latitude varchar(255), longitude varchar(255), neighborhood varchar(255), number varchar(255), public_area varchar(255), city varchar(255), state varchar(255), zip_code varchar(255), user_id integer, primary key (id));
create table balance_movement (id serial primary key, operation_date timestamp, type varchar(255), value double, destination_id integer, origin_id integer, status varchar(255), primary key (id));
create table clinic (cnpj varchar(255), id integer not null, primary key (id));
create table clinic_specialties (clinic_id integer not null, specialties_id integer not null);
create table credit (id serial primary key, balance double, user_id integer, primary key (id));
create table customer (cpf varchar(255), id integer not null, primary key (id));
create table feedback (id serial primary key, message varchar(255), rating double, clinic_id integer, customer_id integer, primary key (id));
create table phones (user_id integer not null, phones varchar(255));
create table profiles (user_id integer not null, profiles integer);
create table specialty (id serial primary key, name varchar(255), primary key (id));
create table email (id serial primary key, ownerRef varchar(255), emailFrom varchar(255), emailTo varchar(255), text text, sendDateEmail date, statusEmail integer, primary key (id));
create table user (id serial primary key, email varchar(255), name varchar(255), password varchar(255), primary key (id));

alter table address add constraint fk_address_user foreign key (user_id) references user;
alter table balance_movement add constraint fk_balance_mov_credit_dest foreign key (destination_id) references credit;
alter table balance_movement add constraint fk_balance_mov_credit_orig foreign key (origin_id) references credit;
alter table clinic add constraint fk_clinic_user foreign key (id) references user;
alter table clinic_specialties add constraint fk_clic_specialt_specialty foreign key (specialties_id) references specialty;
alter table clinic_specialties add constraint fk_clic_specialt_clinic foreign key (clinic_id) references clinic;
alter table credit add constraint fk_credit_user foreign key (user_id) references user;
alter table customer add constraint fk_customer_user foreign key (id) references user;
alter table feedback add constraint fk_feedback_clinic foreign key (clinic_id) references clinic;
alter table feedback add constraint fk_feedback_customer foreign key (customer_id) references customer;
alter table phones add constraint fk_phones_user foreign key (user_id) references user;
alter table profiles add constraint fk_profiles_user foreign key (user_id) references user;
alter table user add constraint uk_user_email unique(email);