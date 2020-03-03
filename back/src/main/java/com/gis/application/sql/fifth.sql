insert into recent_virus (select * from history_virus where public_time=43891);

select * from recent_virus;

select * from history_virus where public_time=43891

select id,publicTime,lat,lng,origin,newDiagnosis,newRecovery,newDeath,ST_AsGeoJson(location) as location from history_virus where public_time = 43891

