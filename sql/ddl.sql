 drop table if exists member CASCADE;
 create table member
 (
    id
bigint generated by default as identity,
    name varchar(255),
 primary key (id)
 );

 insert into member(name) values('tesd9');
 delete from member where id=1;
