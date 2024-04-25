-- 创建数据表
use stories_system;

create table IF NOT EXISTS user_data
(
    uid         bigint unsigned not null comment '用户表序号'
        primary key,
    uuid        char(36)        not null comment '用户唯一识别码',
    name        varchar(100)    not null comment '用户名',
    gender      varchar(10)     null comment '用户性别',
    email       varchar(100)    not null comment '用户邮箱',
    password    varchar(255)    not null comment '用户密码',
    avatar      text            null comment '用户头像',
    create_at   timestamp       not null comment '用户创建时间',
    deleted_at  timestamp       null comment '用户删除时间',
    update_at   timestamp       null comment '用户更新时间',
    all_stories json            null comment '与用户相关的所有故事',
    constraint user_data_email_uindex
        unique (email)
);

create index uuid_index
    on user_data (uuid);

create table user_stories
(
    sid        bigint unsigned not null comment '故事表序号'
        primary key,
    ssid       char(36)        not null comment '故事唯一识别码',
    auid       char(36)        not null comment '故事创建者唯一识别码',
    uuid       json            null comment '故事参与者唯一识别码',
    name       varchar(100)    not null comment '故事名称',
    type       varchar(100)    null comment '故事类型',
    start_time timestamp       not null comment '故事开始时间',
    end_time   timestamp       null comment '故事结束时间',
    content    text            not null comment '故事内容',
    photos     json            null comment '故事图片链接',
    mood       varchar(100)    null comment '作者情绪',
    place      varchar(100)    null comment '故事发生地点',
    status     varchar(100)    not null comment '故事状态',
    constraint user_stories_ssid_uindex
        unique (ssid),
    constraint user_stories_user_data_uuid_fk
        foreign key (auid) references user_data (uuid)
            on update cascade on delete cascade
);

create table code
(
    cid           bigint unsigned auto_increment comment '主键'
        primary key,
    email         varchar(100) not null comment '邮箱',
    code          char(6)      not null comment '邮箱验证码',
    created_time  timestamp    not null comment '验证码创建时间',
    update_time   timestamp    null comment '验证码修改时间',
    times         int          null comment '验证错误次数',
    modified_time timestamp    null comment '上次错误校验时间',
    constraint code_user_data_email_fk
        foreign key (email) references user_data (email)
            on update cascade on delete cascade
)
    comment '校验表';
