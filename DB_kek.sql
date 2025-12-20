create type spec as enum ('MDD', 'RDD', 'HEAL', 'TANK');

create table guild(
id bigserial  primary key,
create_date date default now(),
update_date date default now(),
name varchar
);

create table Person(
id bigserial  primary key,
create_date date default now(),
update_date date default now(),
name varchar,
gear_score int4,
invited_date date 
);


create table Item(
id bigserial primary key,
create_date date default now(),
update_date date default now(),
name varchar
);

create table ItemToPerson(

person_id bigint,
item_id bigint,

constraint itp_person_id_fk foreign key (person_id) references Person(id),
constraint itp_item_id_fk foreign key (item_id) references Item(id)
);

create table Event(
	id bigserial primary key,
	create_date date default now(),
	update_date date default now(),
	name varchar,
	gold int
);

create table EventToPerson(

person_id bigint,
event_id bigint,

constraint etp_person_id_fk foreign key (person_id) references Person(id),
constraint etp_event_id_fk foreign key (event_id) references Event(id)
);
-- V2
alter table guild add column owner_id bigint references Person(id);

create table invites(
person_id bigint references Person(id),
guild_id bigint references Guild(id),
result bool
);

create type member_status as enum ('MEMBER', 'ADMIN');

create table account(
c
login varchar(40) not null,
password varchar(100) not null,
token varchar(100),
valid_token_time timestamp,
personId bigint references Person(id)
);

alter table event add column guild_id bigint references Guild(id);
alter table INVITES add column isProcessed bool default false;
alter table invites add column id bigserial primary key;
alter table invites add column create_date date default now();
alter table invites add column update_date date default now();

create type roles as enum ('USER', 'ADMIN');
alter table account add column role roles default 'USER';