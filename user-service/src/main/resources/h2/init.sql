create table users (
    id bigint auto_increment,
    name varchar(50),
    balance int,
    primary key (id)
);

create table user_transaction (
    id bigint auto_increment,
    user_id bigint,
    amount int,
    transaction_date timestamp,
    CONSTRAINT fk_user_id
    foreign key (user_id)
    references users(id)
    on DELETE CASCADE
);

INSERT INTO users (name, balance) VALUES ('Pratheeban', 126000), ('Abiya', 106000), ('Dhiya', 232000);