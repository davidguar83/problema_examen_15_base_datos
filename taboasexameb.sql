alter session set nls_date_format = 'dd.mm.yyyy hh24:mi:ss';

drop table composicion cascade constraints;
drop table componentes cascade constraints;


create table composicion(codp varchar2(3),codc varchar2(3),peso integer, primary key (codp,codc));
insert into composicion values ('p1','c1',400);
insert into composicion values ('p1','c3',600);
insert into composicion values ('p2','c2','600');
insert into composicion values ('p2','c3','300');
insert into composicion values ('p2','c4','200');
insert into composicion values ('p3','c1','100');
insert into composicion values ('p3','c2','200');
insert into composicion values ('p4','c1','200');
insert into composicion values ('p4','c3','200');

create table componentes(codc varchar2(3),nomec varchar2(15),graxa integer, primary key (codc));
insert into componentes values ('c1','vacuno',5);
insert into componentes values ('c2','ovino',20);
insert into componentes values ('c3','avicola',10);
insert into componentes values ('c4','avicola',5);


commit;
select * from composicion;
select * from componentes;


/*
 @/home/oracle/NetBeansProjects/examen1evb/taboasexameb.sql

composicion

COD COD       PESO
--- --- ----------
p1  c1	       400
p1  c3	       600
p2  c2	       600
p2  c3	       300
p2  c4	       200
p3  c1	       100
p3  c2	       200
p4  c1	       200
p4  c3	       200

9 rows selected.

componentes

COD NOMEC		 GRAXA
--- --------------- ----------
c1  vacuno		     5
c2  ovino		    20
c3  avicola		    10
c4  avicola		     5






*/
