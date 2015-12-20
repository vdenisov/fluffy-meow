## Drop existing tables
drop table if exists SETTINGS;
drop table if exists USERS;

## Set default encoding to UTF8
alter database default character set utf8
collate utf8_general_ci;

## Create tables

create table USERS
(
  USR_ID int not null auto_increment,
  USR_EMAIL varchar(255) not null,
  USR_PASSWORD_HASH varchar(64) not null,
  USR_PASSWORD_SALT varchar(64) not null,
  USR_REGISTER_DATE datetime not null,
  USR_LAST_LOGIN_DATE datetime,

  constraint USERS_PK primary key (USR_ID),
  unique index UNIQUE_USR_EMAIL (USR_EMAIL)
);

create table SETTINGS
(
  ST_NAME varchar(64) not null,
  ST_VAL varchar(255),

  constraint SETTINGS_PK primary key (ST_NAME)
);

insert into SETTINGS (ST_NAME, ST_VAL) values ('db.version', '1');