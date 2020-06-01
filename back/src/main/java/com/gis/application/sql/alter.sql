alter table virus
    add country varchar(300);

alter table virus
    add province varchar(300);

alter table virus
    add city varchar(300);

alter table virus
    add district varchar(300);

select count(*)
from virus;

select country, sum(new_diagnosis) as total
from virus where country<>'中国'
group by country
order by total desc
limit 20
;

select country as name, sum(new_diagnosis) as value
from virus where country <> '中国'
group by name
order by value
select *
from virus
where country = '钻石公主号';

select province ,lng,lat ,sum(new_diagnosis) as new_diagnosis,sum(new_recovery) as new_recovery,sum(new_death) as new_death
from virus where country  = '中国'
group by province,lng,lat

select country as name, sum(new_diagnosis) as value
from virus where country  <>'中国'
            group by name
            order by value Desc
            limit 30


alter table virus
    add description text;

alter table virus
    add fcode varchar(45);

alter table virus
    add image by;