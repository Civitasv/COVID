create table account
(
    username varchar(255) not null
        primary key,
    password varchar(255) not null
);

create table china_all
(
    id                  int                     not null comment 'id'
        primary key,
    timestamp           int                     not null comment '时间戳',
    lon                 decimal(10, 6)          not null comment '经度',
    lat                 decimal(10, 6)          not null comment '纬度',
    confirmed           int                     not null comment '累计确诊',
    deaths              int                     not null comment '累计死亡',
    recovered           int                     not null comment '累计痊愈',
    suspect             int                     not null comment '疑似',
    current_confirmed   int                     not null comment '现存确诊',
    new_confirmed       int                     not null comment '新增确诊',
    new_deaths          int                     not null comment '新增死亡',
    new_recovered       int                     not null comment '新增治愈',
    new_suspect         int                     not null comment '新增存疑',
    case_fatality_ratio decimal(10, 6)          not null comment '病死率',
    country             varchar(255)            not null comment '国家',
    combined_key        varchar(255)            not null comment '地点总称',
    province            varchar(255) default '' null comment '省份',
    city                varchar(255) default '' null comment '城市',
    location_code       varchar(255)            null,
    location            point                   not null
);

create table china_city
(
    id                  int            not null comment 'id'
        primary key,
    timestamp           int            not null comment '时间戳',
    lon                 decimal(10, 6) not null comment '经度',
    lat                 decimal(10, 6) not null comment '纬度',
    confirmed           int            not null comment '累计确诊',
    deaths              int            not null comment '累计死亡',
    recovered           int            not null comment '累计痊愈',
    suspect             int            not null comment '疑似',
    current_confirmed   int            not null comment '现存确诊',
    new_confirmed       int            not null comment '新增确诊',
    new_deaths          int            not null comment '新增死亡',
    new_recovered       int            not null comment '新增治愈',
    new_suspect         int            not null comment '新增存疑',
    case_fatality_ratio decimal(10, 6) not null comment '病死率',
    country             varchar(255)   not null comment '国家',
    combined_key        varchar(255)   not null comment '地点总称',
    province            varchar(255)   not null comment '省份',
    city                varchar(255)   not null comment '城市',
    location_code       varchar(255)   null,
    location            point          not null
);

create table china_province
(
    id                  int            not null comment 'id'
        primary key,
    timestamp           int            not null comment '时间戳',
    lon                 decimal(10, 6) not null comment '经度',
    lat                 decimal(10, 6) not null comment '纬度',
    confirmed           int            not null comment '累计确诊',
    deaths              int            not null comment '累计死亡',
    recovered           int            not null comment '累计痊愈',
    suspect             int            not null comment '疑似',
    current_confirmed   int            not null comment '现存确诊',
    new_confirmed       int            not null comment '新增确诊',
    new_deaths          int            not null comment '新增死亡',
    new_recovered       int            not null comment '新增治愈',
    new_suspect         int            not null comment '新增存疑',
    case_fatality_ratio decimal(10, 6) not null comment '病死率',
    country             varchar(255)   not null comment '国家',
    combined_key        varchar(255)   not null comment '地点总称',
    province            varchar(255)   not null comment '省份',
    location            point          not null,
    location_code       varchar(255)   null
);

create table period
(
    start int default 0 not null comment '数据起始时间',
    end   int           not null comment '数据终止时间'
);

create table world
(
    id                  int default 0  not null comment 'id'
        primary key,
    timestamp           int            not null comment '时间戳',
    lon                 decimal(10, 6) not null comment '经度',
    lat                 decimal(10, 6) not null comment '纬度',
    confirmed           int            not null comment '累计确诊',
    deaths              int            not null comment '累计死亡',
    recovered           int            not null comment '累计痊愈',
    suspect             int            not null comment '疑似',
    current_confirmed   int            not null comment '现存确诊',
    new_confirmed       int            not null comment '新增确诊',
    new_deaths          int            not null comment '新增死亡',
    new_recovered       int            not null comment '新增治愈',
    new_suspect         int            not null comment '新增存疑',
    case_fatality_ratio decimal(10, 6) not null comment '病死率',
    country             varchar(255)   not null comment '国家',
    combined_key        varchar(255)   not null comment '地点总称',
    location            point          null
);

create table world_all
(
    id                  int            not null comment 'id'
        primary key,
    timestamp           int            not null comment '时间戳',
    lon                 decimal(10, 6) not null comment '经度',
    lat                 decimal(10, 6) not null comment '纬度',
    confirmed           int            not null comment '累计确诊',
    deaths              int            not null comment '累计死亡',
    recovered           int            not null comment '累计痊愈',
    suspect             int            not null comment '疑似',
    current_confirmed   int            not null comment '现存确诊',
    new_confirmed       int            not null comment '新增确诊',
    new_deaths          int            not null comment '新增死亡',
    new_recovered       int            not null comment '新增治愈',
    new_suspect         int            not null comment '新增存疑',
    case_fatality_ratio decimal(10, 6) not null comment '病死率',
    country             varchar(255)   not null comment '国家',
    combined_key        varchar(255)   not null comment '地点总称',
    province            varchar(255)   null comment '洲',
    city                varchar(255)   null comment '乡村',
    location            point          not null
);

create table world_county
(
    id                  int            not null comment 'id'
        primary key,
    timestamp           int            not null comment '时间戳',
    lon                 decimal(10, 6) not null comment '经度',
    lat                 decimal(10, 6) not null comment '纬度',
    confirmed           int            not null comment '累计确诊',
    deaths              int            not null comment '累计死亡',
    recovered           int            not null comment '累计痊愈',
    suspect             int            not null comment '疑似',
    current_confirmed   int            not null comment '现存确诊',
    new_confirmed       int            not null comment '新增确诊',
    new_deaths          int            not null comment '新增死亡',
    new_recovered       int            not null comment '新增治愈',
    new_suspect         int            not null comment '新增存疑',
    case_fatality_ratio decimal(10, 6) not null comment '病死率',
    country             varchar(255)   not null comment '国家',
    combined_key        varchar(255)   not null comment '地点总称',
    province            varchar(255)   null comment '洲',
    city                varchar(255)   null comment '乡村',
    location            point          not null
);

create table world_state
(
    id                  int            not null comment 'id'
        primary key,
    timestamp           int            not null comment '时间戳',
    lon                 decimal(10, 6) not null comment '经度',
    lat                 decimal(10, 6) not null comment '纬度',
    confirmed           int            not null comment '累计确诊',
    deaths              int            not null comment '累计死亡',
    recovered           int            not null comment '累计痊愈',
    suspect             int            not null comment '疑似',
    current_confirmed   int            not null comment '现存确诊',
    new_confirmed       int            not null comment '新增确诊',
    new_deaths          int            not null comment '新增死亡',
    new_recovered       int            not null comment '新增治愈',
    new_suspect         int            not null comment '新增存疑',
    case_fatality_ratio decimal(10, 6) not null comment '病死率',
    country             varchar(255)   not null comment '国家',
    combined_key        varchar(255)   not null comment '地点总称',
    province            varchar(255)   not null comment '洲',
    location            point          not null
);

