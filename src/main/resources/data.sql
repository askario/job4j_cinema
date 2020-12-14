insert into halls (id,name) values
    (1,'Большой зал');

insert into sessions (id, name, hall_id) values
    (1,'Вечерний сеанс',1);

insert into seats (row,place,hall_id) values
    (1,1,1),(1,2,1),(1,3,1),
    (2,1,1),(2,2,1),(2,3,1),
    (3,1,1),(3,2,1),(3,3,1);