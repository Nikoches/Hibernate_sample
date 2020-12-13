create table users
(
    name text,
    pass varchar,
    us_id int
    primary key
        references items(user_id) on update no action on delete no action
);

create table items
(
    id int primary key,
    name text,
    description text,
    done boolean,
    user_id int
         references users(us_id) on update no action on delete no action
);
