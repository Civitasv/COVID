drop table virus;

create table virus
(
    id            serial PRIMARY KEY NOT NULL,
    PUBLIC_TIME   INT,
    LAT           real,
    LNG           real,
    ORIGIN        varchar(300),
    NEW_DIAGNOSIS int,
    NEW_RECOVERY  int,
    NEW_DEATH     int
);
select AddGeometryColumn('public', 'history_virus', 'location', 4326, 'POINT', 2);


CREATE SEQUENCE id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

alter table virus
    alter column id set default nextval('id_seq');

SELECT nextval('id_seq');

alter sequence id_seq restart with 9639;

select count(*)
from virus;

insert into test_virus(public_time, lat, lng) values (1,2,3)

delete  from virus where id=9640
update virus set id = 1 where id= 1

select public_time , sum(new_diagnosis) as new_diagnosis,sum(new_recovery) as new_recovery,sum(new_death) as new_death
from virus
group by public_time

select public_time , sum(new_diagnosis) as new_diagnosis,sum(new_recovery) as new_recovery,sum(new_death) as new_death
from virus
group by public_time
order by public_time desc

delete from virus where PUBLIC_TIME=43924

select * from virus where country like '意大利'


update virus
set lng=114.171994,
    lat=   22.281089,
    location=ST_GeomFromText('POINT(114.171994 22.281089)', 4326)
where province='香港' ;

select * from virus where lat=0
select * from virus where province='四川' and city=''

select count(*) from virus

select country as name, sum(NEW_DEATH) as value
from virus
group by name
order by value Desc
limit 10

select country as name, sum(NEW_DEATH)*1.0/sum(NEW_DIAGNOSIS) as value
from virus
group by name
order by value Desc
limit 10

update virus
set lng = 1,
    lat = 1,
    location=ST_GeomFromText('POINT(1 1)', 4326)
where id=15

          <select id="getVirusDivideByTime" resultType="Map">
select public_time , sum(new_diagnosis) as new_diagnosis,sum(new_recovery) as new_recovery,sum(new_death) as new_death
from virus
group by public_time
order by public_time asc

select public_time , sum(new_diagnosis) as new_diagnosis,sum(new_recovery) as new_recovery,sum(new_death) as new_death
from virus where country = '中国'
group by public_time
order by public_time asc

select * from virus where id=15;


CREATE TABLE account
(
    USERNAME VARCHAR(45) NOT NULL,
    PASSWORD  VARCHAR(45) NULL,
    PRIMARY KEY (USERNAME)
);

insert into account values ('husen','12345')

delete from account where USERNAME=''