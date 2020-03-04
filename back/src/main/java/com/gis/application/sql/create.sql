drop table virus;

create table history_virus(
                              id serial PRIMARY KEY NOT NULL,
    PUBLIC_TIME INT ,
    LAT real,
    LNG real,
    ORIGIN varchar(300),
    NEW_DIAGNOSIS int ,
    NEW_RECOVERY int,
    NEW_DEATH int
);
select AddGeometryColumn('public', 'history_virus', 'location', 4326, 'POINT', 2);
drop table recent_virus;
create table recent_virus(
                             id serial PRIMARY KEY NOT NULL,
                             PUBLIC_TIME INT ,
                             LAT real,
                             LNG real,
                             ORIGIN varchar(300),
                             NEW_DIAGNOSIS int ,
                             NEW_RECOVERY int,
                             NEW_DEATH int
);
select AddGeometryColumn('public', 'recent_virus', 'location', 4326, 'POINT', 2);

CREATE SEQUENCE id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

alter table virus alter column id set default nextval('id_seq');
alter table recent_virus alter column id set default nextval('id_seq');


create table test_virus(
                             id serial PRIMARY KEY NOT NULL,
                             PUBLIC_TIME INT ,
                             LAT real,
                             LNG real,
                             ORIGIN varchar(300),
                             NEW_DIAGNOSIS int ,
                             NEW_RECOVERY int,
                             NEW_DEATH int
);
select AddGeometryColumn('public', 'test_virus', 'location', 4326, 'POINT', 2);


alter table test_virus alter column id set default nextval('id_seq');

