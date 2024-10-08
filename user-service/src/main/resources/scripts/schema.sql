DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id          bigint NOT NULL AUTO_INCREMENT,
    email       varchar(100) NOT NULL,
    username    varchar(50) NOT NULL,
    password    varchar(500) NOT NULL,
    profile_url varchar(500) NOT NULL,
    created_at  timestamp default NOW(),
    updated_at  timestamp default NOW(),
    primary key (id)
);